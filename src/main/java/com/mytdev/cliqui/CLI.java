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

import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.Constraint;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.cli.PathSelectionMode;
import com.mytdev.cliqui.cli.constraints.IntMaxConstraint;
import com.mytdev.cliqui.cli.constraints.IntMinConstraint;
import com.mytdev.cliqui.cli.constraints.PathExistsConstraint;
import com.mytdev.cliqui.cli.constraints.PathFileExtensionConstraint;
import com.mytdev.cliqui.cli.constraints.PathSelectionModeConstraint;
import com.mytdev.cliqui.reader.JsonCLIReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@AllArgsConstructor
public final class CLI {

    @Getter
    private final String command;

    private final List<Option> options = new ArrayList<>();

    private final List<Argument> arguments = new ArrayList<>();

    /**
     * Add the options from the given option builders to this CLI.
     *
     * @param options the option builders
     * @return this CLI
     */
    public CLI options(Option.Builder... options) {
        for (Option.Builder option : options) {
            this.options.add(option.build());
        }
        return this;
    }

    /**
     * Add the arguments from the given argument builders to this CLI.
     *
     * @param arguments the argument builders
     * @return this CLI
     */
    public CLI arguments(Argument.Builder... arguments) {
        for (Argument.Builder argument : arguments) {
            this.arguments.add(argument.build());
        }
        return this;
    }

    /**
     * Returns this CLI options list
     *
     * @return an unmodifiable List instance
     */
    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    /**
     * Returns this CLI arguments list
     *
     * @return an unmodifiable List instance
     */
    public List<Argument> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    /**
     * Builds a CLI instance from the given json object.
     *
     * @param json the json object describing the cli
     * @return a CLI instance
     * @throws IllegalArgumentException if the given cli json object is not
     * valid
     */
    public static CLI fromJson(JsonObject json) throws IllegalArgumentException {
        return new JsonCLIReader().read(json);
    }

    /**
     * Loads a json cli object from the given url then build a CLI instance
     * accordingly.
     *
     * @param url the url of the input to read the cli json from
     * @return a CLI instance
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if not CLI valid json
     */
    public static CLI fromURL(URL url) throws IOException, IllegalArgumentException {
        return fromJson(readCLIJsonObject(url));
    }

    /**
     * Loads a json cli object from the given input stream then build a CLI
     * instance accordingly.
     *
     * @param input the input to read the cli json from
     * @return a CLI instance
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if not CLI valid json
     */
    public static CLI fromInputStream(InputStream input) throws IOException, IllegalArgumentException {
        return fromJson(readCLIJsonObject(input));
    }

    /**
     * Loads a json cli object from the given file then build a CLI instance
     * accordingly.
     *
     * @param file the file to read the cli json from
     * @return a CLI instance
     * @throws IOException if an I/O error occurs
     * @throws IllegalArgumentException if not CLI valid json
     */
    public static CLI fromInputStream(File file) throws IOException, IllegalArgumentException {
        return fromJson(readCLIJsonObject(file.toURI().toURL()));
    }

    private static JsonObject readCLIJsonObject(URL url) throws IOException, IllegalArgumentException {
        try (InputStream input = url.openStream()) {
            return readCLIJsonObject(input);
        }
    }

    private static JsonObject readCLIJsonObject(InputStream input) throws IOException, IllegalArgumentException {
        return readCLIJsonObject(Json.createReader(input));
    }

    private static JsonObject readCLIJsonObject(JsonReader reader) throws IOException, IllegalArgumentException {
        try {
            return reader.readObject();
        } catch (JsonParsingException ex) {
            throw new IllegalArgumentException("invalid json", ex);
        } catch (JsonException ex) {
            throw new IOException(ex);
        }
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

    public static Constraint min(int min) {
        return new IntMinConstraint(min);
    }

    public static Constraint max(int max) {
        return new IntMaxConstraint(max);
    }

    public static Constraint pathSelectionMode(PathSelectionMode selectionMode) {
        return new PathSelectionModeConstraint(selectionMode);
    }

    public static Constraint pathMustExist() {
        return pathMustExist(true);
    }

    public static Constraint pathMustExist(boolean mustExist) {
        return new PathExistsConstraint(mustExist);
    }

    public static Constraint fileExtensions(String... extensions) {
        return fileExtensions(null, true, extensions);
    }

    public static Constraint fileExtensions(boolean strict, String... extensions) {
        return fileExtensions(null, strict, extensions);
    }

    public static Constraint fileExtensions(String description, boolean strict, String... extensions) {
        return new PathFileExtensionConstraint(description, strict, extensions);
    }

}
