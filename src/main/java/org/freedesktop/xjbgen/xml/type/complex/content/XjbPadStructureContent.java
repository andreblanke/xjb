package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class XjbPadStructureContent extends XjbStructureContent {

    @XmlAttribute
    private int bytes;

    @Override
    public int byteSize() {
        return bytes;
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getFromBytesSrc() {
        return "/* Skip %1$d byte(s) of padding. */".formatted(byteSize());
    }
}
