package org.freedesktop.xjbgen.xml.type;

import java.util.Arrays;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum XjbAtomicType implements XjbType {

    CARD_8("CARD8", int.class, Integer.class, 1, true) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "Byte.toUnsignedInt(%1$s.get());";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Integer.valueOf(Byte.toUnsignedInt(%1$s.get()));");
        }
    },

    CARD_16("CARD16", int.class, Integer.class, 2, true) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "Short.toUnsignedInt(%1$s.getShort());";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Integer.valueOf(Short.toUnsignedInt(%1$s.getShort()));");
        }
    },

    CARD_32("CARD32", long.class, Long.class, 4, true) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "Integer.toUnsignedLong(%1$s.getInt());";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Long.valueOf(Integer.toUnsignedLong(%1$s.getInt()));");
        }
    },

    CARD_64("CARD64", long.class, Long.class, 8, true) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getLong();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Long.valueOf(%1$s.getLong());");
        }
    },

    INT_8("INT8", byte.class, Byte.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.get();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Byte.valueOf(%1$s.get());");
        }
    },

    INT_16("INT16", short.class, Short.class, 2, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getShort();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Short.valueOf(%1$s.getShort());");
        }
    },

    INT_32("INT32", int.class, Integer.class, 4, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getInt();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Integer.valueOf(%1$s.getInt());");
        }
    },

    INT_64("INT64", long.class, Long.class, 8, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getLong();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Long.valueOf(%1$s.getLong());");
        }
    },

    BYTE("BYTE", int.class, Integer.class, 1, true) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "Byte.toUnsignedInt(%1$s.get());";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Integer.valueOf(Byte.toUnsignedInt(%1$s.get()));");
        }
    },

    BOOLEAN("BOOL", boolean.class, Boolean.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "(%1$s.get() != 0);";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Boolean.valueOf(%1$s.get() != 0);");
        }
    },

    FLOAT("float", float.class, Float.class, 4, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getFloat();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Float.valueOf(%1$s.getFloat());");
        }
    },

    DOUBLE("double", double.class, Double.class, 8, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "%1$s.getDouble();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Double.valueOf(%1$s.getDouble());");
        }
    },

    CHAR("char", char.class, Character.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return "(char) %1$s.get();";
        }

        @Override
        public @NotNull XjbType getBoxedType() {
            return new XjbAbstractBoxedAtomicType("Character.valueOf((char) %1$s.get());");
        }
    };

    private final @NotNull String xmlName;

    private final @NotNull Class<?> javaType;

    private final @NotNull Class<?> boxedJavaType;

    private final int byteSize;

    private final boolean unsigned;

    XjbAtomicType(
            @NotNull final String xmlName,
            @NotNull final Class<?> javaType,
            @NotNull final Class<?> boxedJavaType,
            final int byteSize,
            final boolean unsigned) {
        this.xmlName       = xmlName;
        this.javaType      = javaType;
        this.boxedJavaType = boxedJavaType;
        this.byteSize      = byteSize;
        this.unsigned      = unsigned;
    }

    public static Map<String, XjbAtomicType> getXmlNameMappings() {
        return Arrays
            .stream(values())
            .collect(toMap(XjbAtomicType::getXmlName, identity()));
    }

    @Override
    public abstract @NotNull XjbType getBoxedType();

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

    @Override
    public @NotNull String getQualifiedSrcName() {
        return getSrcName();
    }

    private final class XjbAbstractBoxedAtomicType implements XjbType {

        private final @NotNull String fromBytesSrc;

        private XjbAbstractBoxedAtomicType(@NotNull final String fromBytesSrc) {
            this.fromBytesSrc = fromBytesSrc;
        }

        @Override
        public final int byteSize() {
            return XjbAtomicType.this.byteSize();
        }

        @Override
        public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
            return fromBytesSrc;
        }

        @Override
        public final @NotNull String getQualifiedSrcName() {
            return XjbAtomicType.this.boxedJavaType.getName();
        }

        @Override
        public final @NotNull String getSrcName() {
            return XjbAtomicType.this.boxedJavaType.getSimpleName();
        }

        @Override
        public final @NotNull String getXmlName() {
            return XjbAtomicType.this.getXmlName();
        }
    }
}
