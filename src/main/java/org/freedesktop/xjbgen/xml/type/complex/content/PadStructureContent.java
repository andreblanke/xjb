package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class PadStructureContent extends StructureContent {

    @XmlAttribute
    private int bytes;

    @Override
    public int byteSize() {
        return bytes;
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getFromBytesSrc() {
        return isLastStructureContent()
            ? "/* Skipping 8 byte(s) of padding at end of buffer without advancing. */"
            : String.format("\n            /* Skip %1$d byte(s) of padding. */\n            buffer.position(buffer.position() + %1$d);", byteSize());
    }

    /**
     * Checks whether this {@code XjbPadStructureContent} is the last {@link StructureContent} of its parent.
     *
     * @return {@code true} if this {@code XjbPadStructureContent} is the last {@code XjbStructureContent} of its
     *         parent, otherwise {@code false}.
     */
    private boolean isLastStructureContent() {
        return getParent().getContents().indexOf(this) == (getParent().getContents().size() - 1);
    }
}
