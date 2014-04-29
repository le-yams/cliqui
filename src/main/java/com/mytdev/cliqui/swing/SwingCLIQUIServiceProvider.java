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
package com.mytdev.cliqui.swing;

import com.mytdev.cliqui.swing.SwingCLIQUIServiceProvider.SwingArgumentsUIFactory;
import com.mytdev.cliqui.swing.SwingCLIQUIServiceProvider.SwingOptionsUIFactory;
import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.ArgumentsUIFactory;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.OptionsUIFactory;
import com.mytdev.cliqui.spi.CommandLineElementsUI;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Yann D'Isanto
 */
public final class SwingCLIQUIServiceProvider implements CLIQUIServiceProvider<JPanel> {

    private final SwingOptionsUIFactory optionsUIFactory = new SwingOptionsUIFactory();

    private final SwingArgumentsUIFactory argumentsUIFactory = new SwingArgumentsUIFactory();

    @Override
    public SwingOptionsUIFactory getOptionsUIFactory() {
        return optionsUIFactory;
    }

    @Override
    public SwingArgumentsUIFactory getArgumentsUIFactory() {
        return argumentsUIFactory;
    }

    public final class SwingOptionsUIFactory implements OptionsUIFactory<JPanel> {

        @Override
        public CommandLineElementsUI<Option, JPanel> createUI(List<Option> options) {
            return new SwingCommandLineElementsUI<>(new OptionUIFactoryProvider(), options);
        }

    }

    public final class SwingArgumentsUIFactory implements ArgumentsUIFactory<JPanel> {

        @Override
        public CommandLineElementsUI<Argument, JPanel> createUI(List<Argument> arguments) {
            return new SwingCommandLineElementsUI<>(new ArgumentUIFactoryProvider(), arguments);
        }

    }
}
