package com.mytdev.cliqui.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
@AllArgsConstructor
public final class PathSelectionModeConstraint implements Option.Constraint {

    private final PathSelectionMode mode;

}
