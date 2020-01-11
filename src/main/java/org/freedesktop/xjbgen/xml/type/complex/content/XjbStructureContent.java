package org.freedesktop.xjbgen.xml.type.complex.content;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.type.complex.XjbComplexType;

public abstract class XjbStructureContent extends XjbElement<XjbComplexType<?>> {

    public abstract int byteSize();

    /** @see org.freedesktop.xjbgen.xml.type.XjbType#getFromBytesSrc(XjbFieldStructureContent)  */
    public abstract String getFromBytesSrc();
}
