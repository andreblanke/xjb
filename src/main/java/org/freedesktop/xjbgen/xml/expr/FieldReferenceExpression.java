package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlValue;

import org.jetbrains.annotations.NotNull;

public final class FieldReferenceExpression extends Expression {

    @XmlValue
    private String identifier;

    @Override
    public @NotNull String toString() {
        return getIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }
}
