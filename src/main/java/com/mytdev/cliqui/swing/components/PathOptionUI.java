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
package com.mytdev.cliqui.swing.components;

import com.mytdev.cliqui.cli.Option;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathOptionUI extends AbstractPathUI<Option> {

    public PathOptionUI(Option option) {
        super(option);
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final String path = field.getText();
        if (path.isEmpty() == false) {
            cli.add(getCommandLineElement().getName());
            cli.add(path);
        }
        return cli;
    }
}
