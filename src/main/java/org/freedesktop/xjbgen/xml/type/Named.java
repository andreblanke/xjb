package org.freedesktop.xjbgen.xml.type;

public interface Named {

    String getXmlName();

    default String getSrcName() {
        return getXmlName();
    }
}
