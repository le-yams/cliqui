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
package com.mytdev.cliqui;

import com.mytdev.cliqui.swing.SwingCLIQUIServiceProvider;
import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider;
import com.mytdev.cliqui.spi.CommandLineElementsUI;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Allows to generate options and arguments UI panels for a given CLI instance.
 *
 * @author Yann D'Isanto
 * @param <P> command line elements UI panel type
 */
public final class CLIQUI<P> {

    private final CLI cli;

    private final CommandLineElementsUI<Option, P> optionsUI;

    private final CommandLineElementsUI<Argument, P> argumentsUI;

    /**
     * Creates a CLIQUI instance for the given service provider and the given
     * cli.
     *
     * @param serviceProvider the CLIQUI service provider
     * @param cli the cli to build the UI from
     */
    public CLIQUI(CLIQUIServiceProvider<P> serviceProvider, CLI cli) {
        this.cli = cli;
        this.optionsUI = serviceProvider.getOptionsUIFactory().createUI(cli.getOptions());
        this.argumentsUI = serviceProvider.getArgumentsUIFactory().createUI(cli.getArguments());
    }

    /**
     * @return the command line value from the UI.
     */
    public List<String> getCommandLineValue() {
        final List<String> commandLine = new ArrayList<>();
        commandLine.add(cli.getCommand());
        commandLine.addAll(optionsUI.getCommandLineValue());
        commandLine.addAll(argumentsUI.getCommandLineValue());
        return commandLine;
    }

    /**
     * @return the generated options panel UI.
     */
    public CommandLineElementsUI<Option, P> getOptionsUI() {
        return optionsUI;
    }

    /**
     * @return the generated arguments panel UI.
     */
    public CommandLineElementsUI<Argument, P> getArgumentsUI() {
        return argumentsUI;
    }

    /**
     * Creates then returns a Swing based CLIQUI instance for the given cli.
     *
     * @param cli the cli to build the UI from
     * @return a CLIQUI instance
     */
    public static CLIQUI<JPanel> swing(CLI cli) {
        return new CLIQUI<>(SWING_PROVIDER, cli);
    }

    private static final SwingCLIQUIServiceProvider SWING_PROVIDER = new SwingCLIQUIServiceProvider();

}
