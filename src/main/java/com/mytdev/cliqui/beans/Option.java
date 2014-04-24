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
public final class Option extends CommandLineElement {

    @NonNull
    private final Type type;
    
    private final Map<Class<? extends Constraint>, Constraint> constraints = new HashMap<>();
    
    public Option(String name, Type type, String label, String description, Constraint... constraints) {
        super(name, label, description);
        this.type = type;
        for (Constraint constraint : constraints) {
            this.constraints.put(constraint.getClass(), constraint);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <C extends Constraint> C getConstraint(Class<C> contraint) {
        return (C) constraints.get(contraint);
    }
    
    public static enum Type {
        FLAG, TEXT, INTEGER, PATH, PASSWORD
    }
    
    public static interface Constraint {
        
    }
}
