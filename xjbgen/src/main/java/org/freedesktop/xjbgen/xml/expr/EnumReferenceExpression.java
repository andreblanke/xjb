package org.freedesktop.xjbgen.xml.expr;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.type.Type;

public final class EnumReferenceExpression extends Expression {

    @XmlValue
    @XmlValueExtension
    private String enumItemIdentifier;

    @XmlAttribute(required = true)
    private String ref;

    @Override
    public @NotNull String toString() {
        final Type enumType = XjbGenerationContext.getInstance().lookupType(getModule(), ref);

        return enumType.getQualifiedSrcName() + '.' + enumItemIdentifier;
    }
}
