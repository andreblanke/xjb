package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.Module;
import org.freedesktop.xjbgen.xml.type.Named;
import org.freedesktop.xjbgen.xml.type.Type;

public abstract class NamedTypedStructureContent extends StructureContent implements Named {

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

    private Type srcType;

    public @NotNull Type getSrcType() {
        if (srcType == null) {
            final Module module = getModule();

            srcType =
                XjbGenerationContext
                    .getInstance()
                    .lookupType(module, getXmlType());
        }
        return srcType;
    }
}
