package org.freedesktop.xjbgen.xml;

import javax.xml.bind.annotation.XmlValue;

import org.jetbrains.annotations.NotNull;

public final class XjbImport extends XjbElement<XjbModule> {

    @XmlValue
    private String header;

    static final XjbImport XPROTO_IMPORT = new XjbImport();

    static {
        XPROTO_IMPORT.header = "xproto";
    }

    public @NotNull String getHeader() {
        return header;
    }
}
