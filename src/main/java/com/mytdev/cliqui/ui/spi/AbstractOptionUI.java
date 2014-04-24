package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Option;

/**
 *
 * @author Yann D'Isanto
 */
public abstract class AbstractOptionUI implements OptionUI {

    private final Option option;

    public AbstractOptionUI(Option option) {
        this.option = option;
    }

    @Override
    public final Option getOption() {
        return option;
    }
}
