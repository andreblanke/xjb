package org.freedesktop.xjbgen.xml.type;

import org.freedesktop.xjbgen.xml.XjbModule;

public abstract class XjbXidTypeElement extends XjbTypeElement<XjbModule> {

    @Override
    public String toString() {
        return String.format("@%1$s int", getSrcName());
    }
}
