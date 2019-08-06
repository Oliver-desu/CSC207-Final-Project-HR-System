package gui.panels;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Class {@code ComponentFactory} add desired components to InputInfoPanel
 *
 * @author group 0120 of CSC207 summer 2019
 * @see InputInfoPanel
 * @since 2019-08-05
 */
public class ComponentFactory {
    // Enum of all component types
    private enum ComponentType {LABEL, COMBO_BOX, AREA, FIELD}

    // Component height for all created components(except text area)
    private static final int COMPONENT_HEIGHT = 45;

    /**
     * The destination of all created components is added to
     *
     * @see InputInfoPanel
     * @see #addComboBox(String, String[], String, boolean)
     * @see #addTextField(String, String, boolean)
     * @see #addPasswordField(String)
     * @see #addTextArea(String, String, boolean)
     */
    private InputInfoPanel infoPanel;

    /**
     * Stores sizes of all components
     *
     * @see #setComponentSize(int)
     * @see #getComponentSize(ComponentType)
     */
    private HashMap<ComponentType, Dimension> sizes = new HashMap<>();

    /**
     * Constructor for {@code ComponentFactory}
     * @param infoPanel
     * @param width
     * @see InputInfoPanel
     */

    public ComponentFactory(InputInfoPanel infoPanel, int width) {
        this.infoPanel = infoPanel;
        setComponentSize(width);
    }

    /**
     * Set the size for the components with the given width.
     * @param width
     */

    private void setComponentSize(int width) {
        sizes.put(ComponentType.LABEL, new Dimension(width / 3, COMPONENT_HEIGHT));
        sizes.put(ComponentType.COMBO_BOX, new Dimension(width * 3 / 5, COMPONENT_HEIGHT));
        sizes.put(ComponentType.FIELD, new Dimension(width * 3 / 5, COMPONENT_HEIGHT));
        sizes.put(ComponentType.AREA, new Dimension(width * 3 / 4, COMPONENT_HEIGHT * 2));
    }

    /**
     * Return the size of the given component type
     * @param type
     * @return The size of the given component type
     */

    private Dimension getComponentSize(ComponentType type) {
        return sizes.get(type);

    }


    /**
     * Create a text field and specify what the textfield is for.
     * Set the textfield to be editable by user.
     * @param name
     */
    public void addTextField(String name) {
        addTextField(name, "", true);
    }


    /**
     * Create a combo box  and specify what it is for.
     * Set the combo box to be uneditable.
     * @param name
     * @param options
     */
    public void addComboBox(String name, String[] options) {
        addComboBox(name, options, null, false);
    }

    /**
     * Create a text area and specify what the area is for.
     * Make the area editable by user.
     * @param name
     */
    public void addTextArea(String name) {
        addTextArea(name, "", true);
    }


    /**
     * Return a component from the given component type name
     * @param name
     * @return Component from the given component type name.
     */
    public JComponent getLabel(String name) {
        JLabel label = new JLabel(name);
        label.setPreferredSize(getComponentSize(ComponentType.LABEL));
        return label;
    }

    /**
     * Create a combo box with a set of options.
     * Specify what the combo box is for and decide whether it is editable by the boolean it is passed in.
     * @param name
     * @param options
     * @param defaultValue
     * @param editable
     * @see #infoPanel
     */
    public void addComboBox(String name, String[] options, String defaultValue, boolean editable) {
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedItem(defaultValue);
        comboBox.setEditable(editable);
        comboBox.setPreferredSize(getComponentSize(ComponentType.COMBO_BOX));
        infoPanel.addComponent(name, comboBox);
    }

    /**
     * Create a text field.
     * @param name
     * @param defaultValue
     * @param editable
     * @see #infoPanel
     */
    public void addTextField(String name, String defaultValue, boolean editable) {
        JTextField textField = new JTextField();
        textField.setText(defaultValue);
        textField.setEditable(editable);
        textField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        infoPanel.addComponent(name, textField);
    }


    /**
     * Create a field for password.
     * @param name
     * @see #infoPanel
     */
    public void addPasswordField(String name) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(getComponentSize(ComponentType.FIELD));
        infoPanel.addComponent(name, passwordField);
    }

    /**
     * Add a  text area with scroll pane.
     * @param name
     * @param defaultValue
     * @param editable
     * @see #infoPanel
     */

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
