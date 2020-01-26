package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class OperatorExpression {

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
    private Expression[] expressions = new Expression[2];

    @XmlAttribute(name = "op", required = true)
    private String operator;

    @Override
    public @NotNull String toString() {
        return (expressions[0].toString() + operator + expressions[1].toString());
    }
}
