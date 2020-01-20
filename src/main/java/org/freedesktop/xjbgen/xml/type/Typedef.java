package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.GenerationContext;
import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.Module;

public final class Typedef extends Element<Module> {

    @XmlAttribute(name = "oldname")
    private String oldName;

    @XmlAttribute(name = "newname")
    private String newName;

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object Parent) {
        final Module module = getModule();

        final Type aliased =
            GenerationContext
                .getInstance()
                .lookupType(module, oldName);

        getModule().registerType(newName, aliased);
    }
}
