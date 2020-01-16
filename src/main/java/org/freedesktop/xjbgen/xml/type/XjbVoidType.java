package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public final class XjbVoidType implements XjbType {

    private static final XjbType INSTANCE = new XjbVoidType();

    private XjbVoidType() {
    }

    public static XjbType getInstance() {
        return INSTANCE;
    }

    @Override
    public int byteSize() {
        return -1;
    }

    @Override
    public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return "java.lang.Object";
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getXmlName() {
        return "void";
    }
}
