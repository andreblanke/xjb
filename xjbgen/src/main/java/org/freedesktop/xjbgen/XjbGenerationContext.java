package org.freedesktop.xjbgen;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Import;
import org.freedesktop.xjbgen.xml.Module;
import org.freedesktop.xjbgen.xml.type.Type;

import static java.util.Map.Entry;
import static java.util.stream.Collectors.toMap;

public final class XjbGenerationContext {

    private final Map<String, Module> registeredModules = new HashMap<>();

    private static final XjbGenerationContext INSTANCE = new XjbGenerationContext();

    private XjbGenerationContext() {
    }

    public static XjbGenerationContext getInstance() {
        return INSTANCE;
    }

    public void registerModule(@NotNull final Module module) {
        registeredModules.put(module.getHeader(), module);
    }

    public Module lookupModule(@NotNull final Import xjbImport) {
        return registeredModules.get(xjbImport.getHeader());
    }

    /**
     * Looks up a {@link Type} whose name in the XML protocol descriptions is {@code xmlType}.
     *
     * @param module
     *
     * @param xmlType
     *
     * @return
     *
     * @throws NoSuchElementException If the {@code xmlType} name does not contain a header describing the
     *                                {@link Module} to be searched in and no {@code Type} with the provided name
     *                                {@code xmlType} can be found inside of the given {@code module} or any of the
     *                                {@code Module}s imported by it.
     */
    @NotNull
    public Type lookupType(@NotNull final Module module, @NotNull final String xmlType) {
        final int separatorIndex = xmlType.indexOf(':');

        if (separatorIndex == -1) {
            return Optional
                .ofNullable(
                    module
                        .getRegisteredTypes()
                        .get(xmlType))
                .or(() -> Optional.ofNullable(lookupTypeInImports(module, xmlType)))
                .orElseThrow(() -> new NoSuchElementException(
                    String.format("Could not find type '%1$s' while processing %2$s.xml.", xmlType, module.getHeader())));
        }
        final String header   = xmlType.substring(0, separatorIndex);
        final String typeName = xmlType.substring(separatorIndex + 1);

        return
            registeredModules
                .get(header)
                .getRegisteredTypes()
                .get(typeName);
    }

    /**
     * Looks up a {@link Type} whose name in the XML protocol descriptions is {@code xmlType} defined in the provided
     * {@link Module}'s imports.
     *
     * This function does a shallow search only: types defined in modules which are imported by one of the modules our
     * provided {@code module} imports are not included in the search.
     *
     * @param module
     *
     * @param xmlType
     *
     * @return A {@link Type} object whose XML name matches that of the provided {@code xmlType} defined in any of the
     *         {@link Module}s imported by the provided {@code module}.
     */
    private Type lookupTypeInImports(@NotNull final Module module, @NotNull final String xmlType) {
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
