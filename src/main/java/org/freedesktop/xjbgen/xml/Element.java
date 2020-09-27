package org.freedesktop.xjbgen.xml;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The {@code Element} class is the common superclass of all classes representing XML tags of a protocol description.
 *
 * Each {@code Element} stores a reference to the parent {@code Element} inside the {@link #parent} attribute,
 * which allows walking the tree both from top to bottom, as well as bottom to top.
 *
 * @param <P> The type of the parent {@code Element}.
 */
@XmlTransient
public abstract class Element<P extends Element<?>> {

    private P parent;

    private Module module;

    @SuppressWarnings({"unchecked", "unused"})
    public void beforeUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        this.parent = (P) parent;
    }

    /**
     * Returns the {@link Module} this {@code Element} is part of by walking up the chain of parents of this element.
     *
     * @return The {@code Module} this element is part of.
     */
    public Module getModule() {
        if (module != null)
            return module;
        return (module = (parent == null) ? ((Module) this) : parent.getModule());
    }

    public P getParent() {
        return parent;
    }
}
