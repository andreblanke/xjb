package org.freedesktop.xjbgen.xml.type.complex;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.XjbTypeElement;

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

    @Override
    public @NotNull String getFromBytesExpression() {
        throw new UnsupportedOperationException();
    }

    public static final class Reply extends XjbComplexType<XjbRequest> {

        /**
         * Overrides the default {@link XjbTypeElement#afterUnmarshal(Unmarshaller, Object)} method responsible for
         * registering this type.
         *
         * {@code Reply} types are not meant to be registered, as they do not have their own XML name and are solely
         * meant for the consumer of the API and thus do not appear as a structure content of other complex types.
         *
         * @param unmarshaller The unmarshaller which unmarshalled this object.
         *
         * @param parent The object representing the parent XML element.
         */
        @Override
        public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
            /*
             * Do not register replies as types, as they do not have their own XML name and are solely meant for the
             * consumer of the API and thus do not appear as a structure content of other complex types.
             */
        }

        @Override
        public @NotNull String getSrcName() {
            return getParent().getXmlName() + "Reply";
        }

        @Override
        public @NotNull String getFromBytesExpression() {
            throw new UnsupportedOperationException();
        }
    }
}
