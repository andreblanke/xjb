package org.freedesktop.xjbgen.xml.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public final class XjbXidUnion extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlElement(name = "type", required = true)
    private List<Type> types;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return "@%s int".formatted(getSrcName());
    }

    // <editor-fold desc="XjbType">
    @Override
    public int byteSize() {
        return XjbXidType.BYTE_SIZE;
    }

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.%2$s = %3$s.getInt();".formatted(content.getSrcName());
    }

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }
    // </editor-fold>

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
