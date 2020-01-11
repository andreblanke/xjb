package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class XjbFieldStructureContent extends XjbNamedTypedStructureContent {

    @XmlAttribute(name = "enum")
    private String enum_;

    @Override
    @Contract(pure = true)
    public @NotNull String getFromBytesSrc() {
        return getSrcType().getFromBytesSrc(this).formatted("reply", getSrcName(), "buffer");
    }

    public String getEnum() {
        return enum_;
    }
}
