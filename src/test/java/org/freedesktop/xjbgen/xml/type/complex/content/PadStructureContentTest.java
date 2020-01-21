package org.freedesktop.xjbgen.xml.type.complex.content;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.freedesktop.xjbgen.xml.type.complex.ComplexType;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public final class PadStructureContentTest {

    /*
     * Doesn't matter whether we use XjbComplexType, XjbStruct or anything else. None of those classes expose any
     * setters, meaning we have to either provide them solely for the test cases or fall back to mocking.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetFromBytesSrcSkipsLastPadStructureContent() {
        final var padContent = mock(PadStructureContent.class);
        final var parent     = mock(ComplexType.class);

        when(padContent.byteSize()).thenReturn(8);
        when(padContent.getParent()).thenReturn(parent);
        when(padContent.getAdvanceBufferSrc()).thenCallRealMethod();

        when(parent.getContents()).thenReturn(List.of(padContent));

        assertNull(padContent.getAdvanceBufferSrc());
    }
}
