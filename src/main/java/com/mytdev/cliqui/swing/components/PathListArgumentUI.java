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
import com.mytdev.cliqui.cli.constraints.PathExistsConstraint;
import com.mytdev.cliqui.cli.constraints.PathSelectionMode;
import com.mytdev.cliqui.cli.constraints.PathSelectionModeConstraint;
import com.mytdev.cliqui.util.ValidatingFileChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathListArgumentUI extends AbstractListArgumentUI<Path> {

    private static final Map<PathSelectionMode, Integer> SELECTION_MODES = new EnumMap<>(PathSelectionMode.class);

    static {
        SELECTION_MODES.put(PathSelectionMode.FILES_ONLY, JFileChooser.FILES_ONLY);
        SELECTION_MODES.put(PathSelectionMode.FILES_AND_DIRECTORIES, JFileChooser.FILES_AND_DIRECTORIES);
        SELECTION_MODES.put(PathSelectionMode.DIRECTORIES_ONLY, JFileChooser.DIRECTORIES_ONLY);
    }

    protected final JFileChooser fileChooser;

    public PathListArgumentUI(Argument commandLineElement) {
        super(commandLineElement, new StringConverter<Path>() {

            @Override
            public String format(Path path) {
                return path.toString();
            }

            @Override
            public Path parse(String string) {
                return Paths.get(string);
            }
        });
        
        final PathExistsConstraint pathExistsConstraint = commandLineElement
                .getConstraint(PathExistsConstraint.class);
        final boolean pathMustExist = pathExistsConstraint != null
                ? pathExistsConstraint.isPathExistsMandatory()
                : false;
        fileChooser = new ValidatingFileChooser(new ValidatingFileChooser.DefaultValidator() {

            @Override
            public void validateFileSelection(File file) throws IllegalArgumentException {
                if (pathMustExist && file.exists() == false) {
                    throw new IllegalArgumentException("the selected path does not denote an existing file or directory: " + file.toPath());
                }
            }
        });
        fileChooser.setMultiSelectionEnabled(false);
        final PathSelectionModeConstraint selectionModeConstraint = commandLineElement.getConstraint(PathSelectionModeConstraint.class);
        fileChooser.setFileSelectionMode(selectionModeConstraint != null
            ? SELECTION_MODES.get(selectionModeConstraint.getMode())
            : JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setMultiSelectionEnabled(true);
    }

    @Override
    protected void addButtonActionPerformed(ActionEvent e) {
        if (fileChooser.showDialog((Component) e.getSource(), "Select") == JFileChooser.APPROVE_OPTION) {
            for (File file : fileChooser.getSelectedFiles()) {
                listModel.addElement(file.toPath());
            }
        }
    }

}
