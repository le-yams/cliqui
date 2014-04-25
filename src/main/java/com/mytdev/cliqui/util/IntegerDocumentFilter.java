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

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Yann D'Isanto
 */
public final class IntegerDocumentFilter extends DocumentFilter {

    private static final Pattern INTEGER_PATTERN = Pattern.compile("\\d+");

    private final Integer min;

    private final Integer max;

    public IntegerDocumentFilter() {
        this(null, null);
    }

    public IntegerDocumentFilter(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text,
        AttributeSet attr) throws BadLocationException {
        if (min == null && max == null) {
            if (isInt(text)) {
                super.insertString(fb, offset, text, attr);
            }
        } else if (isIntWithMinMax(fb, offset, 0, text)) {
            super.insertString(fb, offset, text, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
        AttributeSet attrs) throws BadLocationException {
        if (min == null && max == null) {
            if (isInt(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        } else if (isIntWithMinMax(fb, offset, length, text)) {
            super.replace(fb, offset, length, text, attrs);

        }
    }

    private boolean isInt(String text) {
        return INTEGER_PATTERN.matcher(text).matches();
    }

    private boolean isIntWithMinMax(FilterBypass fb, int offset, int length, String string) {
        try {
            final Document document = fb.getDocument();
            final StringBuilder sb = new StringBuilder(document.getText(0, document.getLength()));
            if (length > 0) {
                sb.replace(offset, offset + length, string);
            } else {
                sb.insert(offset, string);
            }
            final String stringValue = sb.toString();
            if (stringValue.isEmpty()) {
                return true;
            }
            try {
                final int value = Integer.parseInt(stringValue);
                final int minValue = min != null ? min : Integer.MIN_VALUE;
                final int maxValue = max != null ? max : Integer.MAX_VALUE;
                return value >= minValue && value <= maxValue;
            } catch (NumberFormatException ex) {
                return false;
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(IntegerDocumentFilter.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
