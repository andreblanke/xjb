package org.freedesktop.xjbgen.xml.type;

import java.util.Arrays;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum XjbAtomicType implements XjbType {

    CARD_8("CARD8", int.class, 1, true) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = Byte.toUnsignedInt(%3$s.get());";
        }
    },

    CARD_16("CARD16", int.class, 2, true) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = Short.toUnsignedInt(%3$s.getShort());";
        }
    },

    CARD_32("CARD32", long.class, 4, true) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = Integer.toUnsignedLong(%3$s.getInt());";
        }
    },

    CARD_64("CARD64", long.class, 8, true) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getLong();";
        }
    },

    INT_8("INT8", byte.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.get();";
        }
    },

    INT_16("INT16", short.class, 2, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getShort();";
        }
    },

    INT_32("INT32", int.class, 4, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getInt();";
        }
    },

    INT_64("INT64", long.class, 8, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getLong();";
        }
    },

    BYTE("BYTE", int.class, 1, true) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = Byte.toUnsignedInt(%3$s.get());";
        }
    },

    BOOLEAN("BOOL", boolean.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = (%3$s.get() != 0);";
        }
    },

    FLOAT("float", float.class, 4, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getFloat();";
        }
    },

    DOUBLE("double", double.class, 8, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = %3$s.getDouble();";
        }
    },

    CHAR("char", char.class, 1, false) {
        @Override
        public @NotNull String getFromBytesSrc(final XjbFieldStructureContent content) {
            return "%1$s.%2$s = (char) %3$s.get();";
        }
    };

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

    @Override
    public @NotNull String getQualifiedSrcName() {
        return getSrcName();
    }
}
