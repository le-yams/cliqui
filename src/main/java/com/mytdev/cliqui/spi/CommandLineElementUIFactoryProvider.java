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

/**
 *
 * @author Yann D'Isanto
 * @param <T> the command line element type
 * @param <C> the command line element UI component type
 */
public interface CommandLineElementUIFactoryProvider<T extends CommandLineElement, C> {

    /**
     * Returns the UI factory for the given command line element.
     *
     * @param commandLineElement
     * @return a CommandLineElementUIFactory instance
     * @throws UnsupportedOperationException if the specified command line
     * element is not supported.
     */
    CommandLineElementUIFactory<T, C> getUIFactory(T commandLineElement) throws UnsupportedOperationException;
}
