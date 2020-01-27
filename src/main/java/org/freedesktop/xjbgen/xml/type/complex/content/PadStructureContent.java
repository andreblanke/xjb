package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
            : String.format("/* Skip %1$d byte(s) of padding. */", byteSize());
    }

    /**
     * {@inheritDoc}
     *
     * No source code fragment is returned if this {@code XjbPadStructureContent} is the last
     * {@link StructureContent} of its parent to avoid an unnecessary advancement of the {@code buffer}'s position.
     */
    @Override
    public @Nullable String getAdvanceBufferSrc() {
        return isLastStructureContent()
            ? null
            : super.getAdvanceBufferSrc();
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
