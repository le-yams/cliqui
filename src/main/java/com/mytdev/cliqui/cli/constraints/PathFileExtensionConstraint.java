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
package com.mytdev.cliqui.cli.constraints;

import com.mytdev.cliqui.cli.Constraint;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
public final class PathFileExtensionConstraint implements Constraint {

    private final String description;

    private final Collection<String> extensions;

    private final boolean strict;

    public PathFileExtensionConstraint(String description, boolean strict, String... extensions) {
        this(description, strict, Arrays.asList(extensions));
    }
    
    public PathFileExtensionConstraint(String description, boolean strict, Collection<String> extensions) {
        this.description = description != null ? description : computeDescriptionFromExtensions(extensions);
        this.extensions = Collections.unmodifiableCollection(extensions);
        this.strict = strict;
    }

    private static String computeDescriptionFromExtensions(Collection<String> extensions) {
        final StringBuilder sb = new StringBuilder();
        for (String extension : extensions) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append("*.").append(extension);
        }
        return sb.toString();
    }

}
