
package com.mytdev.cliqui.beans;

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

    CommandLineElement(String name, String label, String description) {
        this.name = name;
        this.label = label != null ? label : computeLabelFromName(name);
        this.description = description;
    }
    
    private String computeLabelFromName(String name) {
        return name.replaceFirst("^(/|(--?))", "");
    }
    
}
