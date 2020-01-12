package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public abstract class XjbXidTypeElement extends XjbTypeElement<XjbModule> {

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return "@%1$s.%2$s int".formatted(getModule().getClassName(), getSrcName());
    }

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.%1$s = %3$s.getInt();";
    }
}
