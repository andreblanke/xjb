package org.freedesktop.xjbgen.xml.expr;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;

public abstract class Expression extends Element<Element<?>> {

    public abstract @NotNull String toString();
}
