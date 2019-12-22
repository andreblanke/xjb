package org.freedesktop.xjbgen.dom.type;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jetbrains.annotations.NotNull;

public final class XjbRequest implements XjbNamedType {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlAttribute(required = true)
    private int opcode;

    @XmlElement
    private Reply reply;

    @Override
    public int byteSize() {
        return 0;
    }

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }

    public int getOpcode() {
        return opcode;
    }

    public Reply getReply() {
        return reply;
    }

    public static final class Reply implements XjbType {

        @Override
        public int byteSize() {
            return 0;
        }
    }
}
