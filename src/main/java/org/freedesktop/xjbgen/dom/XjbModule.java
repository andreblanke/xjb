package org.freedesktop.xjbgen.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.type.XjbAtomicType;
import org.freedesktop.xjbgen.dom.type.XjbEnum;
import org.freedesktop.xjbgen.dom.type.XjbType;
import org.freedesktop.xjbgen.dom.type.complex.XjbRequest;

@XmlRootElement(name = "xcb")
public final class XjbModule extends XjbElement<XjbElement<?>> {

    @XmlAttribute(required = true)
    private String header;

    @XmlAttribute(name = "extension-name")
    private String extensionName;

    @XmlAttribute(name = "extension-xname")
    private String extensionXName;

    @XmlElement(name = "enum")
    private List<XjbEnum> enums = new ArrayList<>();

    @XmlElement(name = "request")
    private List<XjbRequest> requests = new ArrayList<>();

    private final Map<String, XjbType> registeredTypes = new HashMap<>();

    public XjbModule() {
        registeredTypes.putAll(XjbAtomicType.getXmlNameMappings());
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
