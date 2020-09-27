package org.freedesktop.xjb;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

final class XAuthorityReader {

    private final @NotNull Path authFilePath;

    private XAuthorityReader(@NotNull final Path authFilePath) {
        this.authFilePath = authFilePath;
    }

    static @NotNull Optional<XAuthorityReader> forStandardLocations() {
        final Path xauthority =
            Optional
                .ofNullable(System.getenv("XAUTHORITY"))
                .map(Path::of)
                .orElseGet(() -> Path.of(System.getProperty("user.home"), ".Xauthority"));
        return Files.exists(xauthority) ? Optional.of(new XAuthorityReader(xauthority)) : Optional.empty();
    }

    /**
     * Reads {@link XAuthority} entries from the binary file located at the {@link Path} provided at instantiation time.
     *
     * @return A {@link List} of {@code XAuthority} objects read from the file located at the {@code Path} provided at
     *         instantiation time.
     *
     * @throws InvalidXAuthorityFileException if the binary file is malformed.
     *
     * @throws IOException If the file located at the {@code Path} provided at instantiation time cannot be read.
     */
    @NotNull List<XAuthority> read() throws IOException {
        try (final var channel = FileChannel.open(authFilePath, StandardOpenOption.READ)) {
            final var buffer = ByteBuffer.allocate((int) channel.size());

            channel.read(buffer);
            buffer.rewind();

            final List<XAuthority> entries = new ArrayList<>();
            while (buffer.hasRemaining())
                entries.add(XAuthority.fromByteBuffer(buffer));
            return entries;
        } catch (final BufferUnderflowException exception) {
            throw new InvalidXAuthorityFileException(authFilePath);
        }
    }

    static final class InvalidXAuthorityFileException extends IOException {

        private final @NotNull Path authFilePath;

        InvalidXAuthorityFileException(final @NotNull Path authFilePath) {
            this.authFilePath = authFilePath;
        }

        @NotNull Path getAuthFilePath() {
            return authFilePath;
        }
    }
}
