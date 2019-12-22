package org.freedesktop.xjbgen.dom.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

public final class XjbPadStructureContent extends XjbStructureContent {

    @XmlAttribute
    private int bytes;

    public int getBytes() {
        return bytes;
    }
}
