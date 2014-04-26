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

import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
public final class Option extends CommandLineElement {

    @NonNull
    private final Type type;

    private Option(Builder builder) {
        super(builder);
        this.type = builder.type;
    }
    

    public static enum Type {

        FLAG, TEXT, INTEGER, PATH, PASSWORD

    }

    public static final class Builder extends CommandLineElement.Builder<Option, Builder> {
        
        @NonNull
        private final Type type;
        
        public Builder(String name, Type type) {
            super(name);
            this.type = type;
        }

        @Override
        public Option build() {
            return new Option(this);
        }
        
    }
}
