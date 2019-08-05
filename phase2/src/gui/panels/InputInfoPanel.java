package gui.panels;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class {@code InputInfoPanel} setup gui panel to show output text to users
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.Scenario
 * @see gui.major.Login
 * @see ComponentFactory
 * @since 2019-08-05
 */
public class InputInfoPanel extends JScrollPane {

    /**
     * The container of showed components
     *
     * @see #addComponent(String, JComponent)
     * @see #setup(Dimension, boolean)
     */
    private Container container;

    /**
     * The ComponentFactory that responsible for adding components
     *
     * @see ComponentFactory
     * @see #addComponent(String, JComponent)
     * @see #getComponentFactory()
     * @see #factorySetup(Dimension, boolean)
     */
    private ComponentFactory componentFactory;

    /**
     * The password fields that handles user entered password
     *
     * @see #addComponent(String, JComponent)
     * @see #passwordMatched()
     * @see #getPassword()
     */
    private JPasswordField[] passwordFields = new JPasswordField[2];

    /**
     * The collection of components that handles user input
     *
     * @see #addComponent(String, JComponent)
     * @see #clear()
     * @see #getInfoMap()
     */
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
            container = Box.createVerticalBox();
        } else {
            container = new JPanel();
            container.setPreferredSize(new Dimension(dimension.width - 20, dimension.height - 20));
        }
        setViewportView(container);
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
        container.add(panel);
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
