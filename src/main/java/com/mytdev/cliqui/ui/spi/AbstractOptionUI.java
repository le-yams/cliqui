package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Option;
import lombok.AllArgsConstructor;

/**
 *
 * @author Yann D'Isanto
 */
@AllArgsConstructor
public abstract class AbstractOptionUI implements OptionUI {

    private final Option option;

    @Override
    public final Option getOption() {
        return option;
    }
}
