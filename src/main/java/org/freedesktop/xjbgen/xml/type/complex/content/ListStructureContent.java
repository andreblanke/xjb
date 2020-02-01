package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.expr.*;

public final class ListStructureContent extends FieldStructureContent {

    @XmlElements({
        @XmlElement(name = "bit",         type = BitExpression.class),
        @XmlElement(name = "enumref",     type = EnumReferenceExpression.class),
        @XmlElement(name = "fieldref",    type = FieldReferenceExpression.class),
        @XmlElement(name = "listelement", type = ListElementReferenceExpression.class),
        @XmlElement(name = "op",          type = OperatorExpression.class),
        @XmlElement(name = "paramref",    type = ParameterReferenceExpression.class),
        @XmlElement(name = "popcount",    type = PopCountExpression.class),
        @XmlElement(name = "sumof",       type = SumOfExpression.class),
        @XmlElement(name = "unop",        type = UnaryOperatorExpression.class),
        @XmlElement(name = "value",       type = ValueExpression.class)
    })
    private Expression lengthExpression;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return String.format("private java.util.List<%1$s> %2$s;", getSrcType().getBoxedType().getQualifiedSrcName(), getSrcName());
    }

    @Override
    public @NotNull String getFromBytesSrc() {
        return "TODO " + lengthExpression.toString();
    }
}
