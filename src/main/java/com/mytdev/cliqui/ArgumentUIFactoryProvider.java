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

import com.mytdev.cliqui.beans.Argument;
import com.mytdev.cliqui.ui.IntegerArgumentUIFactory;
import com.mytdev.cliqui.ui.IntegerListArgumentUIFactory;
import com.mytdev.cliqui.ui.PathArgumentUIFactory;
import com.mytdev.cliqui.ui.PathListArgumentUIFactory;
import com.mytdev.cliqui.ui.TextArgumentUIFactory;
import com.mytdev.cliqui.ui.TextListArgumentUIFactory;
import com.mytdev.cliqui.ui.spi.CommandLineElementUIFactory;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Yann D'Isanto
 */
public final class ArgumentUIFactoryProvider implements CommandLineElementUIFactoryProvider<Argument> {

    private static final Map<Argument.Type, CommandLineElementUIFactory<Argument>> ARGUMENT_UI_FACTORIES = new EnumMap<>(Argument.Type.class);

    static {
        ARGUMENT_UI_FACTORIES.put(Argument.Type.PATH, new PathArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.PATH_LIST, new PathListArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.TEXT, new TextArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.TEXT_LIST, new TextListArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.INTEGER, new IntegerArgumentUIFactory());
        ARGUMENT_UI_FACTORIES.put(Argument.Type.INTEGER_LIST, new IntegerListArgumentUIFactory());
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommandLineElementUIFactory<Argument> getUIFactory(Argument argument) throws UnsupportedOperationException {
        final CommandLineElementUIFactory<Argument> factory = ARGUMENT_UI_FACTORIES.get(argument.getType());
        if (factory == null) {
            throw new UnsupportedOperationException("unsupported argument type: " + argument.getType());
        }
        return factory;
    }

}
