package org.freedesktop.xjbgen.xml.type;

import java.util.Arrays;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.template.DataModel;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@DataModel
public enum XjbAtomicType implements XjbType {

    CARD_8 ("CARD8",  int.class,     1, true),
    CARD_16("CARD16", int.class,     2, true),
    CARD_32("CARD32", long.class,    4, true),
    CARD_64("CARD64", long.class,    8, true),
    INT_8  ("INT8",   byte.class,    1, false),
    INT_16 ("INT16",  short.class,   2, false),
    INT_32 ("INT32",  int.class,     4, false),
    INT_64 ("INT64",  long.class,    8, false),
    BYTE   ("BYTE",   int.class,     1, true),
    BOOLEAN("BOOL",   boolean.class, 1, false),
    FLOAT  ("float",  float.class,   4, false),
    DOUBLE ("double", double.class,  8, false),
    CHAR   ("char",   char.class,    1, false);

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
    public @NotNull String toString() {
        return getSrcName();
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
