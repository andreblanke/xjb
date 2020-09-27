package org.freedesktop.xjbgen.util;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

/** Non-instantiable class containing static utility functions for working with {@link String}s. */
public final class Strings {

    /** Prevent instantiation. */
    private Strings() {
    }

    /**
     * Capitalizes the provided {@code string} by converting its first character to its uppercase equivalent.
     *
     * @param string The string to be capitalized.
     *
     * @return The provided {@code string} with its first character capitalized.
     */
    public static @NotNull String capitalize(@NotNull final String string) {
        if (string.isEmpty())
            return string;

        final char firstCharacter = string.charAt(0);
        if (Character.isUpperCase(firstCharacter))
            return string;

        return Character.toUpperCase(firstCharacter) + string.substring(1);
    }

    public static final Predicate<String> ALL_CAPS_PREDICATE = Pattern.compile("^[^a-z]*$").asMatchPredicate();
}
