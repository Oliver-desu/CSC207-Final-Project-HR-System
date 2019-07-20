package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class InputInfoPanel extends JPanel {

    private static final int HEIGHT = 45;
    private HashMap<Size, Dimension> sizes = new HashMap<>();

    private Container box;
    private JPasswordField[] passwordFields = new JPasswordField[2];

    private Dimension getLabelSize() {
        return sizes.get(Size.LABEL);
    }
    private HashMap<String, JComponent> componentMap = new HashMap<>();

    public void setup(Dimension dimension, boolean vertical) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(dimension);
        if (vertical) {
            box = Box.createVerticalBox();
            setComponentSizes(dimension.width);
        } else {
            box = new JPanel(new FlowLayout());
            box.setPreferredSize(new Dimension(dimension.width - 20, dimension.height - 20));
            setComponentSizes(dimension.width / 2);
        }
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);
    }

    private Dimension getAreaSize() {
        return sizes.get(Size.AREA);
    }

    private Dimension getComboBoxSize() {
        return sizes.get(Size.COMBO_BOX);
    }

    private Dimension getFieldSize() {
        return sizes.get(Size.FIELD);
    }

    private void setComponentSizes(int width) {
        sizes.put(Size.LABEL, new Dimension(width / 5, HEIGHT));
        sizes.put(Size.COMBO_BOX, new Dimension(width / 2, HEIGHT));
        sizes.put(Size.FIELD, new Dimension(width / 2, HEIGHT));
        sizes.put(Size.AREA, new Dimension(width * 3 / 4, HEIGHT * 2));
    }

    private void createTool(String name, JComponent component) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(name);
        label.setPreferredSize(getLabelSize());
        panel.add(label);
        if (component instanceof JTextArea) {
            JScrollPane scrollPane = new JScrollPane(component);
            scrollPane.setPreferredSize(getAreaSize());
            panel.add(scrollPane);
        } else panel.add(component);
        box.add(panel);
        if (component instanceof JPasswordField) return;
        componentMap.put(name, component);
    }

    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setEditable(editable);
        comboBox.setPreferredSize(getComboBoxSize());
        createTool(name, comboBox);
    }

    public void addTextField(String name, String defaultValue, boolean editable) {
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEditable(editable);
        textField.setPreferredSize(getFieldSize());
        createTool(name, textField);
    }

    public void addComboBox(String name, String[] options) {
        addComboBox(name, options, null, false);
    }

    public void addPasswordField(String name) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(getFieldSize());
        if (passwordFields[0] == null) passwordFields[0] = passwordField;
        else if (passwordFields[1] == null) passwordFields[1] = passwordField;
        else return;
        createTool(name, passwordField);
    }

    public void addTextField(String name) {
        addTextField(name, "", true);
    }

    public void addTextArea(String name, String defaultValue, boolean editable) {
        JTextArea textArea = new JTextArea();
        textArea.setFont(OutputInfoPanel.FONT);
        textArea.setLineWrap(true);
        textArea.setEditable(editable);
        textArea.setText(defaultValue);
        createTool(name, textArea);
    }

    public void addTextArea(String name) {
        addTextArea(name, "", true);
    }

    private String getText(JComponent component) {
        if (component instanceof JTextField) {
            return ((JTextField) component).getText();
        } else if (component instanceof JComboBox) {
            Object object = ((JComboBox) component).getSelectedItem();
            if (object instanceof String) return (String) object;
        } else if (component instanceof JTextArea) {
            return ((JTextArea) component).getText();
        }
        return "";
    }

    private boolean passwordMatched() {
        JPasswordField passwordField = passwordFields[0];
        JPasswordField confirmPasswordField = passwordFields[1];
        if (passwordField == null) {
            return false;
        } else if (confirmPasswordField == null) {
            return true;
        } else {
            return Arrays.equals(passwordField.getPassword(), confirmPasswordField.getPassword());
        }
    }

    public String getPassword() {
        if (passwordMatched()) return Arrays.toString(passwordFields[0].getPassword());
        else return null;
    }

    private enum Size {LABEL, COMBO_BOX, AREA, FIELD}

    public HashMap<String, String> getInfoMap() {
        HashMap<String, String> infoMap = new HashMap<>();
        for (String componentName : componentMap.keySet()) {
            JComponent component = componentMap.get(componentName);
            infoMap.put(componentName, getText(component));
        }
        return infoMap;
    }
}
