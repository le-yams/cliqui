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
package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.beans.IntMinMaxConstraint;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.ui.spi.CommandLineElementUI;
import com.mytdev.cliqui.ui.spi.CommandLineElementUIFactory;
import com.mytdev.cliqui.util.IntegerDocumentFilter;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Yann D'Isanto
 */
public final class IntegerOptionUIFactory implements CommandLineElementUIFactory<Option> {

    @Override
    public CommandLineElementUI<Option> createUI(Option option) {
        final IntMinMaxConstraint constraint = option.getConstraint(IntMinMaxConstraint.class);
        final DocumentFilter documentFilter = constraint != null
            ? new IntegerDocumentFilter(constraint.getMin(), constraint.getMax())
            : new IntegerDocumentFilter();
        return new TextOptionUI(option, documentFilter);
    }
}
