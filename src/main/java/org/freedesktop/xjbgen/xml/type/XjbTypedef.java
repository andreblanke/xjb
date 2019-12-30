package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.XjbModule;

public final class XjbTypedef extends XjbElement<XjbModule> {

    @XmlAttribute(name = "oldname")
    private String oldName;

    @XmlAttribute(name = "newname")
    private String newName;

    @SuppressWarnings("unused")
    public void afterUnmarshal(final Unmarshaller unmarshaller, final Object Parent) {
        final XjbModule module = getModule();

        final XjbType aliased =
            XjbGenerationContext
                .getInstance()
                .lookupType(module, oldName);

        getModule().registerType(newName, aliased);
    }
}
