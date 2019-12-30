package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.XjbModule;

public final class XjbXidUnion extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @Override
    public int byteSize() {
        return XjbXidType.BYTE_SIZE;
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
}
