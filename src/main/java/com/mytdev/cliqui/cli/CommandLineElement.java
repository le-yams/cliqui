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
package com.mytdev.cliqui.cli;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private final Map<Class<?>, Constraint> constraints = new HashMap<>();

    CommandLineElement(Builder builder) {
        this.name = builder.name;
        this.label = builder.label != null ? builder.label : computeLabelFromName(builder.name);
        this.description = builder.description;
        for (Constraint constraint : (Set<Constraint>) builder.constraints) {
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

    public static abstract class Builder<T extends CommandLineElement, B extends Builder<T, B>> {

        @NonNull
        private final String name;

        private String label;

        private String description;

        private final Set<Constraint> constraints = new HashSet<>();

        Builder(String name) {
            this.name = name;
        }

        /**
         * Sets the label to display in UI. If {@code null } the name is used
         * (without "-", "--" or "/" prefix).
         *
         * @param label the label
         * @return this builder instance
         */
        public B label(String label) {
            this.label = label;
            return (B) this;
        }

        /**
         * Sets the description to display in UI ({@code null } means no
         * description)
         *
         * @param description the description
         * @return this builder instance
         */
        public B description(String description) {
            this.description = description;
            return (B) this;
        }

        /**
         * Adds the given constraints
         * @param constraints the constraints to add
         * @return this builder instance
         */
        public B constraints(Collection<Constraint> constraints) {
            this.constraints.addAll(constraints);
            return (B) this;
        }

        /**
         * Adds the given constraints
         * @param constraints the constraints to add
         * @return this builder instance
         */
        public B constraints(Constraint... constraints) {
            return constraints(Arrays.asList(constraints));
        }

        public abstract T build();
    }
}
