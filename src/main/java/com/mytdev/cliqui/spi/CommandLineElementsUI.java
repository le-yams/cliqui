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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Yann D'Isanto
 * @param <T> the command line element type
 * @param <P> the UI panel type
 */
public abstract class CommandLineElementsUI<T extends CommandLineElement, P> {

    private final CommandLineElementUIFactoryProvider<T, ?> factoryProvider;

    protected final List<T> commandLineElements;

    protected final Map<T, CommandLineElementUI<T, ?>> uis;

    public CommandLineElementsUI(CommandLineElementUIFactoryProvider<T, ?> factoryProvider, List<T> commandLineElements) {
        this.factoryProvider = factoryProvider;
        this.commandLineElements = Collections.unmodifiableList(commandLineElements);
        final Map<T, CommandLineElementUI<T, ?>> uisMap = new HashMap<>(commandLineElements.size());
        for (T commandLineElement : commandLineElements) {
            if (commandLineElement != null) {
                uisMap.put(commandLineElement, buildCommandLineElementUI(commandLineElement));
            }
        }
        uis = Collections.unmodifiableMap(uisMap);
        initPanel();
    }

    public final List<String> getCommandLineValue() {
        final List<String> cliArgs = new ArrayList<>();
        for (T commandLineElement : commandLineElements) {
            cliArgs.addAll(uis.get(commandLineElement).getCommandLineValue());
        }
        return cliArgs;
    }

    public final Collection<T> getCommandLineElements() {
        return commandLineElements;
    }

    protected abstract void initPanel();

    public abstract P getPanel();

    /**
     * Creates then returns the given command line element UI. The UI component type is
     * using the factory given at this instance constructor.
     *
     * @param commandLineElement
     * @return
     */
    protected final CommandLineElementUI<T, ?> buildCommandLineElementUI(T commandLineElement) {
        final CommandLineElementUIFactory<T, ?> factory = factoryProvider.getUIFactory(commandLineElement);
        if (factory == null) {
            throw new IllegalArgumentException("unsupported command line element: " + commandLineElement);
        }
        return factory.createUI(commandLineElement);
    }
}
