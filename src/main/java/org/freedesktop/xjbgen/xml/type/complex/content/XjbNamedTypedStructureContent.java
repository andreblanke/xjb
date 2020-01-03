package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbType;

public abstract class XjbNamedTypedStructureContent extends XjbNamedStructureContent {

    @XmlAttribute(name = "type", required = true)
    private String xmlType;

    private int byteSize;

    @Override
    public int byteSize() {
        return (byteSize == 0) ? (byteSize = getSrcType().byteSize()) : byteSize;
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
