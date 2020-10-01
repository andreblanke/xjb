package org.freedesktop.xjb;

import org.jetbrains.annotations.NotNull;

/** A container for authorization information to be sent to the X server. */
public class AuthInfo {

    /**
     * String containing the authentication protocol name, such as {@code MIT-MAGIC-COOKIE-1} or
     * {@code XDM-AUTHORIZATION-1}.
     */
    private final @NotNull String name;

    /** Data interpreted in a protocol-specific manner. */
    private final @NotNull String data;

    private static final AuthInfo EMPTY = new AuthInfo("", "");

    public AuthInfo(@NotNull final String name, @NotNull final String data) {
        this.name = name;
        this.data = data;
    }

    public static AuthInfo empty() {
        return EMPTY;
    }

    // <editor-fold desc="Getters">
    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getData() {
        return data;
    }
    // </editor-fold>
}
