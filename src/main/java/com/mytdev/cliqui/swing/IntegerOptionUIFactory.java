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

import com.mytdev.cliqui.swing.components.TextOptionUI;
import com.mytdev.cliqui.cli.constraints.IntMaxConstraint;
import com.mytdev.cliqui.cli.constraints.IntMinConstraint;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.spi.CommandLineElementUI;
import com.mytdev.cliqui.spi.CommandLineElementUIFactory;
import com.mytdev.cliqui.util.IntegerDocumentFilter;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public final class IntegerOptionUIFactory implements CommandLineElementUIFactory<Option, JComponent> {

    @Override
    @SuppressWarnings("unchecked")
    public CommandLineElementUI<Option, JComponent> createUI(Option option) {
        final IntMinConstraint minConstraint = option.getConstraint(IntMinConstraint.class);
        final IntMaxConstraint maxConstraint = option.getConstraint(IntMaxConstraint.class);
        return new TextOptionUI(option, IntegerDocumentFilter.create(minConstraint, maxConstraint));
    }
}
