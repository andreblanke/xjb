package org.freedesktop.xjbgen.dom.type;

public interface XjbNamed {

    String getXmlName();

    default String getSrcName() {
        return getXmlName();
    }
}
