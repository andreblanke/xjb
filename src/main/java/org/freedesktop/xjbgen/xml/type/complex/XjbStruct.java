package org.freedesktop.xjbgen.xml.type.complex;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public final class XjbStruct extends XjbComplexType<XjbModule> {

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.%2$s = " + content.getSrcType().toString() + ".fromBytes(%3$s.slice());";
    }
}
