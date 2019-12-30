package org.freedesktop.xjbgen;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbImport;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbAtomicType;
import org.freedesktop.xjbgen.xml.type.XjbType;

public final class XjbGenerationContext {

    private final Map<String, XjbModule> registeredModules = new HashMap<>();

    private final Map<XjbModule, Set<XjbType>> registeredTypes = new IdentityHashMap<>();

    private static final XjbGenerationContext instance = new XjbGenerationContext();

    private XjbGenerationContext() {
    }

    public static XjbGenerationContext getInstance() {
        return instance;
    }

    public void registerModule(@NotNull final XjbModule module) {
        registeredModules.put(module.getHeader(), module);
    }

    public XjbModule lookupModule(@NotNull final XjbImport xjbImport) {
        return registeredModules.get(xjbImport.getHeader());
    }

    public void registerType(@NotNull final XjbModule module, @NotNull final XjbType type) {
        registeredTypes.computeIfAbsent(module, key -> new HashSet<>(Arrays.asList(XjbAtomicType.values()))).add(type);
    }

    @NotNull
    public XjbType lookupType(@NotNull final XjbModule module, @NotNull final String xmlType) {
        final int separatorIndex = xmlType.indexOf(':');

        if (separatorIndex == -1) {
            return
                registeredTypes
                    .computeIfAbsent(module, key -> new HashSet<>(Arrays.asList(XjbAtomicType.values())))
                    .stream()
                    .filter(type -> xmlType.equals(type.getXmlName()))
                    .findFirst()
                    .get();
        }
        final String header   = xmlType.substring(0, separatorIndex);
        final String typeName = xmlType.substring(separatorIndex + 1);

        return
            registeredTypes
                .get(registeredModules.get(header))
                .stream()
                .filter(type -> typeName.equals(type.getXmlName()))
                .findFirst()
                .get();
    }
}
