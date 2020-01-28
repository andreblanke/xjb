package org.freedesktop.xjb;

import org.jetbrains.annotations.NotNull;

/** A container for authorization information to be sent to the X server. */
public final class AuthInfo {

    /** String containing the authentication protocol name, such as "MIT-MAGIC-COOKIE-1" or "XDM-AUTHORIZATION-1". */
    private final @NotNull String name;

    /** Data interpreted in a protocol-specific manner. */
    private final @NotNull String data;

    AuthInfo(@NotNull final String name, @NotNull final String data) {
        this.name = name;
        this.data = data;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getData() {
        return data;
    }
}
