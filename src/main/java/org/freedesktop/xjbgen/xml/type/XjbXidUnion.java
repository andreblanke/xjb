package org.freedesktop.xjbgen.xml.type;

import java.util.List;
import java.util.StringJoiner;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.XjbModule;

public final class XjbXidUnion extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlElement(name = "type", required = true)
    private List<Type> types;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        final String xidUnionValue;
        if (types.size() == 1) {
            xidUnionValue = "\"" + getSrcName() + "\"";
        } else {
            final var joiner = new StringJoiner(", ", "{", "}");
            for (Type type : types)
                joiner.add("\"" + type.toString() + "\"");
            xidUnionValue = joiner.toString();
        }
        return String.format("@XidUnion(%s) int", xidUnionValue);
    }

    // <editor-fold desc="XjbType">
    @Override
    public int byteSize() {
        return XjbXidType.BYTE_SIZE;
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
    // </editor-fold>

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
