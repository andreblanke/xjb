package org.freedesktop.xjb;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public final class XAuthorityTest {

    @Test
    public void testFromByteBuffer() throws IOException {
        final byte[] xauthorityBytes = getClass().getResourceAsStream(".Xauthority").readAllBytes();

        XAuthority.fromByteBuffer(ByteBuffer.wrap(xauthorityBytes));
    }
}
