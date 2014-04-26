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

import com.mytdev.cliqui.ui.spi.ArgumentUIFactoryProvider;
import com.mytdev.cliqui.ui.spi.OptionUIFactoryProvider;
import com.mytdev.cliqui.beans.Argument;
import com.mytdev.cliqui.beans.MaxConstraint;
import com.mytdev.cliqui.beans.MinConstraint;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.beans.PathExistsConstraint;
import com.mytdev.cliqui.beans.PathFileExtensionConstraint;
import com.mytdev.cliqui.beans.PathSelectionMode;
import com.mytdev.cliqui.beans.PathSelectionModeConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
public final class CLIQUI {

    @Getter
    private final CommandLineElementsUI<Option> optionsUI;

    @Getter
    private final CommandLineElementsUI<Argument> argumentsUI;

    public CLIQUI(List<Option> options, List<Argument> arguments) {
        this.optionsUI = new CommandLineElementsUI<>(new OptionUIFactoryProvider(), options);
        this.argumentsUI = new CommandLineElementsUI<>(new ArgumentUIFactoryProvider(), arguments);
    }

    private static Option.Builder option(String name, Option.Type type) {
        return new Option.Builder(name, type);
    }

    public static Option.Builder flagOption(String name) {
        return option(name, Option.Type.FLAG);
    }

    public static Option.Builder intOption(String name) {
        return option(name, Option.Type.INTEGER);
    }

    public static Option.Builder passwordOption(String name) {
        return option(name, Option.Type.PASSWORD);
    }

    public static Option.Builder pathOption(String name) {
        return option(name, Option.Type.PATH);
    }

    public static Option.Builder textOption(String name) {
        return option(name, Option.Type.TEXT);
    }

    private static Argument.Builder arg(String name, Argument.Type type) {
        return new Argument.Builder(name, type);
    }
    
    public static Argument.Builder pathArg(String name) {
        return arg(name, Argument.Type.PATH);
    }

    public static Argument.Builder pathListArg(String name) {
        return arg(name, Argument.Type.PATH_LIST);
    }

    public static Argument.Builder textArg(String name) {
        return arg(name, Argument.Type.TEXT);
    }

    public static Argument.Builder textListArg(String name) {
        return arg(name, Argument.Type.TEXT_LIST);
    }

    public static Argument.Builder intArg(String name) {
        return arg(name, Argument.Type.INTEGER);
    }

    public static Argument.Builder intListArg(String name) {
        return arg(name, Argument.Type.INTEGER_LIST);
    }

    public static Object min(int min) {
        return new MinConstraint(min);
    }

    public static Object max(int max) {
        return new MaxConstraint(max);
    }

    public static Object pathSelectionMode(PathSelectionMode selectionMode) {
        return new PathSelectionModeConstraint(selectionMode);
    }

    public static Object pathMustExist() {
        return pathMustExist(true);
    }

    public static Object pathMustExist(boolean mustExist) {
        return new PathExistsConstraint(mustExist);
    }

    public static Object fileExtensions(String... extensions) {
        return fileExtensions(null, true, extensions);
    }

    public static Object fileExtensions(boolean strict, String... extensions) {
        return fileExtensions(null, strict, extensions);
    }

    public static Object fileExtensions(String description, boolean strict, String... extensions) {
        return new PathFileExtensionConstraint(description, strict, extensions);
    }

    public static final class Builder {

        private final List<Option> options = new ArrayList<>();

        private final List<Argument> arguments = new ArrayList<>();

        public Builder options(Option.Builder... builders) {
            for (Option.Builder builder : builders) {
                this.options.add(builder.build());
            }
            return this;
        }
        
        public Builder arguments(Argument.Builder... builders) {
            for (Argument.Builder builder : builders) {
                this.arguments.add(builder.build());
            }
            return this;
        }
        
        public CLIQUI build() {
            return new CLIQUI(options, arguments);
        }

    }
}
