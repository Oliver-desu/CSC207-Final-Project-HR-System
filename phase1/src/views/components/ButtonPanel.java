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
    private static final String[] APPLICANT_BUTTONS = new String[]{"Drop Application", "Submit Application"};
    private static final String[] LOGIN_BUTTONS = new String[]{"Sign In", "Create new Account"};
    private HashMap<String, JButton> buttons = new HashMap<>();

    private Dimension dimension;
    private View view = View.LOGIN;

    public ButtonPanel(Dimension dimension) {
        this.dimension = dimension;
        update();
    }

    public void setView(View view) {
        this.view = view;
    }

    private void setup(Dimension dimension, String[] buttonNames) {
        // panel settings
        setLayout(new FlowLayout(FlowLayout.CENTER));
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

    @Override
    public void update() {
        if (view == View.APPLICANT) setup(dimension, APPLICANT_BUTTONS);
        else if (view == View.HR) setup(dimension, HR_BUTTONS);
        else if (view == View.LOGIN) setup(dimension, LOGIN_BUTTONS);
    }

    public enum View {APPLICANT, HR, LOGIN}
}
