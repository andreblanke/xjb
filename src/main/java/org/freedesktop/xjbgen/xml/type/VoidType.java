package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class VoidType implements Type {

    private static final Type INSTANCE = new VoidType();

    private VoidType() {
    }

    public static Type getInstance() {
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
