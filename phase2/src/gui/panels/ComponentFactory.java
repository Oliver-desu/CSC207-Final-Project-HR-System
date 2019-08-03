package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ComponentFactory {
    private enum ComponentType {LABEL, COMBO_BOX, AREA, FIELD}

    private static final int COMPONENT_HEIGHT = 45;

    private InputInfoPanel infoPanel;
    private HashMap<ComponentType, Dimension> sizes = new HashMap<>();

    public ComponentFactory(InputInfoPanel infoPanel, int width) {
        this.infoPanel = infoPanel;
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

    public void addTextField(String name) {
        addTextField(name, "", true);
    }

    public void addComboBox(String name, String[] options) {
        addComboBox(name, options, null, false);
    }

    public void addTextArea(String name) {
        addTextArea(name, "", true);
    }

    public JComponent getLabel(String name) {
        JLabel label = new JLabel(name);
        label.setPreferredSize(getComponentSize(ComponentType.LABEL));
        return label;
    }

    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setEditable(editable);
        comboBox.setPreferredSize(getComponentSize(ComponentType.COMBO_BOX));
        infoPanel.addComponent(name, comboBox);
    }

    public void addTextField(String name, String defaultValue, boolean editable) {
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEditable(editable);
        textField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        infoPanel.addComponent(name, textField);
    }

    public void addPasswordField(String name) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        infoPanel.addComponent(name, passwordField);
    }

    public void addTextArea(String name, String defaultValue, boolean editable) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(OutputInfoPanel.FONT);
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        textArea.setText(defaultValue);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(getComponentSize(ComponentType.AREA));
        infoPanel.addComponent(name, scrollPane);
    }
}
