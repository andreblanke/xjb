package org.freedesktop.xjbgen.xml.type;

import javax.xml.bind.Unmarshaller;

import org.freedesktop.xjbgen.XjbGenerationContext;
import org.freedesktop.xjbgen.xml.XjbElement;

/**
 * Represents a type used in the generated source code.
 */
public interface XjbType extends XjbNamed {

    /**
     * Computes the size in bytes of the binary representation of this {@code XjbType}.
     *
     * @return The size of this {@code XjbType} instance in bytes.
     */
    int byteSize();

    default void afterUnmarshal(final Unmarshaller unmarshaller, final Object parent) {
        final var xmlElement = (XjbElement<?>) this;

        XjbGenerationContext
            .getInstance()
            .registerType(xmlElement.getModule(), this);
    }
}
