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
package com.mytdev.cliqui.spi;

import com.mytdev.cliqui.beans.CommandLineElement;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 * @param <T>
 */
public interface CommandLineElementUI<T extends CommandLineElement> {

    /**
     * @return this ui command line element
     */
    T getCommandLineElement();
    
    /**
     * @return this element command line value
     */
    List<String> getCommandLineValue();

    /**
     * Returns the component to be placed on the left with no horizontal fill.
     *
     * @return the label component instance or null if none.
     */
    JComponent getLabelComponent();

    /**
     * Returns the component to be placed after the label with horizontal fill.
     *
     * @return the field component instance or null if none.
     */
    JComponent getFieldComponent();

    /**
     * Returns the component to be placed on the right with no horizontal fill.
     *
     * @return the field suffix component instance or null if none.
     */
    JComponent getFieldSuffixComponent();
}