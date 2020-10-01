package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.NotNull;

public final class ParameterReferenceExpression extends Expression {

    @XmlAttribute(name = "type", required = true)
    private String xmlType;

    @Override
    public @NotNull String toString() {
        return "TODO";
    }
}
