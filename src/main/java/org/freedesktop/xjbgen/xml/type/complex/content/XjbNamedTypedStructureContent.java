package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbType;

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
        final XjbModule module = getModule();

        return
            XjbGenerationContext
                .getInstance()
                .lookupType(module, getXmlType());
    }
}
