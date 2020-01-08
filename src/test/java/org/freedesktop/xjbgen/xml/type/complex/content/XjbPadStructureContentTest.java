package org.freedesktop.xjbgen.xml.type.complex.content;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.freedesktop.xjbgen.xml.type.complex.XjbComplexType;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public final class XjbPadStructureContentTest {

    /*
     * Doesn't matter whether we use XjbComplexType, XjbStruct or anything else. None of those classes expose any
     * setters, meaning we have to either provide them solely for the test cases or fall back to mocking.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testGetFromBytesSrcSkipsLastPadStructureContent() {
        final var padContent = mock(XjbPadStructureContent.class);
        final var parent     = mock(XjbComplexType.class);

        when(padContent.byteSize()).thenReturn(8);
        when(padContent.getParent()).thenReturn(parent);
        when(padContent.getFromBytesSrc()).thenCallRealMethod();

        when(parent.getContents()).thenReturn(List.of(padContent));

        assertEquals("/* Skipping 8 byte(s) of padding at end of buffer. */", padContent.getFromBytesSrc());
    }
}
