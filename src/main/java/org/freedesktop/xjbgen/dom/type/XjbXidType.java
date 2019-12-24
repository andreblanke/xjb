package org.freedesktop.xjbgen.dom.type;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.dom.XjbElement;
import org.freedesktop.xjbgen.dom.XjbModule;

public class XjbXidType extends XjbElement<XjbModule> implements XjbNamed, XjbType {

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

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        getModule().registerType(getXmlName(), this);
    }
}
