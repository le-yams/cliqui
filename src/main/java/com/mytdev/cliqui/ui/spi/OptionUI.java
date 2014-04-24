
package com.mytdev.cliqui.ui.spi;

import com.mytdev.cliqui.beans.Option;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public interface OptionUI extends CommandLineElementUI {
    
    Option getOption();
    
//    void setValue(String value);
    
    JComponent getLabelComponent();
    
    JComponent getFieldComponent();
    
    JComponent getFieldSuffixComponent();
}
