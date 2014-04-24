package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.AbstractOptionUI;
import com.mytdev.cliqui.beans.Option;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author Yann D'Isanto
 */
public final class PasswordOptionUI extends AbstractOptionUI implements ActionListener {

    private final JLabel label = new JLabel();

    private final JPasswordField field = new JPasswordField();
    
    private final JCheckBox displayPasswordCheckBox = new JCheckBox("display");
    
    private final char defaultPasswordEchoChar;

    public PasswordOptionUI(Option option) {
        super(option);
        label.setText(option.getLabel());
        label.setToolTipText(option.getDescription());
        field.setToolTipText(option.getDescription());
        defaultPasswordEchoChar = field.getEchoChar();
        displayPasswordCheckBox.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final char echoChar = displayPasswordCheckBox.isSelected()
                ? 0
                : defaultPasswordEchoChar;
        field.setEchoChar(echoChar);
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final char[] password = field.getPassword();
        if(password.length > 0) {
            cli.add(getOption().getName());
            cli.add(new String(password));
        }
        return cli;
    }

    @Override
    public JComponent getLabelComponent() {
        return label;
    }

    @Override
    public JComponent getFieldComponent() {
        return field;
    }

    @Override
    public JComponent getFieldSuffixComponent() {
        return displayPasswordCheckBox;
    }
}
