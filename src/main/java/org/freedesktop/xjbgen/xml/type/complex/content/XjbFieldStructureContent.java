package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class XjbFieldStructureContent extends XjbNamedTypedStructureContent {

    @XmlAttribute(name = "enum")
    private String enum_;

    @Override
    public String toString() {
        return "private %1$s %2$s;".formatted(getSrcType().getQualifiedSrcName(), getSrcName());
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getFromBytesSrc() {
        return "reply." + getSrcName() + " = " + getSrcType().getFromBytesSrc(this).formatted("buffer");
    }

    public String getEnum() {
        return enum_;
    }
}
