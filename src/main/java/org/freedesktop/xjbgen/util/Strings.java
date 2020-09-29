package org.freedesktop.xjbgen.util;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Predicate;
import java.util.regex.Matcher;
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

    // <editor-fold desc="replaceBlockComments">
    private static final Pattern ANY_BLOCK_COMMENT_PATTERN =
        Pattern.compile("/\\*(.*?)\\*/", Pattern.DOTALL);

    private static final Pattern SINGLE_LINE_BLOCK_COMMENT_PATTERN =
        Pattern.compile("^/\\*(.*?)[^\\S\n\r]*\\*/$");

    public static @NotNull String replaceBlockComments(@NotNull final String string) {
        final class SubstringChange {

            final int start;
            final int end;

            final String replacement;

            SubstringChange(final Matcher matcher, final String replacement) {
                this.start       = matcher.start();
                this.end         = matcher.end();
                this.replacement = replacement;
            }
        }
        final Deque<SubstringChange> stringChanges = new LinkedList<>();

        final Matcher blockCommentMatcher = ANY_BLOCK_COMMENT_PATTERN.matcher(string);
        while (blockCommentMatcher.find()) {
            final String blockComment = blockCommentMatcher.group();

            final Matcher singleLineBlockCommentMatcher = SINGLE_LINE_BLOCK_COMMENT_PATTERN.matcher(blockComment);
            if (singleLineBlockCommentMatcher.matches()) {
                final String singleLineComment = singleLineBlockCommentMatcher.replaceAll("//$1");

                /*
                 * We cannot directly mutate a StringBuilder, as otherwise the deletion and insertion of substrings
                 * of our original string would cause the start and end indices of our matches to go out of sync.
                 *
                 * Instead, we push our changes into a Deque and then apply them in in the reverse order we pushed
                 * them into it.
                 */
                stringChanges.push(new SubstringChange(blockCommentMatcher, singleLineComment));
            } else {
                final String multiLineComment =
                    blockComment
                        .replaceFirst("^\\s*/\\*\\s*\n", "")
                        .replaceFirst("(?m)^\\s*\\*/\\s*", "")
                        .replaceAll("(?m)^\\s*\\*(.*?)?\\s*$", "//$1");

                stringChanges.push(new SubstringChange(blockCommentMatcher, multiLineComment));
            }
        }

        final var builder = new StringBuilder(string);
        for (var replacement : stringChanges) {
            builder.delete(replacement.start, replacement.end);
            builder.insert(replacement.start, replacement.replacement);
        }
        return builder.toString();
    }
    // </editor-fold>
}
