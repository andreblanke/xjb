package org.freedesktop.xjbgen.dom.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.type.XjbType;

public abstract class XjbNamedTypedStructureContent extends XjbNamedStructureContent {

    @XmlAttribute(name = "type", required = true)
    private String xmlType;

    @Override
    public int byteSize() {
        return getSrcType().byteSize();
    }

    public @NotNull String getXmlType() {
        return xmlType;
    }

    public @NotNull XjbType getSrcType() {
        return getModule().getRegisteredTypes().get(getXmlType());
    }
}
