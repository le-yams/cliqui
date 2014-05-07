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

import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.CommandLineElement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Yann D'Isanto
 * @param <T> list element type
 */
public abstract class AbstractListArgumentUI<T> extends AbstractSwingCommandLineElementUI<Argument> {

    protected final DefaultListModel<T> listModel = new DefaultListModel<>();

    private final JPanel mainPanel = new JPanel();

    private final StringConverter<T> stringConverter;

//    public AbstractListArgumentUI(Argument commandLineElement) {
//        this(commandLineElement, new DefaultStringConverter<T>());
//    }
    public AbstractListArgumentUI(Argument commandLineElement, StringConverter<T> stringConverter) {
        super(commandLineElement);
        this.stringConverter = stringConverter;

        final JLabel label = new JLabel(commandLineElement.getLabel());
        final JList<T> list = new JList<>(listModel);
        final JScrollPane listScrollPane = new JScrollPane(list);

        final JButton addButton = new JButton("+");
        final JButton removeButton = new JButton("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addContainerGap(136, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(removeButton, javax.swing.GroupLayout.Alignment.TRAILING)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{addButton, removeButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addButtonActionPerformed(e);
            }
        });
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                final int[] indices = list.getSelectedIndices();
                for (int i = indices.length - 1; i >= 0; i--) {
                    listModel.remove(indices[i]);
                }
            }
        });
        listModel.addListDataListener(new ListDataListener() {

            @Override
            public void intervalAdded(ListDataEvent e) {
                getChangeSupport().fireChange();
            }

            @Override
            public void intervalRemoved(ListDataEvent e) {
                getChangeSupport().fireChange();
            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                getChangeSupport().fireChange();
            }
        });
    }

    protected abstract void addButtonActionPerformed(ActionEvent e);

    @Override
    public void validate() throws IllegalArgumentException {
        final CommandLineElement cle = getCommandLineElement();
        if (cle.isRequired() && listModel.isEmpty()) {
            throw new IllegalArgumentException("missing required field: " + cle.getLabel());
        }
    }

    @Override
    public final List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final Enumeration<T> paths = listModel.elements();
        while (paths.hasMoreElements()) {
            cli.add(stringConverter.format(paths.nextElement()));
        }
        return cli;
    }

    @Override
    public void setCommandLineElementValue(String value) {
        listModel.clear();
        for (String string : value.split("\n")) {
            listModel.addElement(stringConverter.parse(string));
        }
    }

    @Override
    public final JComponent getLabelComponent() {
        return null;
    }

    @Override
    public final JComponent getFieldComponent() {
        return mainPanel;
    }

    @Override
    public final JComponent getFieldSuffixComponent() {
        return null;
    }

    public static interface StringConverter<T> {

        java.lang.String format(T item);

        T parse(java.lang.String string);

        final class Default implements StringConverter<String> {

            @Override
            public String format(String item) {
                return item;
            }

            @Override
            public String parse(String string) {
                return string;
            }

        }
    }
}
