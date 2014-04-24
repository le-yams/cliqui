/*
 * The MIT License
 *
 * Copyright 2014 Yann D'Isanto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
            if(length > 0) {
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
