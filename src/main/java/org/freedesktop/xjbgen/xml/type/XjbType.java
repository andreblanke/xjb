package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.NotNull;

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

    /**
     * The returned source code fragment will be formatted before being used in the generated source code and must thus
     * include the three format specifiers {@code %1$s}, a placeholder for the local variable of the object being
     * constructed from bytes, {@code %2$s}, a placeholder for the field name of the local variable, and {@code %3$s},
     * a placeholder for the variable name of the  {@link java.nio.ByteBuffer} we are reading from.
     *
     * @return
     */
    @NotNull String getFromBytesExpression();

    @NotNull String getQualifiedSrcName();

    default @NotNull XjbType getBoxedType() {
        return this;
    }
}
