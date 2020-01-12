package org.freedesktop.xjbgen.xml.type;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;

public final class XjbXidUnion extends XjbXidTypeElement {

    @XmlElement(name = "type", required = true)
    private List<Type> types;

    public @NotNull List<Type> getTypes() {
        return types;
    }

    public static final class Type extends XjbElement<XjbXidUnion> {

        @XmlValue
        @XmlValueExtension
        private String value;

        @Override
        public String toString() {
            return String.format("%1$s.%2$s", getModule().getClassName(), getValue());
        }

        private String getValue() {
            return value;
        }
    }
}
