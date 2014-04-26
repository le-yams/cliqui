/*
 * Copyright 2014 Yann D'Isanto.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mytdev.cliqui.swing.components;

import com.mytdev.cliqui.beans.CommandLineElement;
import com.mytdev.cliqui.spi.AbstractCommandLineElementUI;
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
 * @param <T>
 */
public final class PasswordOptionUI <T extends CommandLineElement> extends AbstractCommandLineElementUI<T, JComponent> implements ActionListener {

    private final JLabel label = new JLabel();

    private final JPasswordField field = new JPasswordField();
    
    private final JCheckBox displayPasswordCheckBox = new JCheckBox("display");
    
    private final char defaultPasswordEchoChar;

    public PasswordOptionUI(T commandLineElement) {
        super(commandLineElement);
        label.setText(commandLineElement.getLabel());
        label.setToolTipText(commandLineElement.getDescription());
        field.setToolTipText(commandLineElement.getDescription());
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
        if (password.length > 0) {
            cli.add(getCommandLineElement().getName());
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
