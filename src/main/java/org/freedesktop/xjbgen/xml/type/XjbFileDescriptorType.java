package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public final class XjbFileDescriptorType implements XjbType {

    private static final XjbType INSTANCE = new XjbFileDescriptorType();

    private XjbFileDescriptorType() {
    }

    public static XjbType getInstance() {
        return INSTANCE;
    }

    @Override
    public int byteSize() {
        return Integer.BYTES;
    }

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.getInt();";
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return "/* fd */ int";
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getXmlName() {
        return "fd";
    }
}
