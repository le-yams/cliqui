
package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Argument;

/**
 *
 * @author Yann D'Isanto
 */
public interface ArgumentUIFactory {
    
    ArgumentUI createUI(Argument argument);
}
