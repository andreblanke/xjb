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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.util.Strings;
import org.freedesktop.xjbgen.xml.type.XjbAtomicType;
import org.freedesktop.xjbgen.xml.type.XjbEnum;
import org.freedesktop.xjbgen.xml.type.XjbType;
import org.freedesktop.xjbgen.xml.type.XjbTypedef;
import org.freedesktop.xjbgen.xml.type.XjbXidType;
import org.freedesktop.xjbgen.xml.type.XjbXidUnion;
import org.freedesktop.xjbgen.xml.type.complex.XjbRequest;
import org.freedesktop.xjbgen.xml.type.complex.XjbStruct;
import org.freedesktop.xjbgen.xml.type.complex.XjbUnion;
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
public final class XjbModule extends XjbElement<XjbElement<?>> implements PredecessorFunction<XjbModule> {

    @XmlAttribute(required = true)
    private String header;

    @XmlAttribute(name = "extension-name")
    private String extensionName;

    @XmlAttribute(name = "extension-xname")
    private String extensionXName;

    @XmlElement(name = "import")
    private Set<XjbImport> imports = new HashSet<>();

    @XmlElement(name = "struct")
    private List<XjbStruct> structs = new ArrayList<>();

    @XmlElement(name = "union")
    private List<XjbUnion> unions = new ArrayList<>();

    @XmlElement(name = "xidtype")
    private List<XjbXidType> xidTypes = new ArrayList<>();

    @XmlElement(name = "xidunion")
    private List<XjbXidUnion> xidUnions = new ArrayList<>();

    @XmlElement(name = "enum")
    private List<XjbEnum> enums = new ArrayList<>();

    @XmlElement(name = "typedef")
    private List<XjbTypedef> typedefs = new ArrayList<>();

    @XmlElement(name = "request")
    private List<XjbRequest> requests = new ArrayList<>();

    private final Map<String, XjbType> registeredTypes = new HashMap<>(XjbAtomicType.getXmlNameMappings());

    @Override
    public Collection<? extends XjbModule> predecessors() {
        return imports
            .stream()
            .map(XjbGenerationContext.getInstance()::lookupModule)
            .collect(toList());
    }

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        /* Every extension implicitly imports the XProto module. */
        if (isExtension())
            imports.add(XjbImport.XPROTO_IMPORT);
        XjbGenerationContext.getInstance().registerModule(this);
    }

    public void registerType(@NotNull final XjbType type) {
        registerType(type.getXmlName(), type);
    }

    public void registerType(@NotNull final String xmlName, @NotNull final XjbType type) {
        registeredTypes.put(xmlName, type);
    }

    @Contract(pure = true)
    public @NotNull Map<String, XjbType> getRegisteredTypes() {
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

    public List<XjbXidType> getXidTypes() {
        return xidTypes;
    }

    public List<XjbXidUnion> getXidUnions() {
        return xidUnions;
    }

    public List<XjbEnum> getEnums() {
        return enums;
    }

    public List<XjbRequest> getRequests() {
        return requests;
    }
    // </editor-fold>
}
