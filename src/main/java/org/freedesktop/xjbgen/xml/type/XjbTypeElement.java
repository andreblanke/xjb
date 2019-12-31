package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;

import org.freedesktop.xjbgen.xml.XjbElement;

public abstract class XjbTypeElement<P extends XjbElement<?>> extends XjbElement<P> implements XjbType {

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object Parent) {
        getModule().registerType(this);
    }

    @Override
    public String toString() {
        return String.format("%1$s.%2$s", getModule().getClassName(), getSrcName());
    }
}
