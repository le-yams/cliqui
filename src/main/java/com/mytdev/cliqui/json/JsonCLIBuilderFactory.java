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
package com.mytdev.cliqui.json;

import com.mytdev.cliqui.cli.Argument;
import com.mytdev.cliqui.cli.CLI;
import com.mytdev.cliqui.cli.Constraint;
import com.mytdev.cliqui.cli.Option;
import com.mytdev.cliqui.cli.spi.CLIBuilderFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 *
 * @author Yann D'Isanto
 */
public final class JsonCLIBuilderFactory implements CLIBuilderFactory {

    private final JsonConstraintParserProvider constraintParserProvider = new ProxyJsonConstraintParserProvider();

    /**
     * The json object to create cli builder from
     */
    private JsonObject json;

    public JsonCLIBuilderFactory() {
    }

    public JsonCLIBuilderFactory(JsonObject json) {
        this.json = json;
    }

    /**
     * @return the json object this factory creates cli builder from
     */
    public JsonObject getJson() {
        return json;
    }

    /**
     * Sets the json object this factory creates cli builder from
     *
     * @param json a valid cli json object. If it doesn't meet cli json
     * requirements, invoking this factory {@code create() } method will throw
     * an IllegalArgumentException
     */
    public void setJson(JsonObject json) {
        this.json = json;
    }

    /**
     * Creates a cli builder instance from this factory underlying cli json
     * object.
     *
     * @return a CLI.Builder instance
     * @throws NullPointerException if this factory underlying cli json object
     * is not valid
     * @throws IllegalArgumentException if this factory underlying cli json
     * object is not valid
     */
    @Override
    public CLI.Builder create() throws IllegalArgumentException {
        final String command = json.getString("command", null);
        final CLI.Builder builder = new CLI.Builder(command);
        populateOptions(builder, json.getJsonObject("options"));
        populateArguments(builder, json.getJsonObject("arguments"));
        return builder;
    }

    private void populateOptions(CLI.Builder cli, JsonObject json) {
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

    private void populateArguments(CLI.Builder cli, JsonObject json) {
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
        final String typeValue = json.getString("type", null);
        if (typeValue == null) {
            throw new IllegalArgumentException(name + " option type is missing");
        }
        final Option.Type type = Option.Type.valueOf(typeValue.toUpperCase());
        final Option.Builder option = new Option.Builder(name, type)
            .required(json.getBoolean("required", false))
            .label(json.getString("label", null))
            .description(json.getString("description", null))
            .constraints(parseConstraints(json.getJsonObject("constraints")));
        return option;
    }

    private Argument.Builder parseArgument(String name, JsonObject json) {
        final String typeValue = json.getString("type", null);
        if (typeValue == null) {
            throw new IllegalArgumentException(name + " argument type is missing");
        }
        final Argument.Type type = Argument.Type.valueOf(typeValue.toUpperCase());
        final Argument.Builder argument = new Argument.Builder(name, type)
            .required(json.getBoolean("required", false))
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
            final JsonConstraintParser constraintParser = constraintParserProvider.getParser(entry.getKey());
            if (constraintParser == null) {
                throw new IllegalArgumentException("unknown constraint");
            }
            constraints.add(constraintParser.parse(entry.getValue()));
        }
        return constraints;
    }
}
