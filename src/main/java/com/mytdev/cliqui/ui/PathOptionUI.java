package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.AbstractOptionUI;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.beans.PathSelectionMode;
import com.mytdev.cliqui.beans.PathSelectionModeConstraint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Yann D'Isanto
 */
public final class PathOptionUI extends AbstractOptionUI implements ActionListener {

    private static final Map<PathSelectionMode, Integer> SELECTION_MODES = new EnumMap<>(PathSelectionMode.class);

    static {
        SELECTION_MODES.put(PathSelectionMode.FILES_ONLY, JFileChooser.FILES_ONLY);
        SELECTION_MODES.put(PathSelectionMode.FILES_AND_DIRECTORIES, JFileChooser.FILES_AND_DIRECTORIES);
        SELECTION_MODES.put(PathSelectionMode.DIRECTORIES_ONLY, JFileChooser.DIRECTORIES_ONLY);
    }

    private final JLabel label = new JLabel();

    private final JTextField field = new JTextField();

    private final JButton browseButton = new JButton("Browse...");

    private final JFileChooser fileChooser = new JFileChooser();

    public PathOptionUI(Option option) {
        super(option);
        label.setText(option.getLabel());
        label.setToolTipText(option.getDescription());
        field.setToolTipText(option.getDescription());
        browseButton.addActionListener(this);
        fileChooser.setMultiSelectionEnabled(false);
        final PathSelectionModeConstraint selectionModeConstraint = option.getConstraint(PathSelectionModeConstraint.class);
        fileChooser.setFileSelectionMode(selectionModeConstraint != null
            ? SELECTION_MODES.get(selectionModeConstraint.getMode())
            : JFileChooser.FILES_AND_DIRECTORIES);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fileChooser.setSelectedFile(Paths.get(field.getText()).toFile());
        if (fileChooser.showDialog(browseButton, "Select") == JFileChooser.APPROVE_OPTION) {
            field.setText(fileChooser.getSelectedFile().toPath().toString());
        }
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final String path = field.getText();
        if (path.isEmpty() == false) {
            cli.add(getOption().getName());
            cli.add(path);
        }
        return cli;
    }

    @Override
    public JComponent getLabelComponent() {
        return label;
    }

    @Override
    public JComponent getFieldComponent() {
        return field;
//        return fieldPanel;
    }

    @Override
    public JComponent getFieldSuffixComponent() {
        return browseButton;
    }
}
