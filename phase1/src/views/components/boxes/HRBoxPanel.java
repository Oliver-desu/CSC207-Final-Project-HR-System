package views.components.boxes;

import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class HRBoxPanel extends JPanel implements ButtonHolder, ComboBoxHolder {
    private JComboBox<String> interviewerBox = new JComboBox<>();
    private JButton match = new JButton("Match Interview");
    private JButton back = new JButton("Back");

    public HRBoxPanel(Dimension dimension) {
        setup(dimension, dimension.height * 3 / 4);
    }

    private void setup(Dimension dimension, int height) {
        // sizes
        Dimension boxSize = new Dimension(dimension.width / 2, height);
        Dimension buttonSize = new Dimension(dimension.width / 5, height);
        interviewerBox.setPreferredSize(boxSize);
        match.setPreferredSize(buttonSize);
        back.setPreferredSize(buttonSize);

        // panel settings
        setLayout(new FlowLayout());
        setPreferredSize(dimension);
        add(interviewerBox);
        add(match);
        add(back);
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.put(match.getText(), match);
        buttons.put(back.getText(), back);
        return buttons;
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.put("Interviewer Box", interviewerBox);
        return boxes;
    }
}