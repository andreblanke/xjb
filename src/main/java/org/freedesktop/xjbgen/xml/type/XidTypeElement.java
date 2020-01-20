package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Module;

public abstract class XidTypeElement extends TypeElement<Module> {

    private static final int BYTE_SIZE = Integer.BYTES;

    // <editor-fold desc="XjbType">
    @Override
    public int byteSize() {
        return BYTE_SIZE;
    }

    @Override
    public @NotNull String getFromBytesExpression() {
        return "%1$s.getInt()";
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return "@%s int".formatted(super.getQualifiedSrcName());
    }

    @Override
    public @NotNull Type getBoxedType() {
        System.out.println(getSrcName());
        return AtomicType.INT_32.getBoxedType();
    }
    // </editor-fold>
}
