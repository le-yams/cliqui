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
package com.mytdev.cliqui.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Yann D'Isanto
 */
public final class ValidatingFileChooser extends JFileChooser {

    private final Validator validator;

    public ValidatingFileChooser(Validator validator) {
        this.validator = validator;
    }

    @Override
    public void approveSelection() {
        try {
            if (isMultiSelectionEnabled()) {
                validator.validateMultipleFilesSelection(getSelectedFiles());
            } else {
                validator.validateFileSelection(getSelectedFile());
            }
            super.approveSelection();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getLocalizedMessage(),
                    "validation error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static interface Validator {

        /**
         *
         * @param file
         * @throws IllegalArgumentException
         */
        void validateFileSelection(File file) throws IllegalArgumentException;

        /**
         *
         * @param files
         * @throws IllegalArgumentException
         */
        void validateMultipleFilesSelection(File[] files) throws IllegalArgumentException;
    }
    
    public static class DefaultValidator implements Validator {

        @Override
        public void validateFileSelection(File file) throws IllegalArgumentException {
            // accept
        }

        @Override
        public void validateMultipleFilesSelection(File[] files) throws IllegalArgumentException {
            // accept
        }
        
    }
}
