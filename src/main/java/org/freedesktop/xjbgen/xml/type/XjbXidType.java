package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.XjbModule;

public class XjbXidType extends XjbElement<XjbModule> implements XjbType {

    @XmlAttribute
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
