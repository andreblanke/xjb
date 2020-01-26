package org.freedesktop.xjb;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.nio.channels.UnixDomainSocketAddress;
import java.util.regex.Pattern;

public final class XjbConnection implements AutoCloseable {

    private SocketChannel socketChannel;

    static final Pattern DISPLAY_ENVIRONMENT_VARIABLE_FORMAT =
        Pattern.compile("^(?<hostname>.+)?:(?<display>[^\\.]+)(?:\\.(?<screen>.+))?$");

    private XjbConnection() {
    }

    public static XjbConnection establish() {
        return establish(System.getenv("DISPLAY"));
    }

    public static XjbConnection establish(final String displayName) {
        return establish(displayName, null);
    }

    public static XjbConnection establish(final String displayName, final XjbAuthInfo authInfo) {
        try {
            return new XjbConnection().connect(new UnixDomainSocketAddress("/tmp/.X11-unix/X0"));
        } catch (final SocketException exception) {
            /* HP UX */
            return new XjbConnection().connect(new UnixDomainSocketAddress("/usr/spool/sockets/X11/X0"));
        }
    }

    @Override
    public void close() {
    }

    private void connect(final SocketAddress remote) throws IOException {
        socketChannel = SocketChannel.open(remote);
    }
}
