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

import com.mytdev.cliqui.beans.Option;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author Yann D'Isanto
 */
public final class TextOptionUI extends AbstractTextUI<Option> {

    public TextOptionUI(Option option) {
        super(option);
    }

    public TextOptionUI(Option option, DocumentFilter documentFilter) {
        super(option, documentFilter);
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final String value = field.getText();
        if(value.isEmpty() == false) {
            cli.add(getCommandLineElement().getName());
            cli.add(value);
        }
        return cli;
    }
}
