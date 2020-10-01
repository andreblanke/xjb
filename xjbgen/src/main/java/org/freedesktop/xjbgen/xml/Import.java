package org.freedesktop.xjbgen.xml;

import java.util.Objects;

import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;

import org.jetbrains.annotations.NotNull;

public final class Import extends Element<Module> {

    @XmlValue
    @XmlValueExtension
    private String header;

    static final Import XPROTO_IMPORT = new Import();

    static {
        XPROTO_IMPORT.header = "xproto";
    }

    @Override
    public boolean equals(final Object object) {
        return (object instanceof Import) && equals((Import) object);
    }

    public boolean equals(final Import other) {
        return (other != null) && Objects.equals(getHeader(), other.getHeader());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeader());
    }

    public @NotNull String getHeader() {
        return header;
    }
}
