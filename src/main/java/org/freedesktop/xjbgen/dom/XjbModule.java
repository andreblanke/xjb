package org.freedesktop.xjbgen.dom;

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

import org.freedesktop.xjbgen.dom.type.XjbAtomicType;
import org.freedesktop.xjbgen.dom.type.XjbEnum;
import org.freedesktop.xjbgen.dom.type.XjbType;
import org.freedesktop.xjbgen.dom.type.complex.XjbRequest;
import org.freedesktop.xjbgen.util.PredecessorFunction;

/**
 * This class implements the {@link PredecessorFunction} interface to allow iteration of a collection of
 * {@code XjbModule} instance in topological order via the {@link org.freedesktop.xjbgen.util.TopologicalOrderIterator},
 * with the predecessors being the {@code XjbModule} objects this module depends on.
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

    @XmlElement(name = "enum")
    private List<XjbEnum> enums = new ArrayList<>();

    @XmlElement(name = "request")
    private List<XjbRequest> requests = new ArrayList<>();

    private final Map<String, XjbType> registeredTypes = new HashMap<>();

    public XjbModule() {
        registeredTypes.putAll(XjbAtomicType.getXmlNameMappings());
    }

    @Override
    public Collection<? extends XjbModule> predecessors() {
        return null;
    }

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        /* Every extension implicitly imports the XProto module. */
        if (isExtension())
            imports.add(XjbImport.XPROTO_IMPORT);
    }

    public void registerType(final String xmlName, final XjbType type) {
        registeredTypes.put(xmlName, type);
    }

    public String getClassName() {
        return isExtension() ? extensionName : "XProto";
    }

    public boolean isExtension() {
        return !header.equals("xproto");
    }

    public String getHeader() {
        return header;
    }

    public Set<XjbImport> getImports() {
        return imports;
    }

    public List<XjbEnum> getEnums() {
        return enums;
    }

    public List<XjbRequest> getRequests() {
        return requests;
    }

    @Contract(pure = true)
    public @NotNull Map<String, XjbType> getRegisteredTypes() {
        return Collections.unmodifiableMap(registeredTypes);
    }
}
