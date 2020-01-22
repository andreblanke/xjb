package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.expr.Expression;
import org.freedesktop.xjbgen.xml.expr.FieldReferenceExpression;
import org.freedesktop.xjbgen.xml.expr.OperatorExpression;

public final class ListStructureContent extends FieldStructureContent {

    @XmlValue
    @XmlValueExtension
    @XmlElements({
        @XmlElement(name = "fieldref", type = FieldReferenceExpression.class),
        @XmlElement(name = "op",       type = OperatorExpression.class)
    })
    private Expression lengthExpression;

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
