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
package com.mytdev.cliqui.reader;

import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.Constraint;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.CLI;
import com.mytdev.cliqui.CLIBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 *
 * @author Yann D'Isanto
 */
public final class JsonCLIReader {

    /**
     * Builds a CLI instance from the given json object.
     *
     * @param json the json object describing the cli
     * @return a CLI instance
     * @throws IllegalArgumentException if the given cli json object is not
     * valid
     */
    public CLI read(JsonObject json) throws IllegalArgumentException {
        final String command = json.getString("command", null);
        final CLIBuilder cli = new CLIBuilder(command);
        populateOptions(cli, json.getJsonObject("options"));
        populateArguments(cli, json.getJsonObject("arguments"));
        return cli;
    }

    private void populateOptions(CLIBuilder cli, JsonObject json) {
        if (json == null) {
            return;
        }
        for (Map.Entry<String, JsonValue> entry : json.entrySet()) {
            final JsonValue value = entry.getValue();
            switch (value.getValueType()) {
                case OBJECT:
                    cli.options(parseOption(entry.getKey(), (JsonObject) entry.getValue()));
                    break;
                case STRING:
                    final Option.Type type = Option.Type.valueOf(((JsonString) value).getString().toUpperCase());
                    cli.options(new Option.Builder(entry.getKey(), type));
                    break;
                default:
                    throw new IllegalArgumentException("invalid option value");
            }
        }
    }

    private void populateArguments(CLIBuilder cli, JsonObject json) {
        if (json == null) {
            return;
        }
        for (Map.Entry<String, JsonValue> entry : json.entrySet()) {
            final JsonValue value = entry.getValue();
            switch (value.getValueType()) {
                case OBJECT:
                    cli.arguments(parseArgument(entry.getKey(), (JsonObject) entry.getValue()));
                    break;
                case STRING:
                    final Argument.Type type = Argument.Type.valueOf(((JsonString) value).getString().toUpperCase());
                    cli.arguments(new Argument.Builder(entry.getKey(), type));
                    break;
                default:
                    throw new IllegalArgumentException("invalid argument value");
            }
        }
    }

    private Option.Builder parseOption(String name, JsonObject json) {
        final Option.Type type = Option.Type.valueOf(json.getString("type").toUpperCase());
        final Option.Builder option = new Option.Builder(name, type)
                .label(json.getString("label", null))
                .description(json.getString("description", null))
                .constraints(parseConstraints(json.getJsonObject("constraints")));
        return option;
    }

    private Argument.Builder parseArgument(String name, JsonObject json) {
        final Argument.Type type = Argument.Type.valueOf(json.getString("type").toUpperCase());
        final Argument.Builder argument = new Argument.Builder(name, type)
                .label(json.getString("label", null))
                .description(json.getString("description", null))
                .constraints(parseConstraints(json.getJsonObject("constraints")));
        return argument;
    }

    private List<Constraint> parseConstraints(JsonObject json) {
        if (json == null) {
            return Collections.emptyList();
        }
        final List<Constraint> constraints = new ArrayList<>();
        for (Map.Entry<String, JsonValue> entry : json.entrySet()) {
            final ConstraintParser constraintParser = CONSTRAINT_PARSERS.get(entry.getKey());
            if (constraintParser == null) {
                throw new IllegalArgumentException("unknown constraint");
            }
            constraints.add(constraintParser.parse(entry.getValue()));
        }
        return constraints;
    }

    private static final Map<String, ConstraintParser> CONSTRAINT_PARSERS = new HashMap<>();

    static {
        CONSTRAINT_PARSERS.put("min", new IntMinConstraintParser());
        CONSTRAINT_PARSERS.put("max", new IntMaxConstraintParser());
        CONSTRAINT_PARSERS.put("pathMustExist", new PathExistsConstraintParser());
        CONSTRAINT_PARSERS.put("pathSelectionMode", new PathSelectionModeConstraintParser());
        CONSTRAINT_PARSERS.put("fileExtensions", new PathFileExtensionConstraintParser());
    }
}
