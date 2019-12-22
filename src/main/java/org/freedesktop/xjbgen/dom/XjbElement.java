package org.freedesktop.xjbgen.dom;

import javax.xml.bind.Unmarshaller;

public abstract class XjbElement<P extends XjbElement<?>> {

    private P parent;

    @SuppressWarnings({"unchecked", "unused"})
    public void beforeUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        this.parent = (P) parent;
    }

    public XjbModule getModule() {
        return (parent == null) ? (XjbModule) this : parent.getModule();
    }

    public P getParent() {
        return parent;
    }
}
