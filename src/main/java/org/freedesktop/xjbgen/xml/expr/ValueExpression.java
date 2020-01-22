package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class ValueExpression extends IntegerExpression {

    @XmlValue
    @XmlValueExtension
    private int literal;

    public ValueExpression() {
    }

    public ValueExpression(final int literal) {
        this.literal = literal;
    }

    @Override
    public @NotNull String toString() {
        return Integer.toString(literal);
    }

    @Override
    public int getValue() {
        return literal;
    }
}
