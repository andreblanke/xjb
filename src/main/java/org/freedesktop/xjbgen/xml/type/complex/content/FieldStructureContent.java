package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class FieldStructureContent extends NamedTypedStructureContent {

    @XmlAttribute(name = "enum")
    private String enum_;

    @Override
    public String toString() {
        return String.format("private %1$s %2$s;", getSrcType().getQualifiedSrcName(), getSrcName());
    }

    @Override
    @Contract(pure = true)
    public @NotNull String getFromBytesSrc() {
        return String.format("%1$s = %2$s;", getSrcName(), String.format(getSrcType().getFromBytesExpression(), BYTE_BUFFER_NAME));
    }

    public String getEnum() {
        return enum_;
    }
}
