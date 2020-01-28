package org.freedesktop.xjb;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnixDomainSocketAddress;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public final class XjbConnection implements AutoCloseable {

    private int displayNumber;

    private int preferredScreenNumber;

    private SocketChannel socketChannel;

    /* Prevent instantiation. */
    private XjbConnection(final int displayNumber, final int preferredScreenNumber) {
        if (preferredScreenNumber < 0)
            throw new IllegalArgumentException();
        this.preferredScreenNumber = preferredScreenNumber;
    }

    public static @NotNull XjbConnection establish() throws IOException {
        return establish(System.getenv("DISPLAY"));
    }

    public static @NotNull XjbConnection establish(final String displayName) throws IOException {
        return establish(displayName, null);
    }

    /*
     * See 6502afa for a more complex version of this regular expression which would support socket paths and the
     * specification of a protocol. However, these features are not documented in the X man pages where only the
     * hostname:displaynumber.screennumber syntax is mentioned.
     */
    private static final Pattern DISPLAY_ENVIRONMENT_VARIABLE_FORMAT =
        Pattern.compile("^(?<hostname>.+)?:(?<display>[0-9]+)(?:\\.(?<screen>[0-9]+))?$");

    private static final int X_TCP_BASE_PORT = 6_000;

    public static @NotNull XjbConnection establish(@NotNull final String displayName, @Nullable final AuthInfo authInfo)
            throws IOException {
        final Matcher matcher = DISPLAY_ENVIRONMENT_VARIABLE_FORMAT.matcher(displayName);
        if (!matcher.matches())
            throw new IllegalArgumentException(String.format("Bad display string '%s'.", displayName));

        /* Use the specified screen, or 0 if it is not specified. */
        final int screen =
            Optional
                .ofNullable(matcher.group("screen"))
                .map(Integer::parseInt)
                .orElse(0);
        final int display = Integer.parseInt(matcher.group("display"));
        final var connection = new XjbConnection(display, screen);

        final String hostname = matcher.group("hostname");
        if (hostname == null) {
            try {
                /*
                 * TODO:
                 *  - try /usr/spool/sockets/X11/%d location on HP-UX
                 *  - try /var/tsol/doors/.X11-unix/X%d location under Solaris Trusted Extensions
                 */
                /* Attempt to connect via AF_UNIX. */
                connection.connect(new UnixDomainSocketAddress("/tmp/.X11-unix/X" + display), authInfo);
                return connection;
            } catch (final SocketException exception) {
                /* Fallback to AF_INET/AF_INET6 with hostname "localhost". */
            }
        }
        connection.connect(
            new InetSocketAddress(Objects.requireNonNullElse(hostname, "localhost"), X_TCP_BASE_PORT + display),
            authInfo);

        return connection;
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    public static void main(final String... args) throws IOException {
        XjbConnection.establish();
    }

    private void connect(final SocketAddress remote, final AuthInfo authInfo) throws IOException {
        final var reader = XAuthorityReader.forStandardLocations();

        reader.readEntries();

        socketChannel = SocketChannel.open(remote);
        socketChannel.write(
            ByteBuffer.allocate(1 + 1 + 5 * 2)
                .put((byte) 'B')        /* byte_order */
                .put((byte) 0)          /* pad0       */
                .putShort((short) 11)   /* protocol_major_version */
                .putShort((short)  0)   /* protocol_minor_version */
                .putShort((short)  0)   /* authorization_protocol_name_len */
                .putShort((short)  0)   /* authorization_protocol_data_len */
                .putShort((short)  0)); /* pad1 */

        final ByteBuffer commonReplyHeader = ByteBuffer.allocate(8);
        socketChannel.read(commonReplyHeader);
    }

    public int getPreferredScreenNumber() {
        return preferredScreenNumber;
    }
}
