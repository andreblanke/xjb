package org.freedesktop.xjbgen.xml.type;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.Element;
import org.freedesktop.xjbgen.xml.Module;
import org.freedesktop.xjbgen.xml.expr.IntegerExpression;
import org.freedesktop.xjbgen.xml.expr.IntegerExpression.*;

/**
 * Represents a Java {@code enum} type which can take on any of the values returned by {@link #getItems()}.
 *
 * {@code XjbEnum}s are not considered a complex type.
 */
public final class Enum extends TypeElement<Module> {

    @XmlElement(name = "item", required = true)
    private List<Item> items;

    @Override
    public int byteSize() {
        return Integer.BYTES;
    }

    @Override
    public @NotNull String getFromBytesExpression() {
        return getQualifiedSrcName() + ".valueOf(%1$s.getInt())";
    }

    public List<Item> getItems() {
        return items;
    }

    /** Represents one possible value of an {@link Enum}. */
    public static final class Item extends Element<Enum> implements Named {

        @XmlAttribute(name = "name", required = true)
        private String xmlName;

        @XmlElements({
            @XmlElement(name = "bit",   type = BitExpression.class),
            @XmlElement(name = "value", type = ValueExpression.class)
        })
        private IntegerExpression expression;

        @Override
        public @NotNull String getXmlName() {
            return xmlName;
        }

        public IntegerExpression getExpression() {
            if (expression == null) {
                final int index = getParent().getItems().indexOf(this);

                if (index == 0)
                    return (expression = new ValueExpression());

                final IntegerExpression previousItemExpression =
                    getParent()
                        .getItems()
                        .get(index - 1)
                        .getExpression();
                expression = new ValueExpression(previousItemExpression.getValue() + 1);
            }
            return expression;
        }
    }
}
