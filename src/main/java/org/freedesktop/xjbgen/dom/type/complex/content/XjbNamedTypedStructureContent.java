package org.freedesktop.xjbgen.dom.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

public abstract class XjbNamedTypedStructureContent extends XjbNamedStructureContent {

    @XmlAttribute(name = "type", required = true)
    private String xmlType;

    public @NotNull String getXmlType() {
        return xmlType;
    }

    public @NotNull String getSrcType() {
        return getXmlType();
    }
}
