package org.freedesktop.xjbgen.dom.type;

/**
 * Represents a type used in the generated source code.
 */
public interface XjbType {

    /**
     * Computes the size in bytes of the binary representation of this {@code XjbType}.
     *
     * @return The size of this {@code XjbType} instance in bytes.
     */
    int byteSize();
}
