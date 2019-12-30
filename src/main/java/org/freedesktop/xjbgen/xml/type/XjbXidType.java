package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.XjbModule;

public class XjbXidType extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name")
    private String xmlName;

    @Override
    public int byteSize() {
        return 0;
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
}
