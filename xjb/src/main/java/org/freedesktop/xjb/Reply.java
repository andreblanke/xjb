package org.freedesktop.xjb;

import java.nio.ByteBuffer;

public abstract class Reply {

    protected int sequenceNumber;

    protected long replyLength;

    Reply(final ByteBuffer buffer) {
        /* Skip reply determinant. */
        buffer.position(buffer.position() + 1);
    }
}
