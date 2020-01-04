package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlAttribute;

import org.freedesktop.xjbgen.template.DataModel;

@DataModel
public final class XjbPadStructureContent extends XjbStructureContent {

    @XmlAttribute
    private int bytes;

    @Override
    public int byteSize() {
        return bytes;
    }
}
