
package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Option;

/**
 *
 * @author Yann D'Isanto
 */
public interface OptionUIFactory {
    
    OptionUI createUI(Option option);
}
