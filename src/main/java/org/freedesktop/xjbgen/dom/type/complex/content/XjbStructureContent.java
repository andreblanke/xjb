package org.freedesktop.xjbgen.dom.type.complex.content;

import org.freedesktop.xjbgen.dom.XjbElement;
import org.freedesktop.xjbgen.dom.type.complex.XjbComplexType;

public abstract class XjbStructureContent extends XjbElement<XjbComplexType<?>> {

    public abstract int byteSize();
}
