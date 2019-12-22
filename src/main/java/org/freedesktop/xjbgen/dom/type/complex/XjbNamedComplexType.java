package org.freedesktop.xjbgen.dom.type.complex;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.type.XjbNamed;

public abstract class XjbNamedComplexType extends XjbComplexType implements XjbNamed {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }
}
