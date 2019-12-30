package org.freedesktop.xjbgen.xml.type;

import java.util.Arrays;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum XjbAtomicType implements XjbType {

    CARD_8 ("CARD8",  int.class,     Integer.BYTES, true),
    CARD_16("CARD16", int.class,     Integer.BYTES, true),
    CARD_32("CARD32", long.class,    Long.BYTES,    true),
    CARD_64("CARD64", long.class,    Long.BYTES,    true),
    INT_8  ("INT8",   byte.class,    Byte.BYTES,    false),
    INT_16 ("INT16",  short.class,   Short.BYTES,   false),
    INT_32 ("INT32",  int.class,     Integer.BYTES, false),
    INT_64 ("INT64",  long.class,    Long.BYTES,    false),
    BYTE   ("BYTE",   int.class,     Integer.BYTES, true),
    BOOLEAN("BOOL",   boolean.class, 1,             false),
    FLOAT  ("float",  float.class,   Float.BYTES,   false),
    DOUBLE ("double", double.class,  Double.BYTES,  false),
    CHAR   ("char",   byte.class,    Byte.BYTES,    false);

    private final @NotNull String xmlName;

    private final @NotNull Class<?> javaType;

    private final int byteSize;

    private final boolean unsigned;

    XjbAtomicType(
            @NotNull final String xmlName,
            @NotNull final Class<?> javaType,
            final int byteSize,
            final boolean unsigned) {
        this.xmlName  = xmlName;
        this.javaType = javaType;
        this.byteSize = byteSize;
        this.unsigned = unsigned;
    }

    public static Map<String, XjbAtomicType> getXmlNameMappings() {
        return Arrays
            .stream(values())
            .collect(toMap(XjbAtomicType::getXmlName, identity()));
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
