package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.xml.Element;
import org.jetbrains.annotations.NotNull;

public abstract class TypeElement<P extends Element<?>> extends Element<P> implements Type {

    @XmlAttribute(name = "name")
    private String xmlName;

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object Parent) {
        getModule().registerType(this);
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return getModule().getClassName() + '.' + getSrcName();
    }
}
