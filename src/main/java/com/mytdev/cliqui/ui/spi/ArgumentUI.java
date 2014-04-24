
package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Argument;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public interface ArgumentUI extends CommandLineElementUI {
    
    /**
     * @return this ui argument
     */
    Argument getArgument();
    
    /**
     * @return this argument ui component
     */
    JComponent getComonent();
}
