package views.components.boxes;

import views.ViewFrame;
import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PostingBoxPanel extends JPanel implements ButtonHolder, TextFieldHolder, ComboBoxHolder {
    private static final Dimension SIZE = ViewFrame.MAIN_PANEL_SIZE;
    private static final String[] BOX_TYPES = new String[]{"Cover Letter & CV"};
    private static final String[] INPUTS = new String[]{"Job Position Name", "No. Positions", "Close Date"};
    private static final String BOX_NAME = "Requirements";
    private static final String BUTTON_NAME = "Create Posting";

    private HashMap<String, JTextField> textFields = new HashMap<>();
    private JComboBox<String> type = new JComboBox<>(BOX_TYPES);
    private JButton createPosting = new JButton(BUTTON_NAME);

    public PostingBoxPanel() {
        setup();
    }

    private void setup() {
        setLayout(new FlowLayout());
        setPreferredSize(SIZE);

        // add fields
        int height = SIZE.height / 6;
        Dimension labelSize = new Dimension(SIZE.width / 3, height);
        Dimension fieldSize = new Dimension(SIZE.width * 2 / 3 - 20, height);
        for (String input : INPUTS) {
            createInputField(input, labelSize, fieldSize);
        }
        textFields.get("Close Date").setText("YY/MM/DD");

        // add combo box
        createLabel(BOX_NAME, labelSize);
        type.setPreferredSize(fieldSize);
        add(type);

        // add create posting button
        createPosting.setPreferredSize(labelSize);
        add(createPosting);
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
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.put(BUTTON_NAME, createPosting);
        return buttons;
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
}
