package views.panels;

import views.interfaces.ButtonHolder;
import views.interfaces.TextFieldHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 2
public class SearchPanel extends JPanel implements ButtonHolder, TextFieldHolder, ViewComponent {

    private JTextField keyword = new JTextField("Job title, Keywords, or Company");
    private JButton search = new JButton("Search");
    private JButton apply = new JButton("Apply Now");

    SearchPanel(Dimension dimension) {
        setLayout(new FlowLayout());
        int width = dimension.width;
        int height = dimension.height;
        Dimension buttonSize = new Dimension(width / 3, height / 2);
        Dimension textFieldSize = new Dimension(width - 20, height / 3);

        // add keyword
        keyword.setPreferredSize(textFieldSize);
        add(keyword);

        // add two buttons
        search.setPreferredSize(buttonSize);
        add(search);
        apply.setPreferredSize(buttonSize);
        add(apply);
    }

    public HashMap<String, JButton> getButtons() {
        HashMap<String, JButton> buttons = new HashMap<>();
        buttons.put(search.getText(), search);
        buttons.put(apply.getText(), apply);
        return buttons;
    }

    public HashMap<String, JTextField> getTextFields() {
        HashMap<String, JTextField> textFields = new HashMap<>();
        textFields.put("Keyword", keyword);
        return textFields;
    }

    @Override
    public void update() {
        keyword.setText("");
    }
}
