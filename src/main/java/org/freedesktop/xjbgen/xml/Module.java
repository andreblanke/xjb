package org.freedesktop.xjbgen.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.freedesktop.xjbgen.xml.type.Enum;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.GenerationContext;
import org.freedesktop.xjbgen.util.Strings;
import org.freedesktop.xjbgen.xml.type.*;
import org.freedesktop.xjbgen.xml.type.complex.*;
import org.freedesktop.xjbgen.util.PredecessorFunction;

import static java.util.stream.Collectors.toList;

/**
 * This class implements the {@link PredecessorFunction} interface to allow iteration of a collection of
 * {@code XjbModule} instance in topological order via the {@link org.freedesktop.xjbgen.util.TopologicalOrderIterator},
 * with the predecessors being the {@code XjbModule} objects this module depends on.
 *
 * @see org.freedesktop.xjbgen.util.TopologicalOrderIterator
 */
@XmlRootElement(name = "xcb")
public final class Module extends Element<Element<?>> implements PredecessorFunction<Module> {

    @XmlAttribute(required = true)
    private String header;

    @XmlAttribute(name = "extension-name")
    private String extensionName;

    @XmlAttribute(name = "extension-xname")
    private String extensionXName;

    @XmlElement(name = "import")
    private Set<Import> imports = new HashSet<>();

    @XmlElement(name = "struct")
    private List<Struct> structs = new ArrayList<>();

    @XmlElement(name = "union")
    private List<Union> unions = new ArrayList<>();

    @XmlElement(name = "eventstruct")
    private List<EventStruct> eventStructs = new ArrayList<>();

    @XmlElement(name = "xidtype")
    private List<XidType> xidTypes = new ArrayList<>();

    @XmlElement(name = "xidunion")
    private List<XidUnion> xidUnions = new ArrayList<>();

    @XmlElement(name = "enum")
    private List<Enum> enums = new ArrayList<>();

    @XmlElement(name = "typedef")
    private List<Typedef> typedefs = new ArrayList<>();

    @XmlElement(name = "request")
    private List<Request> requests = new ArrayList<>();

    private final Map<String, Type> registeredTypes = new HashMap<>(AtomicType.getXmlNameMappings());

    public Module() {
        registeredTypes.put(
            FileDescriptorType
                .getInstance()
                .getXmlName(),
            FileDescriptorType.getInstance());
        registeredTypes.put(
            VoidType
                .getInstance()
                .getXmlName(),
            VoidType.getInstance());
    }

    @Override
    public Collection<? extends Module> predecessors() {
        return imports
            .stream()
            .map(GenerationContext.getInstance()::lookupModule)
            .collect(toList());
    }

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        /* Every extension implicitly imports the XProto module. */
        if (isExtension())
            imports.add(Import.XPROTO_IMPORT);
        GenerationContext.getInstance().registerModule(this);
    }

    public void registerType(@NotNull final Type type) {
        registerType(type.getXmlName(), type);
    }

    public void registerType(@NotNull final String xmlName, @NotNull final Type type) {
        registeredTypes.put(xmlName, type);
    }

    @Contract(pure = true)
    public @NotNull Map<String, Type> getRegisteredTypes() {
        return Collections.unmodifiableMap(registeredTypes);
    }

    /**
     * Returns the name of the Java class which is to be generated from this {@code XjbModule}.
     *
     * The name of the Java class returned by this method resembles the capitalized {@link #extensionName} if this
     * {@code XjbModule} describes an X server extension according to {@link #isExtension()}, or {@code XProto} if
     * that is not the case.
     *
     * @return The name of the Java class generated from this object.
     */
    public @NotNull String getClassName() {
        return isExtension() ? Strings.capitalize(extensionName) : "XProto";
    }

    /**
     * Returns whether this {@code XjbModule} describes an actual X server extension or the module associated with the
     * {@code xproto.xml} protocol description.
     *
     * @return {@code true} if this {@code XjbModule} describes an X server extension, otherwise {@code false}.
     */
    public boolean isExtension() {
        return !header.equals("xproto");
    }

    // <editor-fold desc="XML getters">
    public String getHeader() {
        return header;
    }

    public List<Struct> getStructs() {
        return structs;
    }

    public List<EventStruct> getEventStructs() {
        return eventStructs;
    }

    public List<XidType> getXidTypes() {
        return xidTypes;
    }

    public List<XidUnion> getXidUnions() {
        return xidUnions;
    }

    public List<Enum> getEnums() {
        return enums;
    }

    public List<Request> getRequests() {
        return requests;
    }
    // </editor-fold>
}
