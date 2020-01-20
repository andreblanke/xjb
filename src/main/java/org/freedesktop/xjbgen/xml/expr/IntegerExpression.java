package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;

@XmlTransient
public abstract class IntegerExpression extends Element<Element<?>> {

    public abstract String toString();

    public abstract int getValue();

    public static final class BitExpression extends IntegerExpression {

        @XmlValue
        @XmlValueExtension
        private int shiftAmount;

        @Override
        public @NotNull String toString() {
            return String.format("1 << %d", shiftAmount);
        }

        @Override
        public int getValue() {
            return (1 << shiftAmount);
        }
    }

    public static final class ValueExpression extends IntegerExpression {

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
}
