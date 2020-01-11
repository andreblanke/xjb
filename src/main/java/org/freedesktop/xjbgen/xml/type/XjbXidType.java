package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public class XjbXidType extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name")
    private String xmlName;

    static int BYTE_SIZE = Integer.BYTES;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return "@%1$s.%2$s int".formatted(getModule().getClassName(), getSrcName());
    }

    @Override
    public int byteSize() {
        return BYTE_SIZE;
    }

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.%1$s = %3$s.getInt();";
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
}
