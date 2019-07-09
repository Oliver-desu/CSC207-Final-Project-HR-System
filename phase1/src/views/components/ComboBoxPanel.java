package views.components;

import views.interfaces.ButtonHolder;
import views.interfaces.ComboBoxHolder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 3(4)
public class ComboBoxPanel extends JPanel {

    class HRBoxPanel extends JPanel implements ComboBoxHolder, ButtonHolder {
        private JComboBox<String> interviewerBox = new JComboBox<>();
        private JButton match = new JButton("Match Interview");
        private JButton back = new JButton("Back");

        HRBoxPanel(Dimension dimension, int height) {
            setup(dimension, height);
        }

        void setup(Dimension dimension, int height) {
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

    class InterviewerBoxPanel extends JPanel implements ComboBoxHolder, ButtonHolder {
        private final String[] DECISIONS = new String[]{"PASS", "REJECT"};
        private JComboBox<String> decisionBox = new JComboBox<>(DECISIONS);
        private JButton recommendation = new JButton("Give Recommendation");

        InterviewerBoxPanel(Dimension dimension, int height) {
            setup(dimension, height);
        }

        void setup(Dimension dimension, int height) {
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

    class ApplicantIncompleteBoxPanel extends JPanel {

    }

    class ApplicantInterviewingBoxPanel extends JPanel {

    }
}
