
package com.mytdev.cliqui.ui.spi;

import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public interface ArgumentUI extends CommandLineElementUI {
    
    /**
     * @return this argument ui component
     */
    JComponent getComonent();
}
