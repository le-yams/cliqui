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
package com.mytdev.cliqui.swing;

import com.mytdev.cliqui.beans.CommandLineElement;
import com.mytdev.cliqui.spi.AbstractCommandLineElementUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public final class BooleanOptionUI <T extends CommandLineElement> extends AbstractCommandLineElementUI<T> {

    private final JCheckBox checkBox = new JCheckBox();

    public BooleanOptionUI(T commandLineElement) {
        super(commandLineElement);
        checkBox.setText(commandLineElement.getLabel());
        checkBox.setToolTipText(commandLineElement.getDescription());
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        if(checkBox.isSelected()) {
            cli.add(getCommandLineElement().getName());
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