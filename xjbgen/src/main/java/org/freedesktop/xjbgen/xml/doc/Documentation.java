package org.freedesktop.xjbgen.xml.doc;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

import org.eclipse.persistence.oxm.annotations.XmlValueExtension;
import org.freedesktop.xjbgen.util.Strings;
import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.type.complex.ComplexType;

public final class Documentation extends Element<ComplexType<?>> {

    @XmlElement
    private Content brief;

    @XmlElement
    private Content description;

    @XmlElement
    private Example example;

    @XmlElement
    private Field field;

    @XmlElement
    private Error error;

    @XmlElement
    private See see;

    public Content getBrief() {
        return brief;
    }

    public Content getDescription() {
        return description;
    }

    public Content getExample() {
        return example;
    }

    public Field getField() {
        return field;
    }

    public Error getError() {
        return error;
    }

    public See getSee() {
        return see;
    }

    public static class Content extends Element<Documentation> {

        @XmlValue
        @XmlValueExtension
        protected String content;

        @Override
        public String toString() {
            return content;
        }
    }

    public static final class Example extends Content {

        @Override
        public String toString() {
            content = Strings.replaceBlockComments(content);
            return super.toString();
        }
    }

    public static final class Field extends Content {

        @XmlAttribute
        private String name;

        public String getName() {
            return name;
        }
    }

    public static final class Error extends Content {

        @XmlAttribute
        private String type;

        public String getType() {
            return type;
        }
    }

    public static final class See extends Element<Documentation> {

        @XmlAttribute
        private String type;

        @XmlAttribute
        private String name;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
