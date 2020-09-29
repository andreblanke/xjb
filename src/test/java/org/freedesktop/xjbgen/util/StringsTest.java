package org.freedesktop.xjbgen.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class StringsTest {

    @Test
    public void testReplaceBlockCommentsSingleLineComment() {
        assertEquals("// Comment", Strings.replaceBlockComments("/* Comment */"));
    }

    @Test
    public void testReplaceBlockCommentsMultiLineComment() {
        var expected =
            """
            // Comment
            """.stripIndent();

        var actual = Strings.replaceBlockComments(
            """
            /*
             * Comment
             */
            """.stripIndent()
        );

        assertEquals(expected, actual);
    }

    @Test
    public void testReplaceBlockCommentsXProtoConfigureWindowRequestDocumentationExample() {
        var expected =
            """
            // Configures the given window to the left upper corner
            // with a size of 1024x768 pixels.
            //
            void my_example(xcb_connection_t *c, xcb_window_t window) {
                uint16_t mask = 0;

                mask |= XCB_CONFIG_WINDOW_X;
                mask |= XCB_CONFIG_WINDOW_Y;
                mask |= XCB_CONFIG_WINDOW_WIDTH;
                mask |= XCB_CONFIG_WINDOW_HEIGHT;

                const uint32_t values[] = {
                    0,    // x
                    0,    // y
                    1024, // width
                    768   // height
                };

                xcb_configure_window(c, window, mask, values);
                xcb_flush(c);
            }
            """.stripIndent();

        var actual = Strings.replaceBlockComments(
            """
            /*
             * Configures the given window to the left upper corner
             * with a size of 1024x768 pixels.
             *
             */
            void my_example(xcb_connection_t *c, xcb_window_t window) {
                uint16_t mask = 0;

                mask |= XCB_CONFIG_WINDOW_X;
                mask |= XCB_CONFIG_WINDOW_Y;
                mask |= XCB_CONFIG_WINDOW_WIDTH;
                mask |= XCB_CONFIG_WINDOW_HEIGHT;

                const uint32_t values[] = {
                    0,    /* x */
                    0,    /* y */
                    1024, /* width */
                    768   /* height */
                };

                xcb_configure_window(c, window, mask, values);
                xcb_flush(c);
            }
            """.stripIndent()
        );

        assertEquals(expected, actual);
    }
}
