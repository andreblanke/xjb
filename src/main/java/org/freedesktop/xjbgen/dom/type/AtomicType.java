package org.freedesktop.xjbgen.dom.type;

import org.jetbrains.annotations.NotNull;

public enum AtomicType implements XjbNamedType {

    CARD_8 ("CARD8",  int.class,   Integer.BYTES),
    CARD_16("CARD16", int.class,   Integer.BYTES),
    CARD_32("CARD32", long.class,  Long.BYTES),
    CARD_64("CARD64", long.class,  Long.BYTES),
    INT_8  ("INT8",   byte.class,  Byte.BYTES),
    INT_16 ("INT16",  short.class, Short.BYTES),
    INT_32 ("INT32",  int.class,   Integer.BYTES),
    INT_64 ("INT64",  long.class,  Long.BYTES);

    private final @NotNull String xmlName;

    private final @NotNull Class<?> javaType;

    private final int byteSize;

    AtomicType(@NotNull final String xmlName, @NotNull final Class<?> javaType, final int byteSize) {
        this.xmlName  = xmlName;
        this.javaType = javaType;
        this.byteSize = byteSize;
    }

    @Override
    public int byteSize() {
        return byteSize;
    }

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }

    @Override
    public @NotNull String getSrcName() {
        return javaType.getSimpleName();
    }
}
