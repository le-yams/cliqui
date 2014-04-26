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

import com.mytdev.cliqui.cli.CommandLineElement;
import com.mytdev.cliqui.spi.AbstractCommandLineElementsUI;
import com.mytdev.cliqui.spi.CommandLineElementUI;
import com.mytdev.cliqui.spi.CommandLineElementUIFactoryProvider;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public final class SwingCommandLineElementsUI<T extends CommandLineElement> extends AbstractCommandLineElementsUI<T, JPanel> {

    private final JPanel panel = new JPanel();

    
    public SwingCommandLineElementsUI(CommandLineElementUIFactoryProvider<T, JComponent> factoryProvider, List<T> commandLineElements) {
        super(factoryProvider, commandLineElements);
    }
    
    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    protected void initPanel() {
        if(commandLineElements.isEmpty()) {
            return;
        }
        final GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        final GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        final List<Component> labels = new ArrayList<>();
        GroupLayout.ParallelGroup noLabelFieldsGroup = null;
        for (T commandLineElement : commandLineElements) {
            final CommandLineElementUI<T, JComponent> paramUI = (CommandLineElementUI<T, JComponent>) uis.get(commandLineElement);
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
        if(labels.isEmpty() == false) {
            layout.linkSize(SwingConstants.HORIZONTAL, labels.toArray(new Component[labels.size()]));
        }
        final GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for (T commandLineElement : commandLineElements) {
            final CommandLineElementUI<T, JComponent> paramUI = (CommandLineElementUI<T, JComponent>) uis.get(commandLineElement);
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
}
