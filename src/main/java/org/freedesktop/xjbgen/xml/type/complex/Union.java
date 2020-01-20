package org.freedesktop.xjbgen.xml.type.complex;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Module;

public final class Union extends ComplexType<Module> {

    @Override
    public @NotNull String getFromBytesExpression() {
        return "%1$s.getInt()";
    }
}
