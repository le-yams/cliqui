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
import com.mytdev.cliqui.beans.IntMinMaxConstraint;
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

    public static final class Builder {

        private final List<Option> options = new ArrayList<>();

        private final List<Argument> arguments = new ArrayList<>();

        public Builder flagOption(String name) {
            return flagOption(name, null, null);
        }

        public Builder flagOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.FLAG, label, description));
            return this;
        }

        public Builder textOption(String name) {
            return textOption(name, null, null);
        }

        public Builder textOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.TEXT, label, description));
            return this;
        }

        public Builder intOption(String name) {
            return intOption(name, null, null);
        }

        public Builder intOption(String name, int minValue, int maxValue) {
            return intOption(name, null, null, minValue, maxValue);
        }

        public Builder intOption(String name, String label, String description) {
            return addIntOption(name, label, description);
        }

        public Builder intOption(String name, String label, String description, int minValue, int maxValue) {
            return addIntOption(name, label, description, new IntMinMaxConstraint(minValue, maxValue));
        }

        private Builder addIntOption(String name, String label, String description, Object... constraints) {
            this.options.add(new Option(name, Option.Type.INTEGER, label, description, constraints));
            return this;
        }

        public Builder pathOption(String name) {
            return pathOption(name, null, null);
        }

        public Builder pathOption(String name, PathSelectionMode selectionMode) {
            return pathOption(name, null, null, selectionMode);
        }

        public Builder pathOption(String name, String label, String description) {
            return addPathOption(name, label, description);
        }

        public Builder pathOption(String name, String label, String description, PathSelectionMode selectionMode) {
            return addPathOption(name, label, description, new PathSelectionModeConstraint(selectionMode));
        }

        private Builder addPathOption(String name, String label, String description, Object... constraints) {
            this.options.add(new Option(name, Option.Type.PATH, label, description, constraints));
            return this;
        }

        public Builder passwordOption(String name) {
            return passwordOption(name, null, null);
        }

        public Builder passwordOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.PASSWORD, label, description));
            return this;
        }

        public Builder textArg(String name) {
            return textArg(name, null, null);
        }

        public Builder textArg(String name, String label, String description) {
            this.arguments.add(new Argument(name, Argument.Type.TEXT, label, description));
            return this;
        }

        public Builder textListArg(String name) {
            return textListArg(name, null, null);
        }

        public Builder textListArg(String name, String label, String description) {
            this.arguments.add(new Argument(name, Argument.Type.TEXT_LIST, label, description));
            return this;
        }

        public Builder intArg(String name) {
            return intArg(name, null, null);
        }

        public Builder intArg(String name, int minValue, int maxValue) {
            return intArg(name, null, null, minValue, maxValue);
        }

        public Builder intArg(String name, String label, String description) {
            return addIntArg(name, label, description);
        }

        public Builder intArg(String name, String label, String description, int minValue, int maxValue) {
            return addIntArg(name, label, description, new IntMinMaxConstraint(minValue, maxValue));
        }

        private Builder addIntArg(String name, String label, String description, Object... constraints) {
            this.arguments.add(new Argument(name, Argument.Type.INTEGER, label, description, constraints));
            return this;
        }

        public Builder intListArg(String name) {
            return intListArg(name, null, null);
        }

        public Builder intListArg(String name, int minValue, int maxValue) {
            return intListArg(name, null, null, minValue, maxValue);
        }

        public Builder intListArg(String name, String label, String description) {
            return addIntListArg(name, label, description);
        }

        public Builder intListArg(String name, String label, String description, int minValue, int maxValue) {
            return addIntListArg(name, label, description, new IntMinMaxConstraint(minValue, maxValue));
        }

        private Builder addIntListArg(String name, String label, String description, Object... constraints) {
            this.arguments.add(new Argument(name, Argument.Type.INTEGER_LIST, label, description, constraints));
            return this;
        }

        public Builder pathArg(String name) {
            return addPathArg(name, null, null);
        }

        public Builder pathArg(String name, PathFileExtensionConstraint extensionConstraint) {
            return pathArg(name, null, null, extensionConstraint);
        }

        public Builder pathArg(String name, boolean mustExist) {
            return pathArg(name, null, null, mustExist);
        }

        public Builder pathArg(String name, boolean mustExist, PathFileExtensionConstraint extensionConstraint) {
            return pathArg(name, null, null, mustExist, extensionConstraint);
        }

        public Builder pathArg(String name, PathSelectionMode selectionMode) {
            return pathArg(name, null, null, selectionMode);
        }

        public Builder pathArg(String name, PathSelectionMode selectionMode, PathFileExtensionConstraint extensionConstraint) {
            return pathArg(name, null, null, selectionMode, extensionConstraint);
        }

        public Builder pathArg(String name, PathSelectionMode selectionMode, boolean mustExist) {
            return pathArg(name, null, null, selectionMode, mustExist);
        }

        public Builder pathArg(String name, PathSelectionMode selectionMode, boolean mustExist, PathFileExtensionConstraint extensionConstraint) {
            return pathArg(name, null, null, selectionMode, mustExist, extensionConstraint);
        }

        public Builder pathArg(String name, String label, String description) {
            return addPathArg(name, label, description);
        }

        public Builder pathArg(String name, String label, String description, PathFileExtensionConstraint extensionConstraint) {
            return addPathArg(name, label, description, extensionConstraint);
        }

        public Builder pathArg(String name, String label, String description, boolean mustExist) {
            return addPathArg(name, label, description, new PathExistsConstraint(mustExist));
        }

        public Builder pathArg(String name, String label, String description, boolean mustExist, PathFileExtensionConstraint extensionConstraint) {
            return addPathArg(name, label, description, new PathExistsConstraint(mustExist), extensionConstraint);
        }

        public Builder pathArg(String name, String label, String description, PathSelectionMode selectionMode) {
            return addPathArg(name, label, description, new PathSelectionModeConstraint(selectionMode));
        }

        public Builder pathArg(String name, String label, String description, PathSelectionMode selectionMode, PathFileExtensionConstraint extensionConstraint) {
            return addPathArg(name, label, description, new PathSelectionModeConstraint(selectionMode), extensionConstraint);
        }

        public Builder pathArg(String name, String label, String description, PathSelectionMode selectionMode, boolean mustExist) {
            return addPathArg(name, label, description, new PathSelectionModeConstraint(selectionMode), new PathExistsConstraint(mustExist));
        }

        public Builder pathArg(String name, String label, String description, PathSelectionMode selectionMode, boolean mustExist, PathFileExtensionConstraint extensionConstraint) {
            return addPathArg(name, label, description, new PathSelectionModeConstraint(selectionMode), new PathExistsConstraint(mustExist), extensionConstraint);
        }

        private Builder addPathArg(String name, String label, String description, Object... constraints) {
            this.arguments.add(new Argument(name, Argument.Type.PATH, label, description, constraints));
            return this;
        }

        public Builder pathListArg(String name) {
            return pathListArg(name, null, null);
        }

        public Builder pathListArg(String name, PathSelectionMode selectionMode) {
            return pathListArg(name, null, null, selectionMode);
        }

        public Builder pathListArg(String name, String label, String description) {
            return addPathListArg(name, label, description);
        }

        public Builder pathListArg(String name, String label, String description, PathSelectionMode selectionMode) {
            return addPathListArg(name, label, description, new PathSelectionModeConstraint(selectionMode));
        }

        private Builder addPathListArg(String name, String label, String description, Object... constraints) {
            this.arguments.add(new Argument(name, Argument.Type.PATH_LIST, label, description, constraints));
            return this;
        }

        public CLIQUI build() {
            return new CLIQUI(options, arguments);
        }

    }
}
