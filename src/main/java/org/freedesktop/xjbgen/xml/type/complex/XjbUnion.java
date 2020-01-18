package org.freedesktop.xjbgen.xml.type.complex;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;

public final class XjbUnion extends XjbComplexType<XjbModule> {

    @Override
    public @NotNull String getFromBytesSrc() {
        return "%1$s.getInt();";
    }
}
