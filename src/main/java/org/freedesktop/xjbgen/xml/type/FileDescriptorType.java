package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class FileDescriptorType implements Type {

    private static final Type INSTANCE = new FileDescriptorType();

    private FileDescriptorType() {
    }

    public static Type getInstance() {
        return INSTANCE;
    }

    @Override
    public int byteSize() {
        return Integer.BYTES;
    }

    @Override
    public @NotNull String getFromBytesExpression() {
        return "%1$s.getInt()";
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
