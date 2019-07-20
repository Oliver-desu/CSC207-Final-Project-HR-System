package gui.major;

import gui.panels.ButtonPanel;
import gui.panels.InputInfoPanel;
import main.Main;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private static final Dimension SIZE = new Dimension(0, 0);
    private static final Dimension INPUT_SIZE = new Dimension(0, 0);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(0, 0);
    private static final String[] USER_TYPE = new String[]{"Applicant", "HRGeneralist", "HRCoordinator", "Interviewer"};

    private Main main;
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    public Login(Main main) {

    }

    private void setup() {
        inputInfoPanel.setup(INPUT_SIZE, true);
        inputInfoPanel.addTextArea("Username:");
        inputInfoPanel.addPasswordField("Password:");
        inputInfoPanel.addComboBox("UserType:", USER_TYPE, "Applicant", false);
    }
}
