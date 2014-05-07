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

import com.mytdev.cliqui.json.constraint_parsers.PathExistsConstraintParser;
import com.mytdev.cliqui.json.constraint_parsers.IntMinConstraintParser;
import com.mytdev.cliqui.json.constraint_parsers.PathFileExtensionConstraintParser;
import com.mytdev.cliqui.json.constraint_parsers.IntMaxConstraintParser;
import com.mytdev.cliqui.json.constraint_parsers.PathSelectionModeConstraintParser;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Yann D'Isanto
 */
public final class BuiltInJsonConstraintParserProvider implements JsonConstraintParserProvider {

    private final Map<String, JsonConstraintParser> parsers = new HashMap<>();

    public BuiltInJsonConstraintParserProvider() {
        parsers.put("min", new IntMinConstraintParser());
        parsers.put("max", new IntMaxConstraintParser());
        parsers.put("pathMustExist", new PathExistsConstraintParser());
        parsers.put("pathSelectionMode", new PathSelectionModeConstraintParser());
        parsers.put("fileExtensions", new PathFileExtensionConstraintParser());
    }

    @Override
    public JsonConstraintParser getParser(String constraintName) {
        return parsers.get(constraintName);
    }

}
