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
            : String.format("private java.util.List<%1$s> %2$s;", getSrcType().boxed().getQualifiedSrcName(), getSrcName());
    }

    @Override
    public @NotNull String getFromBytesSrc() {
        if (isString()) {
            return String.format(
                "%1$s = new java.lang.String(%2$s.array(), %2$s.position(), %3$s, java.nio.charsets.StandardCharsets.ISO_8859_1);",
                getSrcName(),
                BYTE_BUFFER_NAME,
                lengthExpression
            );
        }
        return String.format(
            "%1$s = new java.util.ArrayList<%2$s>(%3$s);\n" +
            "            for (int i = 0; i < %4$s; ++i)\n" +
            "                %1$s.add(%5$s);",
            getSrcName(),
            getSrcType().boxed().getQualifiedSrcName(),
            lengthExpression,
            getSizeSrc(),
            String.format(getSrcType().getFromBytesExpression(), BYTE_BUFFER_NAME)
        );
    }

    /**
     * Checks whether this {@code ListStructureContent} is a string, i.e. a list of characters.
     *
     * @return {@code true} if the underlying type of this {@code ListStructureContent} is {@link AtomicType#CHAR},
     *         otherwise {@code false}.
     */
    private boolean isString() {
        return getSrcType() == AtomicType.CHAR;
    }

    /**
     * Gets a unit of source code whose value is the length of the list represented by this {@code ListStructureContent}.
     *
     * @return {@code srcName.length()} for lists where the underlying type is {@link AtomicType#CHAR}, otherwise
     *         {@code srcName.size()}.
     */
    private @NotNull String getSizeSrc() {
        return String.format("%1$s.%2$s()", getSrcName(), isString() ? "length" : "size");
    }
}
