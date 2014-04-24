
package com.mytdev.cliqui.beans;

import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
@Getter
public final class IntMinMaxConstraint implements Option.Constraint {
    
    private final int min;
    
    private final int max;

    public IntMinMaxConstraint(int min, int max) {
        if(min > max) {
            throw new IllegalArgumentException("min value must be <= max value");
        }
        this.min = min;
        this.max = max;
    }
}
