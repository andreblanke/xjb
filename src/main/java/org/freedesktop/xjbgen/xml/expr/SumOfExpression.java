package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.NotNull;

public final class SumOfExpression extends Expression {

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
    private Expression expression;

    @Override
    public @NotNull String toString() {
        return "TODO";
    }
}
