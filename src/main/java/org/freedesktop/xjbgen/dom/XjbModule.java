package org.freedesktop.xjbgen.dom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.freedesktop.xjbgen.dom.type.XjbEnum;
import org.freedesktop.xjbgen.dom.type.complex.XjbRequest;

@XmlRootElement(name = "xcb")
public final class XjbModule {

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
}
