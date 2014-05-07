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

import com.mytdev.cliqui.cli.CommandLineElement;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The UI for command line elements.
 *
 * @author Yann D'Isanto
 * @param <T> the command line element type
 * @param <P> the UI panel type
 */
public interface CommandLineElementsUI<T extends CommandLineElement, P> {

    /**
     * @return the command line elements of this UI
     */
    Collection<T> getCommandLineElements();

    CommandLineElementUI<T, ?> getCommandLineElementUI(T element);
    
    CommandLineElementUI<T, ?> getCommandLineElementUI(String elementName);

    /**
     * @return the command line value from this UI
     */
    List<String> getCommandLineValue();

    /**
     * Sets this UI element values.
     *
     * @param values a map with element names as key associated with the element
     * values
     */
    void setCommandLineElementValues(Map<String, String> values);

    /**
     * Validates the element UIs.
     *
     * @throws IllegalArgumentException if missing required input or if
     * constraint violation
     * @see CommandLineElementUI#validate()
     */
    void validate() throws IllegalArgumentException;

    /**
     * @return this UI change support.
     */
    ChangeSupport<?> getChangeSupport();

    /**
     * @return this UI panel
     */
    P getPanel();
}
