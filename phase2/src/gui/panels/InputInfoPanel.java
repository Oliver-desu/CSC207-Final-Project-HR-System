package gui.panels;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

public class InputInfoPanel extends JScrollPane {

    private Container box;
    private ComponentFactory componentFactory;
    private JPasswordField[] passwordFields = new JPasswordField[2];
    private HashMap<String, JComponent> componentMap = new HashMap<>();

    public InputInfoPanel(Dimension dimension) {
        setup(dimension, false);
        factorySetup(dimension, false);
    }

    public InputInfoPanel(Dimension dimension, boolean vertical) {
        setup(dimension, vertical);
        factorySetup(dimension, vertical);
    }

    public void setup(Dimension dimension, boolean vertical) {
        setPreferredSize(dimension);
        if (vertical) {
            box = Box.createVerticalBox();
        } else {
            box = new JPanel();
            box.setPreferredSize(dimension);
        }
        setViewportView(box);
    }

    private void factorySetup(Dimension dimension, boolean vertical) {
        if (vertical) {
            componentFactory = new ComponentFactory(this, dimension.width * 3 / 4);
        } else {
            componentFactory = new ComponentFactory(this, dimension.width / 2);
        }
    }

    public ComponentFactory getComponentFactory() {
        return componentFactory;
    }

    void addComponent(String name, JComponent component) {
        JPanel panel = new JPanel();
        panel.add(componentFactory.getLabel(name));
        panel.add(component);
        box.add(panel);
        if (component instanceof JPasswordField) {
            if (passwordFields[0] == null) passwordFields[0] = (JPasswordField) component;
            else if (passwordFields[1] == null) passwordFields[1] = (JPasswordField) component;
        } else {
            componentMap.put(name, component);
        }
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
