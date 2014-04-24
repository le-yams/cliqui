package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Argument;
import lombok.AllArgsConstructor;

/**
 *
 * @author Yann D'Isanto
 */
@AllArgsConstructor
public abstract class AbstractArgumentUI implements ArgumentUI {

    private final Argument argument;

    @Override
    public final Argument getArgument() {
        return argument;
    }
}
