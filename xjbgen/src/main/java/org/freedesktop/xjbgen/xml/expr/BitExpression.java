package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class BitExpression extends IntegerExpression {

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
