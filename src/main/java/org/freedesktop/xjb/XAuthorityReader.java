package org.freedesktop.xjb;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class XAuthorityReader {

    private final @NotNull Path authFilePath;

    private static final int FAMILY_LOCAL =    256;
    private static final int FAMILY_WILD  = 65_535;

    private XAuthorityReader(@NotNull final Path authFilePath) {
        this.authFilePath = authFilePath;
    }

    static @Nullable XAuthorityReader forStandardLocations() {
        final Optional<Path> authFilePath =
            Optional
                .ofNullable(System.getenv("XAUTHORITY"))
                .map(Path::of)
                .or(() ->
                    Optional
                        .ofNullable(System.getenv("HOME"))
                        .map(home -> Path.of(home, ".Xauthority")));
        if (authFilePath.isEmpty() || Files.notExists(authFilePath.get()))
            return null;
        return new XAuthorityReader(authFilePath.get());
    }

    @Nullable AuthInfo findEntry(@NotNull final String hostname, final String display) throws IOException {
        try (final var channel = FileChannel.open(authFilePath, StandardOpenOption.READ)) {
            final var buffer = ByteBuffer.allocate((int) channel.size());

            channel.read(buffer);
            buffer.rewind();

            while (buffer.hasRemaining()) {
                final int family = Short.toUnsignedInt(buffer.getShort());
                final String address = readString(buffer);
                final String disp = readString(buffer);
                final String name = readString(buffer);
                final String data = readString(buffer);

                final boolean addressMatch =
                    (family == FAMILY_WILD) || ((family == FAMILY_LOCAL) && hostname.equals(address));
                final boolean displayMatch =
                    (disp.isEmpty() || disp.equals(display));

                if (addressMatch && displayMatch)
                    return new AuthInfo(name, data);
            }
            return null;
        } catch (final BufferUnderflowException exception) {
            return null;
        }
    }

    @Contract("_ -> new")
    private @NotNull String readString(@NotNull final ByteBuffer buffer) {
        final int    length = Short.toUnsignedInt(buffer.getShort());
        final byte[] bytes  = new byte[length];

        buffer.get(bytes);

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
