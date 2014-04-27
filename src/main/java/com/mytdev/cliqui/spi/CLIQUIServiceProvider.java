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

import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.ArgumentsUIFactory;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.OptionsUIFactory;
import java.util.List;

/**
 *
 * @author Yann D'Isanto
 * @param <P> the UI panel type
 */
public interface CLIQUIServiceProvider<P> {

    /**
     * @return the factory to create options UI
     */
    OptionsUIFactory<P> getOptionsUIFactory();

    /**
     * @return the factory to create arguments UI
     */
    ArgumentsUIFactory<P> getArgumentsUIFactory();

    /**
     * Factory to create options UI
     *
     * @param <P> the UI panel type
     */
    public static interface OptionsUIFactory<P> {

        /**
         * Creates then returns the UI for the given options
         *
         * @param options the options to generate th UI from
         * @return the options UI
         */
        CommandLineElementsUI<Option, P> createUI(List<Option> options);
    }

    /**
     * Factory to create arguments UI
     *
     * @param <P> the UI panel type
     */
    public static interface ArgumentsUIFactory<P> {

        /**
         * Creates then returns the UI for the given argument
         *
         * @param argument the argument to generate th UI from
         * @return the argument UI
         */
        CommandLineElementsUI<Argument, P> createUI(List<Argument> argument);
    }
}
