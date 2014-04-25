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
package com.mytdev.cliqui;

import com.mytdev.cliqui.beans.Argument;
import com.mytdev.cliqui.beans.CommandLineElement;
import com.mytdev.cliqui.ui.IntegerArgumentUIFactory;
import com.mytdev.cliqui.ui.PathArgumentUIFactory;
import com.mytdev.cliqui.ui.TextArgumentUIFactory;
import com.mytdev.cliqui.ui.spi.CommandLineElementUI;
import com.mytdev.cliqui.ui.spi.CommandLineElementUIFactory;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public final class CommandLineElementsUI<T extends CommandLineElement> extends JPanel {

    private static final Map<Argument.Type, CommandLineElementUIFactory<Argument>> ARGUMENT_UI_FACTORIES = new EnumMap<>(Argument.Type.class);

    static {
        ARGUMENT_UI_FACTORIES.put(Argument.Type.PATH, new PathArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.TEXT, new TextArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.INTEGER, new IntegerArgumentUIFactory());
    }

    private final CommandLineElementUIFactoryProvider<T> factoryProvider;
    
    private final List<T> commandLineElements;

    private final Map<T, CommandLineElementUI<T>> uis;

    
    public CommandLineElementsUI(CommandLineElementUIFactoryProvider<T> factoryProvider, List<T> commandLineElements) {
        this.factoryProvider = factoryProvider;
        this.commandLineElements = new ArrayList<>(commandLineElements);
        uis = new HashMap<>(commandLineElements.size());
        for (T commandLineElement : commandLineElements) {
            if (commandLineElement != null) {
                uis.put(commandLineElement, buildCommandLineElementUI(commandLineElement));
            }
        }
        initialize();
    }

    public List<String> getCommandLineValue() {
        final List<String> cliArgs = new ArrayList<>();
        for (T commandLineElement : commandLineElements) {
            cliArgs.addAll(uis.get(commandLineElement).getCommandLineValue());
        }
        return cliArgs;
    }

    public Collection<T> getCommandLineElements() {
        return Collections.unmodifiableCollection(commandLineElements);
    }
    
    private void initialize() {
        if(commandLineElements.isEmpty()) {
            return;
        }
        final GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        final GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        final List<Component> labels = new ArrayList<>();
        GroupLayout.ParallelGroup noLabelFieldsGroup = null;
        for (T commandLineElement : commandLineElements) {
            final CommandLineElementUI<T> paramUI = uis.get(commandLineElement);
            final Component label = paramUI.getLabelComponent();
            final Component field = paramUI.getFieldComponent();
            final Component fieldSuffix = paramUI.getFieldSuffixComponent();
            if (label != null) {
                labels.add(label);
                GroupLayout.SequentialGroup group = layout.createSequentialGroup()
                    .addComponent(label)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
                if (fieldSuffix != null) {
                    group.addComponent(field, GroupLayout.DEFAULT_SIZE, field.getPreferredSize().width, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldSuffix);
                } else {
                    group.addComponent(field);
                }
                hGroup.addGroup(group);
            } else {
                if (noLabelFieldsGroup == null) {
                    noLabelFieldsGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
                    hGroup.addGroup(noLabelFieldsGroup);
                }
                noLabelFieldsGroup.addComponent(field);
            }
        }
        layout.setHorizontalGroup(hGroup);

        layout.linkSize(SwingConstants.HORIZONTAL, labels.toArray(new Component[labels.size()]));

        final GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for (T commandLineElement : commandLineElements) {
            final CommandLineElementUI<T> paramUI = uis.get(commandLineElement);
            final Component label = paramUI.getLabelComponent();
            final Component field = paramUI.getFieldComponent();
            final Component fieldSuffix = paramUI.getFieldSuffixComponent();
            if (label != null) {
                GroupLayout.ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                if (fieldSuffix != null) {
                    group.addComponent(fieldSuffix);
                }
                vGroup.addGroup(group);
            } else {
                vGroup.addComponent(field);
            }
            vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(vGroup));
    }

    private CommandLineElementUI<T> buildCommandLineElementUI(T commandLineElement) {
        final CommandLineElementUIFactory<T> factory = factoryProvider.getUIFactory(commandLineElement);
        if (factory == null) {
            throw new IllegalArgumentException("unsupported command line element: " + commandLineElement);
        }
        return factory.createUI(commandLineElement);
    }
}
