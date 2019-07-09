package views.components;

import views.interfaces.ButtonHolder;
import views.interfaces.ViewComponent;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

// 5(2)
public class ButtonPanel extends JPanel implements ButtonHolder, ViewComponent {
    private static final int MAX_BUTTON_NUM = 4;
    private static final String[] HR_BUTTONS = new String[]{
            "Post Job", "View Applicants", "Hire", "Reject"
    };
    private static final String[] APPLICANT_BUTTONS = new String[]{"Drop Application"};
    private HashMap<String, JButton> buttons;

    private Dimension dimension;
    private Type type;

    ButtonPanel(Dimension dimension, Type type) {
        this.dimension = dimension;
        this.type = type;
        update();
    }

    private void setup(Dimension dimension, String[] buttonNames) {
        // panel settings
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setPreferredSize(dimension);

        // add buttons
        Dimension buttonSize = new Dimension(dimension.width / (MAX_BUTTON_NUM + 1), dimension.height * 3 / 4);
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setPreferredSize(buttonSize);
            buttons.put(buttonName, button);
            add(button);
        }
    }

    @Override
    public HashMap<String, JButton> getButtons() {
        return buttons;
    }

    enum Type {APPLICANT, HR}

    @Override
    public void update() {
        if (type == Type.APPLICANT) setup(dimension, APPLICANT_BUTTONS);
        else if (type == Type.HR) setup(dimension, HR_BUTTONS);
    }
}
