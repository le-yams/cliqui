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

import com.mytdev.cliqui.cli.constraints.PathSelectionMode;
import com.mytdev.cliqui.cli.constraints.PathSelectionModeConstraint;
import com.mytdev.cliqui.json.JsonConstraintParser;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathSelectionModeConstraintParser implements JsonConstraintParser<PathSelectionModeConstraint> {

    @Override
    public PathSelectionModeConstraint parse(JsonValue json) {
        final ValueType valueType = json.getValueType();
        if(valueType != ValueType.STRING) {
            throw new IllegalArgumentException("invalid path selection value");
        }
        final String value = ((JsonString) json).getString().toUpperCase();
        final PathSelectionMode mode = PathSelectionMode.valueOf(value);
        return new PathSelectionModeConstraint(mode);
    }

}
