package org.freedesktop.xjb;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnixDomainSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class XjbConnection implements AutoCloseable {

    private SocketChannel socketChannel;

    /* Prevent instantiation. */
    private XjbConnection() {
    }

    public static @NotNull XjbConnection establish() throws IOException {
        return establish(System.getenv("DISPLAY"));
    }

    public static @NotNull XjbConnection establish(final String displayName) throws IOException {
        return establish(displayName, null);
    }

    static final Pattern DISPLAY_ENVIRONMENT_VARIABLE_FORMAT = Pattern.compile(
        "^(?:(?<socketPath>/.+?)|(?:(?<host>[^/:]+)(?:/(?<protocol>[^:]+))?)?:(?<display>[0-9][1-9]*))(?:\\.(?<screen>[0-9][1-9]*))?$");

    public static @NotNull XjbConnection establish(@NotNull final String displayName, @Nullable final AuthInfo authInfo)
            throws IOException {
        final var connection = new XjbConnection();

        final Matcher matcher = DISPLAY_ENVIRONMENT_VARIABLE_FORMAT.matcher(displayName);

        if (!matcher.matches())
            throw new IllegalArgumentException("Bad display string '%s'.".formatted(displayName));

        try {
            connection.connect(new UnixDomainSocketAddress("/tmp/.X11-unix/X0"));
        } catch (final SocketException exception) {
            /* HP UX */
            connection.connect(new UnixDomainSocketAddress("/usr/spool/sockets/X11/X0"));
        }
        return connection;
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    private void connect(final SocketAddress remote) throws IOException {
        socketChannel = SocketChannel.open(remote);
    }
}
