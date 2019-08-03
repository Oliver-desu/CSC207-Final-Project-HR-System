package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ComponentFactory {
    private enum ComponentType {LABEL, COMBO_BOX, AREA, FIELD}

    private static final int COMPONENT_HEIGHT = 45;

    private HashMap<ComponentType, Dimension> sizes = new HashMap<>();

    public ComponentFactory(int width) {
        setComponentComponentTypes(width);
    }

    private void setComponentComponentTypes(int width) {
        sizes.put(ComponentType.LABEL, new Dimension(width / 3, COMPONENT_HEIGHT));
        sizes.put(ComponentType.COMBO_BOX, new Dimension(width * 3 / 5, COMPONENT_HEIGHT));
        sizes.put(ComponentType.FIELD, new Dimension(width * 3 / 5, COMPONENT_HEIGHT));
        sizes.put(ComponentType.AREA, new Dimension(width * 3 / 4, COMPONENT_HEIGHT * 2));
    }

    private Dimension getComponentSize(ComponentType type) {
        return sizes.get(type);

    }

    public JComponent getLabel(String name) {
        JLabel label = new JLabel(name);
        label.setPreferredSize(getComponentSize(ComponentType.LABEL));
        return label;
    }

    public JComponent getComboBox(String[] options, String defaultValue, boolean editable) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setEditable(editable);
        comboBox.setPreferredSize(getComponentSize(ComponentType.COMBO_BOX));
        return comboBox;
    }

    public JComponent getTextField(String defaultValue, boolean editable) {
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEditable(editable);
        textField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        return textField;
    }

    public JPasswordField getPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        return passwordField;
    }

    public JComponent getTextArea(String defaultValue, boolean editable) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(OutputInfoPanel.FONT);
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        textArea.setText(defaultValue);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(getComponentSize(ComponentType.AREA));
        return scrollPane;
    }
}
