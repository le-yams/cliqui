package com.mytdev.cliqui.beans;

import javax.swing.JFileChooser;
import lombok.Getter;

/**
 *
 * @author Yann D'Isanto
 */
public enum PathSelectionMode {

    FILES_ONLY(JFileChooser.FILES_ONLY),
    DIRECTORIES_ONLY(JFileChooser.DIRECTORIES_ONLY),
    FILES_AND_DIRECTORIES(JFileChooser.FILES_AND_DIRECTORIES);

    @Getter
    private final int value;

    private PathSelectionMode(int value) {
        this.value = value;
    }
}
