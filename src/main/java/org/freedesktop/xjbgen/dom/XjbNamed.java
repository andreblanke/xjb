package org.freedesktop.xjbgen.dom;

import org.jetbrains.annotations.NotNull;

public interface XjbNamed {

    @NotNull String getXmlName();

    default @NotNull String getSrcName() {
        return getXmlName();
    }
}
