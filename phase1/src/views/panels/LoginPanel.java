package views.panels;

import views.components.ButtonPanel;
import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 1
public class LoginPanel extends JPanel implements ButtonHolder, TextFieldHolder, ComboBoxHolder, ViewComponent {

    private static final String[] INPUTS = new String[]{"Username", "Company Name", "Password"};
    private static final String[] BOX_TYPES = new String[]{"Company", "Applicant"};
    private static final String BOX_NAME = "Type";

    private HashMap<String, JTextField> textFields = new HashMap<>();
    private JComboBox<String> type = new JComboBox<>(BOX_TYPES);
    private ButtonPanel buttons;

    public LoginPanel(Dimension dimension) {
        setup(dimension);
    }

    private void setup(Dimension dimension) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(dimension);

        // add fields
        int height = dimension.height / 4;
        Dimension buttonPanelSize = new Dimension(dimension.width - 20, dimension.height / 3);
        Dimension labelSize = new Dimension(dimension.width / 7, height);
        Dimension fieldSize = new Dimension(dimension.width / 3, height);
        for (String input : INPUTS) {
            createInputField(input, labelSize, fieldSize);
        }

        // add combo box
        createLabel(BOX_NAME, labelSize);
        type.setPreferredSize(fieldSize);
        add(type);

        // add some buttons
        buttons = new ButtonPanel(buttonPanelSize);
        buttons.setView(ButtonPanel.View.LOGIN);
        buttons.update();
        add(buttons);
    }

    private void createLabel(String input, Dimension labelSize) {
        JLabel label = new JLabel(input);
        label.setPreferredSize(labelSize);
        add(label);
    }

    private void createInputField(String input, Dimension labelSize, Dimension fieldSize) {
        // create a label
        createLabel(input, labelSize);

        // create a field
        JTextField field = new JTextField();
        field.setPreferredSize(fieldSize);
        add(field);

        textFields.put(input, field);
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return buttons.getButtons();
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        return textFields;
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.put(BOX_NAME, type);
        return boxes;
    }

    @Override
    public void update() {
        for (JTextField textField : textFields.values()) {
            textField.setText("");
        }
    }
}
