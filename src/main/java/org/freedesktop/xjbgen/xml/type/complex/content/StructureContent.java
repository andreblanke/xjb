package org.freedesktop.xjbgen.xml.type.complex.content;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.type.Type;
import org.freedesktop.xjbgen.xml.type.complex.ComplexType;

public abstract class StructureContent extends Element<ComplexType<?>> {

    public static final String BYTE_BUFFER_NAME = "buffer";

    public abstract int byteSize();

    /**
     * Returns the source code fragment used to advance the position of a {@link java.nio.ByteBuffer} named
     * {@code buffer} by the size of this {@code XjbStructureContent}.
     *
     * @return A source code fragment which advances the {@link java.nio.ByteBuffer#position()} of the {@code buffer}
     *         variable.
     */
    public @Nullable String getAdvanceBufferSrc() {
        return String.format("%1$s.position(buffer.position() + %2$d);", BYTE_BUFFER_NAME, byteSize());
    }

    /** @see Type#getFromBytesExpression()  */
    public abstract @NotNull String getFromBytesSrc();
}
