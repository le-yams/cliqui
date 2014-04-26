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

import com.mytdev.cliqui.beans.Argument;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Yann D'Isanto
 */
public final class TextListArgumentUI extends AbstractListArgumentUI<String> {

    public TextListArgumentUI(Argument commandLineElement) {
        super(commandLineElement);
    }

    @Override
    protected void addButtonActionPerformed(ActionEvent e) {
        final String input = JOptionPane.showInputDialog(
            (Component) e.getSource(), 
            "", 
            getCommandLineElement().getLabel(), 
            JOptionPane.PLAIN_MESSAGE);
        if(input != null && input.isEmpty() == false) {
            listModel.addElement(input);
        }
    }

}
