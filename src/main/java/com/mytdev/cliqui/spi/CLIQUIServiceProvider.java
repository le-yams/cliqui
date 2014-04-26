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

import com.mytdev.cliqui.beans.Argument;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.ArgumentsUIFactory;
import com.mytdev.cliqui.spi.CLIQUIServiceProvider.OptionsUIFactory;
import java.util.List;

/**
 *
 * @author Yann D'Isanto
 * @param <P> the UI panel type
 */
public interface CLIQUIServiceProvider<P> {

    OptionsUIFactory<P> getOptionsUIFactory();

    ArgumentsUIFactory<P> getArgumentsUIFactory();

    public static interface OptionsUIFactory<P> {

        CommandLineElementsUI<Option, P> createUI(List<Option> options);
    }

    public static interface ArgumentsUIFactory<P> {

        CommandLineElementsUI<Argument, P> createUI(List<Argument> argument);
    }
}
