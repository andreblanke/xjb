package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull String getFromBytesExpression() {
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
