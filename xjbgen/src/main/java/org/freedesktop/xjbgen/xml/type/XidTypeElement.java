package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Module;

public abstract class XidTypeElement extends TypeElement<Module> {

    @Override
    public int byteSize() {
        return AtomicType.INT_32.byteSize();
    }

    @Override
    public @NotNull String getFromBytesExpression() {
        return AtomicType.INT_32.getFromBytesExpression();
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return String.format("@%s int", super.getQualifiedSrcName());
    }
}
