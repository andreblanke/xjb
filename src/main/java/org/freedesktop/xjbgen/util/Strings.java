package org.freedesktop.xjbgen.util;

import org.jetbrains.annotations.NotNull;

public final class Strings {

    private Strings() {
    }

    public static @NotNull String capitalize(@NotNull final String string) {
        return string.isEmpty()
            ? string
            : Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
