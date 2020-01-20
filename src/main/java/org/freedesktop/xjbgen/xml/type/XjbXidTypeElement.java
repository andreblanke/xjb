package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;

public abstract class XjbXidTypeElement extends XjbTypeElement<XjbModule> {

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
    public @NotNull XjbType getBoxedType() {
        System.out.println(getSrcName());
        return XjbAtomicType.INT_32.getBoxedType();
    }
    // </editor-fold>
}
