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

import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.spi.CommandLineElementUIFactory;
import com.mytdev.cliqui.spi.CommandLineElementUIFactoryProvider;
import java.util.EnumMap;
import java.util.Map;
import javax.swing.JComponent;

/**
 *
 * @author Yann D'Isanto
 */
public final class OptionUIFactoryProvider implements CommandLineElementUIFactoryProvider<Option, JComponent> {

    private static final Map<Option.Type, CommandLineElementUIFactory<Option, JComponent>> OPTION_UI_FACTORIES = new EnumMap<>(Option.Type.class);

    static {
        OPTION_UI_FACTORIES.put(Option.Type.FLAG, new FlagOptionUIFactory());
        OPTION_UI_FACTORIES.put(Option.Type.TEXT, new TextOptionUIFactory());
        OPTION_UI_FACTORIES.put(Option.Type.PATH, new PathOptionUIFactory());
        OPTION_UI_FACTORIES.put(Option.Type.PASSWORD, new PasswordOptionUIFactory());
        OPTION_UI_FACTORIES.put(Option.Type.INTEGER, new IntegerOptionUIFactory());
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandLineElementUIFactory<Option, JComponent> getUIFactory(Option option) throws UnsupportedOperationException {
        final CommandLineElementUIFactory<Option, JComponent> factory = OPTION_UI_FACTORIES.get(option.getType());
        if(factory == null) {
            throw new UnsupportedOperationException("unsupported option type: " + option.getType());
        }
        return factory;
    }

}
