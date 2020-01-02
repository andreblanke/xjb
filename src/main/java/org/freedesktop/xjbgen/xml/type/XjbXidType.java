package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.annotation.XmlAttribute;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;

public class XjbXidType extends XjbTypeElement<XjbModule> {

    @XmlAttribute(name = "name")
    private String xmlName;

    static int BYTE_SIZE = Integer.BYTES;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return String.format(
            "@XidType(\"%1$s.%2$s\") int",
            getModule().getClassName(),
            getSrcName());
    }

    @Override
    public int byteSize() {
        return BYTE_SIZE;
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }
}
