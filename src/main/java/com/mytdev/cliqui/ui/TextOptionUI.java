package com.mytdev.cliqui.ui;

import com.mytdev.cliqui.ui.spi.AbstractOptionUI;
import com.mytdev.cliqui.beans.Option;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Yann D'Isanto
 */
public final class TextOptionUI extends AbstractOptionUI {

    private final JLabel label = new JLabel();

    private final JTextField field = new JTextField();

    public TextOptionUI(Option option) {
        this(option, null);
    }

    public TextOptionUI(Option option, DocumentFilter documentFilter) {
        super(option);
        if (documentFilter != null) {
            final PlainDocument document = (PlainDocument) field.getDocument();
            document.setDocumentFilter(documentFilter);
        }
        label.setText(option.getLabel());
        label.setToolTipText(option.getDescription());
        field.setToolTipText(option.getDescription());
    }

    @Override
    public List<String> getCommandLineValue() {
        final List<String> cli = new ArrayList<>();
        final String value = field.getText();
        if(value.isEmpty() == false) {
            cli.add(getOption().getName());
            cli.add(value);
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
    }

    @Override
    public JComponent getFieldSuffixComponent() {
        return null;
    }

}
