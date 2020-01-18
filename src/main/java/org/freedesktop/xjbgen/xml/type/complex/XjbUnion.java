package org.freedesktop.xjbgen.xml.type.complex;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public final class XjbUnion extends XjbComplexType<XjbModule> {

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.getInt();";
    }
}
