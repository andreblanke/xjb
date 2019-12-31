package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

public final class XjbXidUnion extends XjbXidTypeElement {

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
