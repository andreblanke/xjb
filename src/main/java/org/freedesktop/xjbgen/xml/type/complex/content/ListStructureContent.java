package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class ListStructureContent extends FieldStructureContent {

    @XmlValue
    @XmlValueExtension
    private Object lengthExpression;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return "private java.util.List<%1$s> %2$s;".formatted(getSrcType().getBoxedType().getQualifiedSrcName(), getSrcName());
    }

    @Override
    public @NotNull String getFromBytesSrc() {
        return "TODO";
    }
}
