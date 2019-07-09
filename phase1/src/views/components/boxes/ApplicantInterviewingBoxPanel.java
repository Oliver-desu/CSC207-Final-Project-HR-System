package views.components.boxes;

import views.interfaces.ComboBoxHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ApplicantInterviewingBoxPanel extends JPanel implements ComboBoxHolder {
    private static final String[] INTERVIEW_TYPES = new String[]{"UPCOMING", "PENDING", "PAST"};
    private JComboBox<String> typeBox = new JComboBox<>(INTERVIEW_TYPES);

    public ApplicantInterviewingBoxPanel(Dimension dimension) {
        setup(dimension, dimension.height * 3 / 4);
    }

    private void setup(Dimension dimension, int height) {
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
}