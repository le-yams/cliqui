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

import com.mytdev.cliqui.cli.CommandLineElement;
import com.mytdev.cliqui.spi.AbstractCommandLineElementUI;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public abstract class AbstractTextUI<T extends CommandLineElement> extends AbstractCommandLineElementUI<T, JComponent> {

    protected final JLabel label = new JLabel();

    protected final JTextField field = new JTextField();

    public AbstractTextUI(T commandLineElement) {
        this(commandLineElement, null);
    }

    public AbstractTextUI(T commandLineElement, DocumentFilter documentFilter) {
        super(commandLineElement);
        if (documentFilter != null) {
            final PlainDocument document = (PlainDocument) field.getDocument();
            document.setDocumentFilter(documentFilter);
        }
        label.setText(commandLineElement.getLabel());
        label.setToolTipText(commandLineElement.getDescription());
        field.setToolTipText(commandLineElement.getDescription());
    }

    @Override
    public final JComponent getLabelComponent() {
        return label;
    }

    @Override
    public final JComponent getFieldComponent() {
        return field;
    }

    @Override
    public final JComponent getFieldSuffixComponent() {
        return null;
    }

}
