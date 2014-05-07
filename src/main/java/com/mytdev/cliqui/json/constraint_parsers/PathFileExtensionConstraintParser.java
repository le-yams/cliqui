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
package com.mytdev.cliqui.json.constraint_parsers;

import com.mytdev.cliqui.cli.constraints.PathFileExtensionConstraint;
import com.mytdev.cliqui.json.JsonConstraintParser;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathFileExtensionConstraintParser implements JsonConstraintParser<PathFileExtensionConstraint> {

    @Override
    public PathFileExtensionConstraint parse(JsonValue json) throws IllegalArgumentException {
        final ValueType valueType = json.getValueType();
        if (valueType == ValueType.ARRAY) {
            return new PathFileExtensionConstraint(null, true, parseExtensionsArray((JsonArray) json));
        }
        if (valueType != ValueType.OBJECT) {
            throw new IllegalArgumentException("invalid file extensions value");
        }
        final JsonObject value = (JsonObject) json;
        final String description = value.getString("description", null);
        final boolean strict = value.getBoolean("strict", true);
        try {
            final JsonArray extensions = value.getJsonArray("extensions");
            if(extensions == null) {
                throw new IllegalArgumentException("extensions field is missing");
            }
            return new PathFileExtensionConstraint(description, strict, parseExtensionsArray(extensions));
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("invalid extensions array", ex);
        }
    }

    private List<String> parseExtensionsArray(JsonArray array) {
        if (array == null) {
            throw new IllegalArgumentException("empty extensions array");
        }
        try {
            final List<String> extensions = new ArrayList<>();
            for (JsonString extension : array.getValuesAs(JsonString.class)) {
                extensions.add(extension.getString());
            }
            return extensions;
        } catch (ClassCastException ex) {
            throw new IllegalArgumentException("invalid extensions array", ex);
        }
    }
}
