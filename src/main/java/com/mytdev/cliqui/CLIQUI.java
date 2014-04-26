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
import javax.swing.JPanel;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 * @param <P> command line elements UI panel type
 */
@Getter
public final class CLIQUI<P> {

    private final CommandLineElementsUI<Option, P> optionsUI;

    private final CommandLineElementsUI<Argument, P> argumentsUI;

    public CLIQUI(CLIQUIServiceProvider<P> serviceProvider, CLI cli) {
        this.optionsUI = serviceProvider.getOptionsUIFactory().createUI(cli.getOptions());
        this.argumentsUI = serviceProvider.getArgumentsUIFactory().createUI(cli.getArguments());
    }
    
    public static CLIQUI<JPanel> swing(CLI cli) {
        return new CLIQUI<>(new SwingCLIQUIServiceProvider(), cli);
    }
}
