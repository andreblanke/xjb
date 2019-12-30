package org.freedesktop.xjbgen.xml.type.complex;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.type.XjbType;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbNamedTypedStructureContent;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbPadStructureContent;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbStructureContent;

import static java.util.stream.Collectors.toList;

/**
 * Represents a complex {@link XjbType} which will be represented as a class in the generated source code.
 *
 * @param <P> The type of the parent {@link XjbElement}.
 */
public abstract class XjbComplexType<P extends XjbElement<?>> extends XjbElement<P> implements XjbType {

    /**
     * Not all complex types are required to have this XML attribute.
     *
     * An example for this would be {@link XjbRequest.Reply} which only appears as a child of an {@link XjbRequest} and
     * does not define an XML {@code name} attribute.
     */
    @XmlAttribute(name = "name")
    private String xmlName;

    @XmlElements({
        @XmlElement(name = "pad",   type = XjbPadStructureContent.class),
        @XmlElement(name = "field", type = XjbFieldStructureContent.class)
    })
    private List<XjbStructureContent> contents = new ArrayList<>();

    @Override
    public int byteSize() {
        return contents.stream().mapToInt(XjbStructureContent::byteSize).sum();
    }

    public @NotNull List<XjbNamedTypedStructureContent> getNamedTypedContents() {
        return contents
            .stream()
            .filter(XjbNamedTypedStructureContent.class::isInstance)
            .map(XjbNamedTypedStructureContent.class::cast)
            .collect(toList());
    }

    @Override
    public String getXmlName() {
        return xmlName;
    }

    public @NotNull List<XjbStructureContent> getContents() {
        return contents;
    }
}
