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

package com.mytdev.cliqui.swing;

import com.mytdev.cliqui.spi.ChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Yann D'Isanto
 */
public final class SwingChangeSupport implements ChangeSupport<ChangeListener> {

    private final Object source;
    
    private final List<ChangeListener> listeners = new ArrayList<>();

    public SwingChangeSupport(Object source) {
        this.source = source;
    }
    
    @Override
    public void addListener(ChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public void fireChange() {
        final ChangeEvent event = new ChangeEvent(source);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);
        }
    }

    @Override
    public void removeListener(ChangeListener listener) {
        listeners.add(listener);
    }
    
}
