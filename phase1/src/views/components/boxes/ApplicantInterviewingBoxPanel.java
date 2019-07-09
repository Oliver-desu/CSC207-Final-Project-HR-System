package views.components.boxes;

import views.components.ComboBoxPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ApplicantInterviewingBoxPanel extends ComboBoxPanel {
    private static final String[] INTERVIEW_TYPES = new String[]{"UPCOMING", "PENDING", "PAST"};
    private JComboBox<String> typeBox = new JComboBox<>();

    ApplicantInterviewingBoxPanel(Dimension dimension) {
        super(dimension);
    }

    protected void setup() {
        setLayout(new FlowLayout());
        setPreferredSize(dimension);
        Dimension size = new Dimension(dimension.width / 4, height);
        typeBox.setPreferredSize(size);
        add(typeBox);
    }

    @Override
    public HashMap<String, JComboBox<String>> getBoxes() {
        HashMap<String, JComboBox<String>> boxes = new HashMap<>();
        boxes.put("Type Box", typeBox);
        return boxes;
    }

    @Override
    public void update() {

    }
}