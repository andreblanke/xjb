package org.freedesktop.xjbgen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbImport;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbAtomicType;
import org.freedesktop.xjbgen.xml.type.XjbType;

public final class XjbGenerationContext {

    private final Map<String, XjbModule> registeredModules = new HashMap<>();

    private static final XjbGenerationContext instance = new XjbGenerationContext();

    private XjbGenerationContext() {
    }

    public static XjbGenerationContext getInstance() {
        return instance;
    }

    @NotNull
    @Contract(" -> new")
    private static Set<XjbType> newDefaultTypeSet() {
        return new HashSet<>(Arrays.asList(XjbAtomicType.values()));
    }

    public void registerModule(@NotNull final XjbModule module) {
        registeredModules.put(module.getHeader(), module);
    }

    public XjbModule lookupModule(@NotNull final XjbImport xjbImport) {
        return registeredModules.get(xjbImport.getHeader());
    }

    @NotNull
    public XjbType lookupType(@NotNull final XjbModule module, @NotNull final String xmlType) {
        final int separatorIndex = xmlType.indexOf(':');

        if (separatorIndex == -1) {
            var type =
                module
                    .getRegisteredTypes()
                    .get(xmlType);
            return (type != null) ? type : lookupTypeInImports(module, xmlType);
        }
        final String header   = xmlType.substring(0, separatorIndex);
        final String typeName = xmlType.substring(separatorIndex + 1);

        return
            registeredModules
                .get(header)
                .getRegisteredTypes()
                .values()
                .stream()
                .filter(type -> typeName.equals(type.getXmlName()))
                .findFirst()
                .orElseThrow();
    }

    private XjbType lookupTypeInImports(@NotNull final XjbModule module, @NotNull final String xmlType) {
        return module
            .predecessors()
            .stream()
            .map(XjbModule::getRegisteredTypes)
            .flatMap(types -> types.values().stream())
            .filter(type -> Objects.equals(xmlType, type.getXmlName()))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(module.getHeader() + " " + xmlType));
    }
}
