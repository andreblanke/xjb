package org.freedesktop.xjbgen.xml.type.complex.content;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.expr.*;
import org.freedesktop.xjbgen.xml.type.AtomicType;

public final class ListStructureContent extends FieldStructureContent {

    @XmlElements({
        @XmlElement(name = "bit",         type = BitExpression.class),
        @XmlElement(name = "enumref",     type = EnumReferenceExpression.class),
        @XmlElement(name = "fieldref",    type = FieldReferenceExpression.class),
        @XmlElement(name = "listelement", type = ListElementReferenceExpression.class),
        @XmlElement(name = "op",          type = OperatorExpression.class),
        @XmlElement(name = "paramref",    type = ParameterReferenceExpression.class),
        @XmlElement(name = "popcount",    type = PopCountExpression.class),
        @XmlElement(name = "sumof",       type = SumOfExpression.class),
        @XmlElement(name = "unop",        type = UnaryOperatorExpression.class),
        @XmlElement(name = "value",       type = ValueExpression.class)
    })
    private Expression lengthExpression;

    @Override
    @Contract(pure = true)
    public @NotNull String toString() {
        return isString()
            ? String.format("private java.lang.String %1$s;", getSrcName())
            : String.format("private java.util.List<%1$s> %2$s;", getSrcType().getBoxedType().getQualifiedSrcName(), getSrcName());
    }

    @Override
    public @NotNull String getFromBytesSrc() {
        return isString()
            ? String.format("%1$s = new java.lang.String(%2$s.array(), %2$s.position(), %3$s, java.nio.charsets.StandardCharsets.ISO_8859_1);", getSrcName(), BYTE_BUFFER_NAME, lengthExpression)
            : String.format(
                "%1$s = new java.util.ArrayList<%2$s>(%3$s); for (int i = 0; i < %4$s; ++i) %1$s.add(%5$s);",
                getSrcName(),
                getSrcType().getBoxedType().getQualifiedSrcName(),
                lengthExpression,
                getSizeSrc(),
                String.format(getSrcType().getBoxedType().getFromBytesExpression(), BYTE_BUFFER_NAME));
    }

    @Override
    public @NotNull String getAdvanceBufferSrc() {
        return String.format("%1$s.position(%1$s.position() + %2$s);", BYTE_BUFFER_NAME, getSizeSrc());
    }

    private boolean isString() {
        return getSrcType() == AtomicType.CHAR;
    }

    private @NotNull String getSizeSrc() {
        return String.format("%1$s.%2$s()", getSrcName(), isString() ? "length" : "size");
    }
}
