package views.components.boxes;

import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;
import views.interfaces.TextFieldHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ApplicantIncompleteBoxPanel extends JPanel implements ButtonHolder, ComboBoxHolder, TextFieldHolder {

    private static final String[] BUTTON_TYPES = new String[]{"Attach", "Remove", "Update", "Add"};
    private static final String BOX_NAME = "My Documents";
    private static final String FIELD_NAME = "Document Name";

    private HashMap<String, JButton> buttons = new HashMap<>();
    private JComboBox<String> documents = new JComboBox<>();
    private JTextField input = new JTextField();

    public ApplicantIncompleteBoxPanel(Dimension dimension) {
        setup(dimension);
    }

    private void setup(Dimension dimension) {
        // basic settings
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(dimension);

        // sizes
        Dimension buttonPanelSize = new Dimension(dimension.width / 2, dimension.height);
        Dimension buttonSize = new Dimension(dimension.width / 5, dimension.height / 3);
        Dimension fieldSize = new Dimension(dimension.width / 2, dimension.height / 3);

        // buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(buttonPanelSize);
        for (String buttonType : BUTTON_TYPES) {
            JButton button = new JButton(buttonType);
            button.setPreferredSize(buttonSize);
            buttonPanel.add(button);
        }

        // add components
        add(buttonPanel);
        documents.setPreferredSize(fieldSize);
        input.setPreferredSize(fieldSize);
        add(documents);
        add(input);
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.put(BOX_NAME, documents);
        return boxes;
    }

    @Override
    public HashMap<String, JTextField> getTextFields() {
        HashMap<String, JTextField> textFields = new HashMap<>();
        textFields.put(FIELD_NAME, input);
        return textFields;
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return buttons;
    }
}
