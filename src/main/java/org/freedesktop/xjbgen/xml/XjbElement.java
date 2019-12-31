package org.freedesktop.xjbgen.xml;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The {@code XjbElement} class is the common superclass of all classes representing XML tags of a protocol description.
 *
 * Each {@code XjbElement} stores a reference to the parent {@code XjbElement} inside the {@link #parent} attribute,
 * which allows walking the tree both from top to bottom, as well as bottom to top.
 *
 * @param <P> The type of the parent {@code XjbElement}.
 */
@XmlTransient
public abstract class XjbElement<P extends XjbElement<?>> {

    private P parent;

    private XjbModule module;

    @SuppressWarnings({"unchecked", "unused"})
    public void beforeUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        this.parent = (P) parent;
    }

    public XjbModule getModule() {
        if (module != null)
            return module;
        return (module = (parent == null) ? ((XjbModule) this) : parent.getModule());
    }

    public P getParent() {
        return parent;
    }
}
