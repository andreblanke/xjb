package org.freedesktop.xjbgen;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbImport;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbType;

import static java.util.Map.Entry;
import static java.util.stream.Collectors.toMap;

public final class XjbGenerationContext {

    private final Map<String, XjbModule> registeredModules = new HashMap<>();

    private static final XjbGenerationContext INSTANCE = new XjbGenerationContext();

    private XjbGenerationContext() {
    }

    public static XjbGenerationContext getInstance() {
        return INSTANCE;
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
            return Optional
                .ofNullable(
                    module
                        .getRegisteredTypes()
                        .get(xmlType))
                .or(() -> Optional.ofNullable(lookupTypeInImports(module, xmlType)))
                .orElseThrow(() -> new NoSuchElementException(
                    "Could not find type '%1$s' while processing %2$s.xml.".formatted(xmlType, module.getHeader())));
        }
        final String header   = xmlType.substring(0, separatorIndex);
        final String typeName = xmlType.substring(separatorIndex + 1);

        return
            registeredModules
                .get(header)
                .getRegisteredTypes()
                .get(typeName);
    }

    private XjbType lookupTypeInImports(@NotNull final XjbModule module, @NotNull final String xmlType) {
        return module
            .predecessors()
            .stream()
            .flatMap(xjbModule ->
                xjbModule
                    .getRegisteredTypes()
                    .entrySet()
                    .stream())
            .collect(toMap(Entry::getKey, Entry::getValue, (left, right) -> left))
            .get(xmlType);
    }
}
