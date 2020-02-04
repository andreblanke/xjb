package org.freedesktop.xjbgen.xml.type.complex.content;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.type.Type;
import org.freedesktop.xjbgen.xml.type.complex.ComplexType;

public abstract class StructureContent extends Element<ComplexType<?>> {

    public static final String BYTE_BUFFER_NAME = "buffer";

    public abstract int byteSize();

    /** @see Type#getFromBytesExpression()  */
    public abstract @NotNull String getFromBytesSrc();
}
