package org.freedesktop.xjbgen.xml.type.complex.content;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.type.complex.XjbComplexType;

public abstract class XjbStructureContent extends XjbElement<XjbComplexType<?>> {

    public abstract int byteSize();
}
