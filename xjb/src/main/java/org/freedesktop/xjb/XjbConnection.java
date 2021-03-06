package org.freedesktop.xjb;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;
import java.net.UnixDomainSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.freedesktop.xjb.XAuthorityReader.InvalidXAuthorityFileException;
import org.freedesktop.xjb.util.Padding;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public final class XjbConnection implements AutoCloseable {

    private SocketChannel socketChannel;

    private final int displayNumber;

    private final int preferredScreenNumber;

    private XjbConnection(final int displayNumber, final int preferredScreenNumber) {
        if (displayNumber < 0)
            throw new IllegalArgumentException();
        if (preferredScreenNumber < 0)
            throw new IllegalArgumentException();
        this.displayNumber         = displayNumber;
        this.preferredScreenNumber = preferredScreenNumber;
    }

    public static void main(final String... args) throws IOException {
        XjbConnection.establish();
    }

    public static @NotNull XjbConnection establish() throws IOException {
        return establish(System.getenv("DISPLAY"));
    }

    public static @NotNull XjbConnection establish(@NotNull final String displayName) throws IOException {
        return establish(displayName, null);
    }

    /*
     * See 6502afa for a more complex version of this regular expression which would support socket paths and the
     * specification of a protocol. However, these features are not documented in the X man pages where only the
     * hostname:displaynumber.screennumber syntax is mentioned.
     */
    private static final Pattern DISPLAY_ENVIRONMENT_VARIABLE_FORMAT =
        Pattern.compile("^(?<hostname>.+)?:(?<display>[0-9]+)(?:\\.(?<screen>[0-9]+))?$");

    public static @NotNull XjbConnection establish(@NotNull final String displayName, @Nullable AuthInfo authInfo)
            throws IOException {
        final Matcher matcher = DISPLAY_ENVIRONMENT_VARIABLE_FORMAT.matcher(displayName);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Bad display string '%s'.", displayName));

        final String hostname = matcher.group("hostname");

        final int display = Integer.parseInt(matcher.group("display"));
        final int screen =
            Optional
                .ofNullable(matcher.group("screen"))
                .map(Integer::parseInt)
                .orElse(0);

        final var connection = new XjbConnection(display, screen);

        if (authInfo == null)
            authInfo = getAuthInfo(hostname, display);

        return connection.connect(hostname, authInfo);
    }

    private static AuthInfo getAuthInfo(final @Nullable String hostname, final int display) throws IOException {
        final List<XAuthority> xAuthorities;
        final Optional<XAuthorityReader> reader = XAuthorityReader.forStandardLocations();
        if (reader.isEmpty())
            return AuthInfo.empty();

        try {
            xAuthorities = reader.get().read();
        } catch (final InvalidXAuthorityFileException exception) {
            return AuthInfo.empty();
        }

        return xAuthorities
            .stream()
            .filter(xAuthority -> {
                final String address;
                if (hostname != null) {
                    address = hostname;
                } else {
                    try {
                        address = InetAddress.getLocalHost().getHostName();
                    } catch (final UnknownHostException exception) {
                        throw new UncheckedIOException(exception);
                    }
                }
                return xAuthority.isMatch(address, Integer.toString(display));
            })
            .findFirst()
            .map(AuthInfo.class::cast)
            .orElse(XAuthority.empty());
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    private XjbConnection connect(final @Nullable String hostname, final @NotNull AuthInfo authInfo)
            throws IOException {
        if (hostname == null)
            return connectToUnixDomainSocket(authInfo);
        return connectToInetSocket(hostname, authInfo);
    }

    private XjbConnection connectToUnixDomainSocket(final @NotNull AuthInfo authInfo) throws IOException {
        try {
            /*
             * TODO:
             *  - try /usr/spool/sockets/X11/%d location on HP-UX
             *  - try /var/tsol/doors/.X11-unix/X%d location under Solaris Trusted Extensions
             */
            /* Attempt to connect via AF_UNIX. */
            return connect(UnixDomainSocketAddress.of("/tmp/.X11-unix/X" + 6), authInfo);
        } catch (final SocketException exception) {
            /* Fall back to AF_INET/AF_INET6. */
            return connectToInetSocket("localhost", authInfo);
        }
    }

    /**
     * The well-known TCP ports for X11 are 6000 to 6063. The screen number is typically added to the base port of 6000.
     */
    private static final int X_TCP_BASE_PORT = 6_000;

    private XjbConnection connectToInetSocket(final @Nullable String hostname, final @NotNull AuthInfo authInfo)
            throws IOException {
        final var inetAddress = (hostname == null) ? InetAddress.getLoopbackAddress() : InetAddress.getByName(hostname);

        return connect(new InetSocketAddress(inetAddress, X_TCP_BASE_PORT + displayNumber), authInfo);
    }

    private static final Charset CHARSET = StandardCharsets.ISO_8859_1;

    /**
     * See "Chapter 8. Connection Setup" and "Connection Setup" under "Appendix B. Protocol Encoding"
     * of the X Window System Protocol for more information.
     *
     * @param remote
     *
     * @param authInfo
     *
     * @return
     *
     * @throws IOException If no {@link SocketChannel} from the provided {@code remote}.
     */
    private XjbConnection connect(final @NotNull SocketAddress remote, final @NotNull AuthInfo authInfo)
            throws IOException {
        final int paddedAuthNameLength = Padding.pad(authInfo.getName().length());
        final int paddedAuthDataLength = Padding.pad(authInfo.getData().length());

        socketChannel = SocketChannel.open(remote);
        socketChannel.write(
            ByteBuffer.allocate(1 + 1 + 5 * 2 + paddedAuthNameLength + paddedAuthDataLength)
                .order(ByteOrder.BIG_ENDIAN)
                .put((byte) 'B')      /* byte_order */
                .put((byte) 0)        /* pad0       */
                .putShort((short) 11) /* protocol_major_version */
                .putShort((short)  0) /* protocol_minor_version */
                .putShort((short) authInfo.getName().length()) /* authorization_protocol_name_len */
                .putShort((short) authInfo.getData().length()) /* authorization_protocol_data_len */
                .putShort((short)  0) /* pad1       */
                .put(authInfo.getName().getBytes(CHARSET))
                .put(new byte[paddedAuthNameLength - authInfo.getName().length()])
                .put(authInfo.getData().getBytes(CHARSET)));

        final ByteBuffer commonReplyHeader = ByteBuffer.allocate(1);
        socketChannel.read(commonReplyHeader);

        return this;
    }

    public <R extends Reply> R sendRequest(@NotNull final Request<R> request) {
        return null;
    }

    public int getPreferredScreenNumber() {
        return preferredScreenNumber;
    }
}
