package gui.panels;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class InputInfoPanel extends JPanel {

    private Container box;
    private ComponentFactory componentFactory;
    private JPasswordField[] passwordFields = new JPasswordField[2];
    private HashMap<String, JComponent> componentMap = new HashMap<>();

    public void setup(Dimension dimension, boolean vertical) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(dimension);
        if (vertical) {
            box = Box.createVerticalBox();
            componentFactory = new ComponentFactory(dimension.width * 3 / 4);
        } else {
            box = new JPanel(new FlowLayout());
            box.setPreferredSize(new Dimension(dimension.width - 20, dimension.height - 20));
            componentFactory = new ComponentFactory(dimension.width / 2);
        }
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(dimension);
        add(scrollPane);
    }

    private void createTool(String name, JComponent component) {
        JPanel panel = new JPanel();
        panel.add(componentFactory.getLabel(name));
        panel.add(component);
        box.add(panel);
        if (!(component instanceof JPasswordField)) {
            componentMap.put(name, component);
        }
    }

    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
        createTool(name, componentFactory.getComboBox(options, defaultValue, editable));
    }

    public void addTextField(String name, String defaultValue, boolean editable) {
        createTool(name, componentFactory.getTextField(defaultValue, editable));
    }

    public void addComboBox(String name, String[] options) {
        addComboBox(name, options, null, false);
    }

    public void addPasswordField(String name) {
        JPasswordField passwordField = componentFactory.getPasswordField();
        if (passwordFields[0] == null) passwordFields[0] = passwordField;
        else if (passwordFields[1] == null) passwordFields[1] = passwordField;
        else return;
        createTool(name, passwordField);
    }

    public void addTextField(String name) {
        addTextField(name, "", true);
    }

    public void addTextArea(String name, String defaultValue, boolean editable) {
        createTool(name, componentFactory.getTextArea(defaultValue, editable));
    }

    public void addTextArea(String name) {
        addTextArea(name, "", true);
    }

    private String getText(JComponent component) {
        if (component instanceof JTextComponent) {
            return ((JTextField) component).getText();
        } else if (component instanceof JComboBox) {
            Object object = ((JComboBox) component).getSelectedItem();
            if (object instanceof String) return (String) object;
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

    public char[] getPassword() {
        if (passwordMatched()) return passwordFields[0].getPassword();
        else return new char[0];
    }

    public void clear() {
        for (JComponent component : componentMap.values()) {
            if (component instanceof JTextComponent) {
                ((JTextComponent) component).setText("");
            }
        }
    }

    public HashMap<String, String> getInfoMap() {
        HashMap<String, String> infoMap = new HashMap<>();
        for (String componentName : componentMap.keySet()) {
            JComponent component = componentMap.get(componentName);
            infoMap.put(componentName, getText(component));
        }
        return infoMap;
    }
}
