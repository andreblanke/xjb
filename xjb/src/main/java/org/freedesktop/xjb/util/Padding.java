package org.freedesktop.xjb.util;

public final class Padding {

    private Padding() {
    }

    public static int pad(final int n) {
        return (n + 3) & ~3;
    }
}
