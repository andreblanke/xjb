package org.freedesktop.xjbgen.xml.type;

import java.util.Arrays;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum AtomicType implements Type {

    CARD_8("CARD8", int.class, Integer.class, 1, true) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "Byte.toUnsignedInt(%1$s.get())";
        }
    },

    CARD_16("CARD16", int.class, Integer.class, 2, true) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "Short.toUnsignedInt(%1$s.getShort())";
        }
    },

    CARD_32("CARD32", long.class, Long.class, 4, true) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "Integer.toUnsignedLong(%1$s.getInt())";
        }
    },

    CARD_64("CARD64", long.class, Long.class, 8, true) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getLong()";
        }
    },

    INT_8("INT8", byte.class, Byte.class, 1, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.get()";
        }
    },

    INT_16("INT16", short.class, Short.class, 2, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getShort()";
        }
    },

    INT_32("INT32", int.class, Integer.class, 4, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getInt()";
        }
    },

    INT_64("INT64", long.class, Long.class, 8, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getLong()";
        }
    },

    BYTE("BYTE", int.class, Integer.class, 1, true) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "Byte.toUnsignedInt(%1$s.get())";
        }
    },

    BOOLEAN("BOOL", boolean.class, Boolean.class, 1, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "(%1$s.get() != 0)";
        }
    },

    FLOAT("float", float.class, Float.class, 4, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getFloat()";
        }
    },

    DOUBLE("double", double.class, Double.class, 8, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.getDouble()";
        }
    },

    CHAR("char", char.class, Character.class, 1, false) {
        @Override
        public @NotNull String getFromBytesExpression() {
            return "(char) %1$s.get()";
        }
    };

    private final @NotNull String xmlName;

    private final @NotNull Class<?> javaType;

    private final @NotNull Class<?> boxedJavaType;

    private final int byteSize;

    private final boolean unsigned;

    AtomicType(
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

    public static Map<String, AtomicType> getXmlNameMappings() {
        return Arrays
            .stream(values())
            .collect(toMap(AtomicType::getXmlName, identity()));
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

    @Override
    public @NotNull String getQualifiedSrcName() {
        return getSrcName();
    }

    @Override
    public @NotNull Type getBoxedType() {
        return new AbstractBoxedAtomicType();
    }

    private final class AbstractBoxedAtomicType implements Type {

        @Override
        public final int byteSize() {
            return AtomicType.this.byteSize();
        }

        @Override
        public @NotNull String getFromBytesExpression() {
            return "%1$s.valueOf(%2$s)".formatted(getQualifiedSrcName(), AtomicType.this.getFromBytesExpression());
        }

        @Override
        public final @NotNull String getQualifiedSrcName() {
            return AtomicType.this.boxedJavaType.getName();
        }

        @Override
        public final @NotNull String getSrcName() {
            return AtomicType.this.boxedJavaType.getSimpleName();
        }

        @Override
        public final @NotNull String getXmlName() {
            return AtomicType.this.getXmlName();
        }
    }
}
