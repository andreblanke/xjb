package org.freedesktop.xjbgen.xml.type.complex;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public final class EventStruct extends Struct {

    @XmlElement(name = "allowed")
    private List<EventTypeSelector> eventTypeSelectorList = new ArrayList<>();

    private static final class EventTypeSelector {

        @XmlAttribute(required = true)
        private String extension;

        @XmlAttribute(name = "xge", required = true)
        private boolean xGenericEvent;

        @XmlAttribute(name = "opcode-min", required = true)
        private int opcodeMin;

        @XmlAttribute(name = "opcode-max", required = true)
        private int opcodeMax;
    }
}
