package org.freedesktop.xjbgen.dom.type.complex;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.XjbModule;

public final class XjbRequest extends XjbComplexType<XjbModule> {

    @XmlAttribute(required = true)
    private int opcode;

    @XmlElement
    private Reply reply;

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

    public static final class Reply extends XjbComplexType<XjbRequest> {

        @Override
        public @NotNull String getSrcName() {
            return getParent().getXmlName() + "Reply";
        }
    }
}
