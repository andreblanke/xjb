package org.freedesktop.xjbgen.xml.type.complex;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.freedesktop.xjbgen.xml.doc.Documentation;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.type.Type;
import org.freedesktop.xjbgen.xml.type.TypeElement;
import org.freedesktop.xjbgen.xml.type.complex.content.*;

import static java.util.stream.Collectors.toList;

/**
 * Represents a complex {@link Type} which will be represented as a class in the generated source code.
 *
 * Not all complex types are required to have this XML attribute.
 *
 * An example for this would be {@link Request.Reply} which only appears as a child of an {@link Request} and
 * does not define an XML {@code name} attribute.
 *
 * @param <P> The type of the parent {@link Element}.
 */
public abstract class ComplexType<P extends Element<?>> extends TypeElement<P> {

    @XmlElement(name = "doc")
    private Documentation documentation;

    @XmlElements({
        @XmlElement(name = "pad",   type = PadStructureContent.class),
        @XmlElement(name = "field", type = FieldStructureContent.class),
        @XmlElement(name = "list",  type = ListStructureContent.class)
    })
    private List<StructureContent> contents = new ArrayList<>();

    // <editor-fold desc="XjbType">
    private int byteSize;

    @Override
    public int byteSize() {
        if (byteSize == 0)
            byteSize = contents.stream().mapToInt(StructureContent::byteSize).sum();
        return byteSize;
    }

    @Override
    public @NotNull String getFromBytesExpression() {
        return "new " + getQualifiedSrcName() + "(%1$s)";
    }
    // </editor-fold>

    public @NotNull List<NamedTypedStructureContent> getNamedTypedContents() {
        return contents
            .stream()
            .filter(NamedTypedStructureContent.class::isInstance)
            .map(NamedTypedStructureContent.class::cast)
            .collect(toList());
    }

    public Documentation getDocumentation() {
        return documentation;
    }

    public @NotNull List<StructureContent> getContents() {
        return contents;
    }
}
