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
import com.mytdev.cliqui.spi.AbstractCommandLineElementUI;
import com.mytdev.cliqui.swing.SwingChangeSupport;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 * @param <T> the command line element type
 */
public abstract class AbstractSwingCommandLineElementUI<T extends CommandLineElement> extends AbstractCommandLineElementUI<T, JComponent> {

    private final SwingChangeSupport changeSupport;

    public AbstractSwingCommandLineElementUI(T commandLineElement) {
        super(commandLineElement);
        changeSupport = new SwingChangeSupport(this);
    }

    @Override
    public SwingChangeSupport getChangeSupport() {
        return changeSupport;
    }
    
    @Override
    public void validate() throws IllegalArgumentException {
        // default implementation does nothing
    }
}
