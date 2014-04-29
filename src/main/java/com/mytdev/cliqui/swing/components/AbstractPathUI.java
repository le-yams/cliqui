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
import com.mytdev.cliqui.cli.constraints.PathExistsConstraint;
import com.mytdev.cliqui.cli.constraints.PathFileExtensionConstraint;
import com.mytdev.cliqui.cli.constraints.PathSelectionMode;
import com.mytdev.cliqui.cli.constraints.PathSelectionModeConstraint;
import com.mytdev.cliqui.util.ValidatingFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public abstract class AbstractPathUI<T extends CommandLineElement> extends AbstractSwingCommandLineElementUI<T> implements ActionListener {

    private static final Map<PathSelectionMode, Integer> SELECTION_MODES = new EnumMap<>(PathSelectionMode.class);

    static {
        SELECTION_MODES.put(PathSelectionMode.FILES_ONLY, JFileChooser.FILES_ONLY);
        SELECTION_MODES.put(PathSelectionMode.FILES_AND_DIRECTORIES, JFileChooser.FILES_AND_DIRECTORIES);
        SELECTION_MODES.put(PathSelectionMode.DIRECTORIES_ONLY, JFileChooser.DIRECTORIES_ONLY);
    }

    protected final JLabel label = new JLabel();

    protected final JTextField field = new JTextField();

    protected final JButton browseButton = new JButton("Browse...");

    protected final JFileChooser fileChooser;

    private final boolean pathMustExist;

    public AbstractPathUI(T commandLineElement) {
        super(commandLineElement);
        label.setText(commandLineElement.getLabel());
        label.setToolTipText(commandLineElement.getDescription());
        field.setToolTipText(commandLineElement.getDescription());
        browseButton.addActionListener(this);
        final PathExistsConstraint pathExistsConstraint = commandLineElement
                .getConstraint(PathExistsConstraint.class);
        pathMustExist = pathExistsConstraint != null
                ? pathExistsConstraint.isPathExistsMandatory()
                : false;
        field.setEditable(pathMustExist == false);
        fileChooser = new ValidatingFileChooser(new ValidatingFileChooser.DefaultValidator() {

            @Override
            public void validateFileSelection(File file) throws IllegalArgumentException {
                if (pathMustExist && file.exists() == false) {
                    throw new IllegalArgumentException("the selected path does not denote an existing file or directory: " + file.toPath());
                }
            }
        });

        fileChooser.setMultiSelectionEnabled(false);
        final PathSelectionModeConstraint selectionModeConstraint = commandLineElement
                .getConstraint(PathSelectionModeConstraint.class);
        fileChooser.setFileSelectionMode(selectionModeConstraint != null
                ? SELECTION_MODES.get(selectionModeConstraint.getMode())
                : JFileChooser.FILES_AND_DIRECTORIES);
        final PathFileExtensionConstraint extensionConstraint = commandLineElement
                .getConstraint(PathFileExtensionConstraint.class);
        if (extensionConstraint != null) {
            final FileFilter extensionFileFilter = new FileNameExtensionFilter(
                    extensionConstraint.getDescription(),
                    extensionConstraint.getExtensions().toArray(new String[0]));
            fileChooser.setFileFilter(extensionFileFilter);
            fileChooser.setAcceptAllFileFilterUsed(extensionConstraint.isStrict() == false);
        }
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                getChangeSupport().fireChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getChangeSupport().fireChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
    }

    @Override
    public void validate() throws IllegalArgumentException {
        final CommandLineElement cle = getCommandLineElement();
        if(cle.isRequired() && field.getText().isEmpty()) {
            throw new IllegalArgumentException("missing required field: " + cle.getLabel());
        }
    }

    @Override
    public final void actionPerformed(ActionEvent e) {
        fileChooser.setSelectedFile(Paths.get(field.getText()).toFile());
        if (fileChooser.showDialog(browseButton, "Select") == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            if (pathMustExist && file.exists() == false) {
            } else {
                field.setText(file.toPath().toString());
            }
        }
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
        return browseButton;
    }
}
