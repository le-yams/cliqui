package com.mytdev.cliqui;

import com.mytdev.cliqui.beans.IntMinMaxConstraint;
import com.mytdev.cliqui.beans.Option;
import com.mytdev.cliqui.ui.BooleanOptionUIFactory;
import com.mytdev.cliqui.ui.IntegerOptionUIFactory;
import com.mytdev.cliqui.ui.spi.OptionUI;
import com.mytdev.cliqui.ui.spi.OptionUIFactory;
import com.mytdev.cliqui.ui.PasswordOptionUIFactory;
import com.mytdev.cliqui.ui.PathOptionUIFactory;
import com.mytdev.cliqui.ui.TextOptionUIFactory;
import java.awt.Component;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.GroupLayout;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 *
 * @author Yann D'Isanto
 */
public final class OptionsUI extends JPanel {

    private static final Map<Option.Type, OptionUIFactory> FACTORIES = new EnumMap<>(Option.Type.class);

    static {
        FACTORIES.put(Option.Type.FLAG, new BooleanOptionUIFactory());
        FACTORIES.put(Option.Type.TEXT, new TextOptionUIFactory());
        FACTORIES.put(Option.Type.PATH, new PathOptionUIFactory());
        FACTORIES.put(Option.Type.PASSWORD, new PasswordOptionUIFactory());
        FACTORIES.put(Option.Type.INTEGER, new IntegerOptionUIFactory());
    }

    private final List<Option> options;

    private final Map<Option, OptionUI> optionUIs;

    public OptionsUI(List<Option> options) {
        this.options = new ArrayList<>(options);
        optionUIs = new HashMap<>(options.size());
        for (Option option : options) {
            if (option != null) {
                optionUIs.put(option, buildOptionUI(option));
            }
        }
        initialize();
    }

    public List<String> getCommandLineOptions() {
        final List<String> cliOptions = new ArrayList<>();
        for (Option option : options) {
            cliOptions.addAll(optionUIs.get(option).getCommandLineValue());
        }
        return cliOptions;
    }

    private void initialize() {
        final GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        final GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
        final List<Component> labels = new ArrayList<>();
        GroupLayout.ParallelGroup noLabelFieldsGroup = null;
        for (Option option : options) {
            final OptionUI paramUI = optionUIs.get(option);
            final Component label = paramUI.getLabelComponent();
            final Component field = paramUI.getFieldComponent();
            final Component fieldSuffix = paramUI.getFieldSuffixComponent();
            if (label != null) {
                labels.add(label);
                GroupLayout.SequentialGroup group = layout.createSequentialGroup()
                    .addComponent(label)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
                if (fieldSuffix != null) {
                    group.addComponent(field, GroupLayout.DEFAULT_SIZE, field.getPreferredSize().width, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fieldSuffix);
                } else {
                    group.addComponent(field);
                }
                hGroup.addGroup(group);
            } else {
                if (noLabelFieldsGroup == null) {
                    noLabelFieldsGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
                    hGroup.addGroup(noLabelFieldsGroup);
                }
                noLabelFieldsGroup.addComponent(field);
            }
        }
        layout.setHorizontalGroup(hGroup);

        layout.linkSize(SwingConstants.HORIZONTAL, labels.toArray(new Component[labels.size()]));

        final GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        for (Option option : options) {
            final OptionUI paramUI = optionUIs.get(option);
            final Component label = paramUI.getLabelComponent();
            final Component field = paramUI.getFieldComponent();
            final Component fieldSuffix = paramUI.getFieldSuffixComponent();
            if (label != null) {
                GroupLayout.ParallelGroup group = layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(label)
                    .addComponent(field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE);
                if (fieldSuffix != null) {
                    group.addComponent(fieldSuffix);
                }
                vGroup.addGroup(group);
            } else {
                vGroup.addComponent(field);
            }
            vGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        }

        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(vGroup));

    }

    private OptionUI buildOptionUI(Option option) {
        final OptionUIFactory factory = getFactory(option);
        if (factory == null) {
            throw new IllegalArgumentException("unsupported option type: " + option.getType());
        }
        return factory.createUI(option);
    }

    @SuppressWarnings("unchecked")
    private static OptionUIFactory getFactory(Option option) {
        return FACTORIES.get(option.getType());
    }

    public static final class Builder {

        private final List<Option> options = new ArrayList<>();

        public Builder flagOption(String name) {
            return flagOption(name, null, null);
        }

        public Builder flagOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.FLAG, label, description));
            return this;
        }

        public Builder textOption(String name) {
            return textOption(name, null, null);
        }

        public Builder textOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.TEXT, label, description));
            return this;
        }

        public Builder intOption(String name) {
            return intOption(name, null, null);
        }

        public Builder intOption(String name, int minValue, int maxValue) {
            return addIntOption(name, null, null, new IntMinMaxConstraint(minValue, maxValue));
        }

        public Builder intOption(String name, String label, String description) {
            return addIntOption(name, label, description);
        }

        public Builder intOption(String name, String label, String description, int minValue, int maxValue) {
            return addIntOption(name, label, description, new IntMinMaxConstraint(minValue, maxValue));
        }

        private Builder addIntOption(String name, String label, String description, Option.Constraint... constraints) {
            this.options.add(new Option(name, Option.Type.INTEGER, label, description, constraints));
            return this;
        }

        public Builder pathOption(String name) {
            return passwordOption(name, null, null);
        }

        public Builder pathOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.PATH, label, description));
            return this;
        }

        public Builder passwordOption(String name) {
            return passwordOption(name, null, null);
        }

        public Builder passwordOption(String name, String label, String description) {
            this.options.add(new Option(name, Option.Type.PASSWORD, label, description));
            return this;
        }

        public OptionsUI build() {
            return new OptionsUI(options);
        }

    }
}
