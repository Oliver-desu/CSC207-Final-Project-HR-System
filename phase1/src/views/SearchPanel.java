package views;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 2
public class SearchPanel extends JPanel {

    private JTextField textField = new JTextField("Job title, Keywords, or Company");
    private JButton search = new JButton("Search");
    private JButton apply = new JButton("Apply Now");

    SearchPanel(Dimension dimension) {
        setLayout(new FlowLayout());
        int width = dimension.width;
        int height = dimension.height;
        Dimension buttonSize = new Dimension(width / 3, height / 2);
        Dimension textFieldSize = new Dimension(width - 20, height / 3);

        // add textField
        textField.setPreferredSize(textFieldSize);
        add(textField);

        // add two buttons
        search.setPreferredSize(buttonSize);
        add(search);
        apply.setPreferredSize(buttonSize);
        add(apply);
    }

    HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.put(search.getText(), search);
        buttons.put(apply.getText(), apply);
        return buttons;
    }

    public JTextField getTextField() {
        return textField;
    }
}
