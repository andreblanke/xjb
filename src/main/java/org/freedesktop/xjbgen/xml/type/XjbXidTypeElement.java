package org.freedesktop.xjbgen.xml.type;

import org.jetbrains.annotations.NotNull;

import org.freedesktop.xjbgen.xml.XjbModule;
import org.freedesktop.xjbgen.xml.type.complex.content.XjbFieldStructureContent;

public abstract class XjbXidTypeElement extends XjbTypeElement<XjbModule> {

    private static final int BYTE_SIZE = Integer.BYTES;

    // <editor-fold desc="XjbType">
    @Override
    public int byteSize() {
        return BYTE_SIZE;
    }

    @Override
    public @NotNull String getFromBytesSrc(@NotNull final XjbFieldStructureContent content) {
        return "%1$s.getInt();";
    }

    @Override
    public @NotNull String getQualifiedSrcName() {
        return "@%s int".formatted(super.getQualifiedSrcName());
    }
    // </editor-fold>
}
