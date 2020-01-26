package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class UnaryOperatorExpression extends Expression {

    @XmlValue
    @XmlValueExtension
    @XmlElements({
        @XmlElement(name = "bit",      type = BitExpression.class),
        @XmlElement(name = "enumref",  type = EnumReferenceExpression.class),
        @XmlElement(name = "fieldref", type = FieldReferenceExpression.class),
        @XmlElement(name = "op",       type = OperatorExpression.class),
        @XmlElement(name = "popcount", type = PopCountExpression.class),
        @XmlElement(name = "unop",     type = UnaryOperatorExpression.class),
        @XmlElement(name = "value",    type = ValueExpression.class)
    })
    private Expression expression;

    @XmlAttribute(name = "op", required = true)
    private String operator;

    @Override
    public @NotNull String toString() {
        return operator + expression.toString();
    }
}
