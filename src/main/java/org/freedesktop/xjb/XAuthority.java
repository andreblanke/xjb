package org.freedesktop.xjb;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

final class XAuthority extends AuthInfo {

    private final @NotNull Family family;

    private final @NotNull String address;

    private final @NotNull String display;

    private XAuthority(@NotNull final Family family,
                       @NotNull final String address,
                       @NotNull final String display,
                       @NotNull final String name,
                       @NotNull final String data) {
        super(name, data);

        this.family  = family;
        this.address = address;
        this.display = display;
    }

    /**
     * Reads and returns a single {@code XAuthority} object from the provided {@code buffer}.
     *
     * @param buffer The {@link ByteBuffer} from which to read a single {@code XAuthority} object.
     *
     * @return The {@code XAuthority} object that was stored inside the provided {@link ByteBuffer}.
     *
     * @throws BufferUnderflowException If the provided {@code buffer} does not contain a valid, serialized
     *                                  {@code XAuthority} object.
     */
    static @NotNull XAuthority fromByteBuffer(@NotNull final ByteBuffer buffer) {
        final int family = Short.toUnsignedInt(buffer.getShort());
        final String address = readCountedString(buffer);
        final String display = readCountedString(buffer);
        final String name    = readCountedString(buffer);
        final String data    = readCountedString(buffer);

        return new XAuthority(Family.valueOf(family), address, display, name, data);
    }

    @Contract("_ -> new")
    private static @NotNull String readCountedString(final @NotNull ByteBuffer buffer) {
        final int    length = Short.toUnsignedInt(buffer.getShort());
        final byte[] bytes  = new byte[length];

        buffer.get(bytes);

        return new String(bytes, StandardCharsets.ISO_8859_1);
    }

    boolean isMatch(final String address, final String display) {
        final boolean addressMatch =
            (getFamily() == Family.WILD)
                || ((getFamily() == Family.LOCAL) && (getAddress().equals(address)));
        final boolean displayMatch =
            getDisplay().isEmpty() || getDisplay().equals(display);
        return (addressMatch && displayMatch);
    }

    @NotNull Family getFamily() {
        return family;
    }

    @NotNull String getAddress() {
        return address;
    }

    @NotNull String getDisplay() {
        return display;
    }

    enum Family {
        /**
         * Refers to any family of connections which use a non-network method of communication, such as Unix sockets,
         * shared memory, or loopback serial line.
         */
        LOCAL  (   256),
        WILD   (65_535),
        UNKNOWN(     0);

        private final int value;

        Family(final int value) {
            this.value = value;
        }

        static @NotNull Family valueOf(final int value) {
            for (var family : values()) {
                if (family.getValue() == value)
                    return family;
            }
            return UNKNOWN;
        }

        int getValue() {
            return value;
        }
    }
}
