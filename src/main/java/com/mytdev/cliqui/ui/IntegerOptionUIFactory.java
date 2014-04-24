package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.OptionUI;
import com.mytdev.cliqui.ui.spi.OptionUIFactory;
import com.mytdev.cliqui.beans.IntMinMaxConstraint;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.util.IntegerDocumentFilter;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Yann D'Isanto
 */
public final class IntegerOptionUIFactory implements OptionUIFactory {

    @Override
    public OptionUI createUI(Option option) {
        final IntMinMaxConstraint constraint = option.getConstraint(IntMinMaxConstraint.class);
        final DocumentFilter documentFilter = constraint != null 
            ?  new IntegerDocumentFilter(constraint.getMin(), constraint.getMax())
            : new IntegerDocumentFilter();
        return new TextOptionUI(option, documentFilter);
    }
}
