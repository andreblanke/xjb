package org.freedesktop.xjbgen.xml.type.complex.content;

import org.freedesktop.xjbgen.xml.type.Type;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.type.complex.ComplexType;

public abstract class StructureContent extends Element<ComplexType<?>> {

    public abstract int byteSize();

    /**
     * Returns the source code fragment used to advance the position of a {@link java.nio.ByteBuffer} named
     * {@code buffer} by the size of this {@code XjbStructureContent}.
     *
     * @return A source code fragment which advances the {@link java.nio.ByteBuffer#position()} of the {@code buffer}
     *         variable.
     */
    public @NotNull String getAdvanceBufferSrc() {
        return "buffer.position(buffer.position() + %d);".formatted(byteSize());
    }

    /** @see Type#getFromBytesExpression()  */
    public abstract @NotNull String getFromBytesSrc();
}
