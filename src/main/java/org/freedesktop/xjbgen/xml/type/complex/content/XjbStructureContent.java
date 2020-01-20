package org.freedesktop.xjbgen.xml.type.complex.content;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.type.complex.XjbComplexType;

public abstract class XjbStructureContent extends XjbElement<XjbComplexType<?>> {

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

    /** @see org.freedesktop.xjbgen.xml.type.XjbType#getFromBytesExpression()  */
    public abstract @NotNull String getFromBytesSrc();
}
