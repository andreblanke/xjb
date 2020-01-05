package org.freedesktop.xjbgen.xml.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.template.DataModel;
import org.freedesktop.xjbgen.xml.XjbElement;
import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.expr.XjbIntegerExpression;
import org.freedesktop.xjbgen.xml.expr.XjbIntegerExpression.*;

/**
 * Represents a Java {@code enum} type which can take on any of the values returned by {@link #getItems()}.
 *
 * {@code XjbEnum}s are not considered a complex type.
 */
@DataModel
public final class XjbEnum extends XjbTypeElement<XjbModule>{

    @XmlAttribute(name = "name", required = true)
    private String xmlName;

    @XmlElement(name = "item", required = true)
    private List<Item> items;

    @Override
    public String toString() {
        return getSrcName();
    }

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
    public static final class Item extends XjbElement<XjbEnum> implements XjbNamed {

        @XmlAttribute(name = "name", required = true)
        private String xmlName;

        @XmlElements({
            @XmlElement(name = "bit",   type = XjbBitExpression.class),
            @XmlElement(name = "value", type = XjbValueExpression.class)
        })
        private XjbIntegerExpression expression;

        @Override
        public @NotNull String getXmlName() {
            return xmlName;
        }

        public XjbIntegerExpression getExpression() {
            if (expression == null) {
                final int index = getParent().getItems().indexOf(this);

                if (index == 0)
                    return (expression = new XjbValueExpression());

                final XjbIntegerExpression previousItemExpression =
                    getParent()
                        .getItems()
                        .get(index - 1)
                        .getExpression();
                expression = new XjbValueExpression(previousItemExpression.getValue() + 1);
            }
            return expression;
        }
    }
}
