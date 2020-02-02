package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class FieldReferenceExpression extends Expression {

    @XmlValue
    @XmlValueExtension
    private String identifier;

    @Override
    public @NotNull String toString() {
        return "this." + getIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }
}
