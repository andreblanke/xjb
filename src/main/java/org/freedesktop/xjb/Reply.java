package org.freedesktop.xjb;

import java.nio.ByteBuffer;

import org.jetbrains.annotations.NotNull;

public abstract class Reply {

    protected int sequenceNumber;

    protected long replyLength;

    protected Reply(@NotNull final ByteBuffer buffer) {
        /* Skip reply determinant. */
        buffer.position(buffer.position() + 1);
    }
}
