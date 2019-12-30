package org.freedesktop.xjbgen.xml.type;

public interface XjbNamed {

    String getXmlName();

    default String getSrcName() {
        return getXmlName();
    }
}
