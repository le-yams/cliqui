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
package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.beans.Argument;
import com.mytdev.cliqui.beans.IntMinMaxConstraint;
import com.mytdev.cliqui.util.IntegerDocumentFilter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Yann D'Isanto
 */
public final class IntegerListArgumentUI extends AbstractListArgumentUI<String> {

    private final DocumentFilter documentFilter;

    public IntegerListArgumentUI(Argument commandLineElement) {
        super(commandLineElement);

        final IntMinMaxConstraint constraint = commandLineElement.getConstraint(IntMinMaxConstraint.class);
        documentFilter = constraint != null
            ? new IntegerDocumentFilter(constraint.getMin(), constraint.getMax())
            : new IntegerDocumentFilter();
    }

    @Override
    protected void addButtonActionPerformed(ActionEvent e) {
        final JTextField inputField = new JTextField();
        final PlainDocument document = (PlainDocument) inputField.getDocument();
        document.setDocumentFilter(documentFilter);
        if (JOptionPane.showConfirmDialog(
            (Component) e.getSource(),
            inputField,
            getCommandLineElement().getLabel(),
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            final String input = inputField.getText();
            if (input.isEmpty() == false) {
                listModel.addElement(input);
            }
        }
    }

}
