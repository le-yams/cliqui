package com.mytdev.cliqui.beans;

import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
public final class Argument extends CommandLineElement {

    @NonNull
    private final Type type;

    public Argument(String name, Type type, String label, String description) {
        super(name, label, description);
        this.type = type;
    }

    public static enum Type {

        TEXT,
        TEXT_LIST,
        INTEGER,
        INTEGER_LIST,
        PATH,
        PATH_LIST

    }
}
