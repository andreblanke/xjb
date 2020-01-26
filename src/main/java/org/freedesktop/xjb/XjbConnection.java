package org.freedesktop.xjb;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnixDomainSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public final class XjbConnection implements AutoCloseable {

    private int preferredScreenNumber;

    private SocketChannel socketChannel;

    /* Prevent instantiation. */
    private XjbConnection(final int preferredScreenNumber) {
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

    /**
     * https://regex101.com/r/X6Ix1y/2
     */
    private static final Pattern DISPLAY_ENVIRONMENT_VARIABLE_FORMAT =
        Pattern.compile(
            """
            ^(?x) # Embedded flags expression to enable comment mode.
                 (?: # Start of the non-capturing group containing either a socketPath or an optional host name.
                    (?<socketPath>/.+?) # Match a file path to a socket
                                       | # or
                                        (?: # optionally match a host name consisting of
                                           (?<host>.+?) # a host,
                                                       (?:/(?<protocol>[^:]+))? # optionally followed by a protocol.
                                                                               )? # End of the optional host name.
                                                                                 : # Required separator between the optional host name and
                                                                                  (?<display>[0-9]+) # the display number.
                                                                                                    ) # End of the choice between socketPath and the host name.
                                                                                                     (?:\\.(?<screen>[0-9]+))?$ # Both may be followed by an optional screen number.
            """);

    private static final int X_TCP_BASE_PORT = 6_000;

    public static @NotNull XjbConnection establish(@NotNull final String displayName, @Nullable final AuthInfo authInfo)
            throws IOException {
        final Matcher matcher = DISPLAY_ENVIRONMENT_VARIABLE_FORMAT.matcher(displayName);
        if (!matcher.matches())
            throw new IllegalArgumentException("Bad display string '%s'.".formatted(displayName));

        /* Use the specified screen number as preferredScreenNumber, or 0 if it is not specified. */
        final int preferredScreenNumber =
            Optional
                .ofNullable(matcher.group("screen"))
                .map(Integer::parseInt)
                .orElse(0);
        final var connection = new XjbConnection(preferredScreenNumber);

        final String socketPath = matcher.group("socketPath");
        if (socketPath != null) {
            connection.establishViaUnixDomainSocket(Paths.get(socketPath));
            return connection;
        }

        final String host = matcher.group("host");
        final String protocol =
            Optional
                .ofNullable(matcher.group("protocol"))
                .orElse("tcp");
        final int display = Integer.parseInt(matcher.group("display"));

        if (host == null || host.equals("unix") || protocol.equals("unix")) {
            connection.establishViaUnixDomainSocket(display);
        } else {

        }
        return connection;
    }

    private void establishViaUnixDomainSocket(final int display) throws IOException {
        final Path defaultSocketPath = Paths.get("/tmp/.X11-unix/X" + display);
        if (Files.exists(defaultSocketPath)) {
            establishViaUnixDomainSocket(defaultSocketPath);
            return;
        }

        final Path hpUxSocketPath = Paths.get("/usr/spool/sockets/X11/" + display);
        if (Files.exists(hpUxSocketPath)) {
            establishViaUnixDomainSocket(hpUxSocketPath);
            return;
        }

        final Path tsolSocketPath = Paths.get("/var/tsol/doors/.X11-unix/X" + display);
        if (Files.exists(tsolSocketPath)) {
            establishViaUnixDomainSocket(tsolSocketPath);
            return;
        }
        throw new RuntimeException();
    }

    private void establishViaUnixDomainSocket(final Path socketPath) throws IOException {
        connect(new UnixDomainSocketAddress(socketPath));
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    private void connect(final SocketAddress remote) throws IOException {
        socketChannel = SocketChannel.open(remote);
    }

    public int getPreferredScreenNumber() {
        return preferredScreenNumber;
    }
}
