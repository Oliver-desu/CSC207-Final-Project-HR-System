package views.components.boxes;

import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class InterviewerBoxPanel extends JPanel implements ButtonHolder, ComboBoxHolder {
    private static final String[] DECISIONS = new String[]{"PASS", "REJECT"};
    private JComboBox<String> decisionBox = new JComboBox<>(DECISIONS);
    private JButton recommendation = new JButton("Give Recommendation");

    public InterviewerBoxPanel(Dimension dimension) {
        setup(dimension, dimension.height * 3 / 4);
    }

    private void setup(Dimension dimension, int height) {
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
}
