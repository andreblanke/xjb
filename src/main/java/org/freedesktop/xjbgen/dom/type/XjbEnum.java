package org.freedesktop.xjbgen.dom.type;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.dom.expr.XjbIntegerExpression;
import org.freedesktop.xjbgen.dom.expr.XjbIntegerExpression.*;

/**
 * Represents a Java {@code enum} type which can take on any of the values given by the contained {@link #items}.
 */
public final class XjbEnum implements XjbNamed, XjbType {

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlElement(name = "item", required = true)
    private List<Item> items;

    @Override
    public int byteSize() {
        return Integer.BYTES;
    }

    @Override
    public @NotNull String getXmlName() {
        return xmlName;
    }

    public List<Item> getItems() {
        return items;
    }

    /** Represents one possible value of an {@link XjbEnum}. */
    public static final class Item implements XjbNamed {

        private XjbEnum parent;

        @XmlAttribute(name = "name", required = true)
        private String xmlName;

        @XmlElements({
            @XmlElement(name = "bit",   type = XjbBitExpression.class),
            @XmlElement(name = "value", type = XjbValueExpression.class)
        })
        private XjbIntegerExpression expression;

        @SuppressWarnings("unused")
        public void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
            /* JAXB cannot handle non-static inner classes, which is why we have to go this route instead. */
            this.parent = (XjbEnum) parent;
        }

        @Override
        public @NotNull String getXmlName() {
            return xmlName;
        }

        public XjbIntegerExpression getExpression() {
            if (expression == null) {
                final int index = parent.getItems().indexOf(this);

                if (index == 0)
                    return (expression = new XjbValueExpression());

                final XjbIntegerExpression previousItemExpression =
                    parent
                        .getItems()
                        .get(index - 1)
                        .getExpression();
                expression = new XjbValueExpression(previousItemExpression.getValue() + 1);
            }
            return expression;
        }
    }
}
