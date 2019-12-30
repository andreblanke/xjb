package org.freedesktop.xjbgen;

import java.util.Map;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.XjbImport;
import org.freedesktop.xjbgen.dom.XjbModule;

public final class XjbGenerationContext {

    private final Map<String, XjbModule> registeredModules;

    XjbGenerationContext(@NotNull final Map<String, XjbModule> registeredModules) {
        this.registeredModules = registeredModules;
    }

    public XjbModule lookupModule(@NotNull final XjbImport xjbImport) {
        return registeredModules.get(xjbImport.getHeader());
    }
}
