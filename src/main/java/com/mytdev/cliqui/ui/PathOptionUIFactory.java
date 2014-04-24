package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.OptionUI;
import com.mytdev.cliqui.ui.spi.OptionUIFactory;
import com.mytdev.cliqui.beans.Option;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathOptionUIFactory implements OptionUIFactory {

    @Override
    public OptionUI createUI(Option option) {
        return new PathOptionUI(option);
    }
}
