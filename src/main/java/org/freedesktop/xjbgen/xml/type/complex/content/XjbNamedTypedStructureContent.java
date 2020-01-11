package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.type.XjbNamed;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbType;

public abstract class XjbNamedTypedStructureContent extends XjbStructureContent implements XjbNamed {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlAttribute(name = "type", required = true)
    private String xmlType;

    private int byteSize;

    @Override
    public int byteSize() {
        return (byteSize == 0) ? (byteSize = getSrcType().byteSize()) : byteSize;
    }

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }

    public @NotNull String getXmlType() {
        return xmlType;
    }

    private XjbType srcType;

    public @NotNull XjbType getSrcType() {
        if (srcType == null) {
            final XjbModule module = getModule();

            srcType =
                XjbGenerationContext
                    .getInstance()
                    .lookupType(module, getXmlType());
        }
        return srcType;
    }
}
