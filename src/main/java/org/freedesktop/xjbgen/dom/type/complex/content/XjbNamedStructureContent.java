package org.freedesktop.xjbgen.dom.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.type.XjbNamed;

public abstract class XjbNamedStructureContent extends XjbStructureContent implements XjbNamed {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }
}
