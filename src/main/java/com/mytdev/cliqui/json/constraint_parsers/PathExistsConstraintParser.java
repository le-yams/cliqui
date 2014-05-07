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

import com.mytdev.cliqui.cli.constraints.PathExistsConstraint;
import com.mytdev.cliqui.json.JsonConstraintParser;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathExistsConstraintParser implements JsonConstraintParser<PathExistsConstraint> {

    @Override
    public PathExistsConstraint parse(JsonValue json) {
        final ValueType valueType = json.getValueType();
        switch (valueType) {
            case TRUE:
                return new PathExistsConstraint(true);
            case FALSE:
                return new PathExistsConstraint(false);
            default:
                throw new IllegalArgumentException("invalid path must exist value, true or false is expected");
        }
    }
}
