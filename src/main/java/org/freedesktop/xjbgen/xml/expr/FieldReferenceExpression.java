package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlValue;

public final class FieldReferenceExpression {

    @XmlValue
    private String identifier;

    public String getIdentifier() {
        return identifier;
    }
}
