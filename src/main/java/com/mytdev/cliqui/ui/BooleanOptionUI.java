package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.AbstractOptionUI;
import com.mytdev.cliqui.beans.Option;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public final class BooleanOptionUI extends AbstractOptionUI {

    private final JCheckBox checkBox = new JCheckBox();

    public BooleanOptionUI(Option option) {
        super(option);
        checkBox.setText(option.getLabel());
        checkBox.setToolTipText(option.getDescription());
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        if(checkBox.isSelected()) {
            cli.add(getOption().getName());
        }
        return cli;
    }

    @Override
    public JComponent getLabelComponent() {
        return null;
    }

    @Override
    public JComponent getFieldComponent() {
        return checkBox;
    }

    @Override
    public JComponent getFieldSuffixComponent() {
        return null;
    }
}
