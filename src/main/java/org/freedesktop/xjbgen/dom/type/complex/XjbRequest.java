package org.freedesktop.xjbgen.dom.type.complex;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public final class XjbRequest extends XjbNamedComplexType {

    @XmlAttribute(required = true)
    private int opcode;

    @XmlElement
    private Reply reply;

    @Override
    public int byteSize() {
        return 0;
    }

    @Override
    public @NotNull String getSrcName() {
        return getXmlName() + "Request";
    }

    public int getOpcode() {
        return opcode;
    }

    public Reply getReply() {
        return reply;
    }

    public static final class Reply extends XjbComplexType {

        @Override
        public int byteSize() {
            return 0;
        }
    }
}
