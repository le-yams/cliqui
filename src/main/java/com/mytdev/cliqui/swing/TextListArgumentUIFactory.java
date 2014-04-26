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

import com.mytdev.cliqui.swing.components.TextListArgumentUI;
import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.spi.CommandLineElementUI;
import com.mytdev.cliqui.spi.CommandLineElementUIFactory;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public final class TextListArgumentUIFactory implements CommandLineElementUIFactory<Argument, JComponent> {

    @Override
    public CommandLineElementUI<Argument, JComponent> createUI(Argument argument) {
        return new TextListArgumentUI(argument);
    }
}
