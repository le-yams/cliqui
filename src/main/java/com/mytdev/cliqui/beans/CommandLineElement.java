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
package com.mytdev.cliqui.beans;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
public abstract class CommandLineElement {

    @NonNull
    private final String name;

    @NonNull
    private final String label;

    @NonNull
    private final String description;

    private final Map<Class<?>, Object> constraints = new HashMap<>();

    CommandLineElement(String name, String label, String description, Object... constraints) {
        this.name = name;
        this.label = label != null ? label : computeLabelFromName(name);
        this.description = description;
        for (Object constraint : constraints) {
            this.constraints.put(constraint.getClass(), constraint);
        }
    }

    @SuppressWarnings("unchecked")
    public <C> C getConstraint(Class<C> contraint) {
        return (C) constraints.get(contraint);
    }

    private String computeLabelFromName(String name) {
        return name.replaceFirst("^(/|(--?))", "");
    }

}
