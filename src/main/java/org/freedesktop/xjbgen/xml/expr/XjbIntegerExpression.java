package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;

@XmlTransient
public abstract class XjbIntegerExpression extends XjbElement<XjbElement<?>> {

    public abstract String toString();

    public abstract int getValue();

    public static final class XjbBitExpression extends XjbIntegerExpression {

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

    public static final class XjbValueExpression extends XjbIntegerExpression {

        @XmlValue
        @XmlValueExtension
        private int literal;

        public XjbValueExpression() {
        }

        public XjbValueExpression(final int literal) {
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
