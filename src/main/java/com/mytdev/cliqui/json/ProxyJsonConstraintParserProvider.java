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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 *
 * @author Yann D'Isanto
 */
public final class ProxyJsonConstraintParserProvider implements JsonConstraintParserProvider {

    private final Map<String, JsonConstraintParser> cache = new HashMap<>();

    private final JsonConstraintParserProvider builtInProvider = new BuiltInJsonConstraintParserProvider();

    private final ServiceLoader<JsonConstraintParserProvider> loader = ServiceLoader.load(JsonConstraintParserProvider.class);

    @Override
    public JsonConstraintParser getParser(String constraintName) {
        final JsonConstraintParser parser = cache.get(constraintName);
        return parser != null ? parser : loadAndCacheParser(constraintName);
    }

    private JsonConstraintParser loadAndCacheParser(String constraintName) {
        final JsonConstraintParser builtInParser = builtInProvider.getParser(constraintName);
        if (builtInParser != null) {
            cache.put(constraintName, builtInParser);
            return builtInParser;
        }
        for (JsonConstraintParserProvider provider : loader) {
            final JsonConstraintParser parser = provider.getParser(constraintName);
            if (parser != null) {
                cache.put(constraintName, parser);
                return parser;
            }
        }
        return null;
    }
}
