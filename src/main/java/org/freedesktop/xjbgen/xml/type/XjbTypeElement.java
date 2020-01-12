package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.XjbElement;

public abstract class XjbTypeElement<P extends XjbElement<?>> extends XjbElement<P> implements XjbType {

    @XmlAttribute(name = "name")
    private String xmlName;

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object Parent) {
        getModule().registerType(this);
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
}
