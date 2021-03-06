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
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
@AllArgsConstructor
public final class IntMaxConstraint implements Constraint {

    private final int max;
    
}
