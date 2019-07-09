package views.components.boxes;

import views.components.ComboBoxPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InterviewerBoxPanel extends ComboBoxPanel {
    private static final String[] DECISIONS = new String[]{"PASS", "REJECT"};
    private JComboBox<String> decisionBox = new JComboBox<>(DECISIONS);
    private JButton recommendation = new JButton("Give Recommendation");

    InterviewerBoxPanel(Dimension dimension) {
        super(dimension);
    }

    protected void setup() {
        // size
        Dimension size = new Dimension(dimension.width / 4, height);
        decisionBox.setPreferredSize(size);
        recommendation.setPreferredSize(size);

        // panel settings
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(dimension);
        add(recommendation);
        add(decisionBox);
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.put(recommendation.getText(), recommendation);
        return buttons;
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.put("Decision Box", decisionBox);
        return boxes;
    }

    @Override
    public void update() {

    }
}
