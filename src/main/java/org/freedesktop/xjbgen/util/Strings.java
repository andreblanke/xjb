package org.freedesktop.xjbgen.util;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

public final class Strings {

    private Strings() {
    }

    public static @NotNull String capitalize(@NotNull final String string) {
        return string.isEmpty()
            ? string
            : Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static final Predicate<String> ALL_CAPS_PREDICATE = Pattern.compile("^[^a-z]*$").asMatchPredicate();
}
