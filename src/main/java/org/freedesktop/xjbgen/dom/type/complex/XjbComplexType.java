package org.freedesktop.xjbgen.dom.type.complex;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.type.XjbType;
import org.freedesktop.xjbgen.dom.type.complex.content.XjbFieldStructureContent;
import org.freedesktop.xjbgen.dom.type.complex.content.XjbNamedTypedStructureContent;
import org.freedesktop.xjbgen.dom.type.complex.content.XjbPadStructureContent;
import org.freedesktop.xjbgen.dom.type.complex.content.XjbStructureContent;

import static java.util.stream.Collectors.toList;

public abstract class XjbComplexType implements XjbType {

    @XmlElements({
        @XmlElement(name = "pad",   type = XjbPadStructureContent.class),
        @XmlElement(name = "field", type = XjbFieldStructureContent.class)
    })
    private List<XjbStructureContent> contents = new ArrayList<>();

    public @NotNull List<XjbNamedTypedStructureContent> getNamedTypedContents() {
        return contents
            .stream()
            .filter(XjbNamedTypedStructureContent.class::isInstance)
            .map(XjbNamedTypedStructureContent.class::cast)
            .collect(toList());
    }

    public @NotNull List<XjbStructureContent> getContents() {
        return contents;
    }
}
