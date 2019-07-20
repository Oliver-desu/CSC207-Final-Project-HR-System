package gui.major;

import domain.storage.UserPool;
import domain.user.NullUser;
import domain.user.User;
import gui.panels.ButtonPanel;
import gui.panels.InputInfoPanel;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Login extends JFrame {

    private static final Dimension SIZE = new Dimension(520, 400);
    private static final Dimension INPUT_SIZE = new Dimension(500, 300);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(500, 50);
    private static final String[] USER_TYPE = new String[]{"Applicant", "HR_Generalist", "HR_Coordinator", "Interviewer"};

    private Main main;
    private InputInfoPanel inputInfoPanel = new InputInfoPanel();
    private ButtonPanel buttonPanel = new ButtonPanel();

    public Login(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {
        setTitle("Login");
        setSize(SIZE);
        setLayout(new FlowLayout());
        infoPanelSetup();
        buttonPanelSetup();
        setVisible(true);
    }

    private void buttonPanelSetup() {
        buttonPanel.setup(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Login", new LoginListener());
        buttonPanel.addButton("Register", new RegisterListener());
        add(buttonPanel);
    }

    private void infoPanelSetup() {
        inputInfoPanel.setup(INPUT_SIZE, true);
        inputInfoPanel.addTextField("Username:");
        inputInfoPanel.addPasswordField("Password:");
        inputInfoPanel.addComboBox("UserType:", USER_TYPE, "Applicant", false);
        add(inputInfoPanel);
    }

    public Main getMain() {
        return main;
    }

    private InputInfoPanel getInputInfoPanel() {
        return inputInfoPanel;
    }

    private User getUser() {
        HashMap<String, String> infoMap = getInputInfoPanel().getInfoMap();
        String userType = infoMap.get("UserType:").toUpperCase();
        String userName = infoMap.get("Username:");
        return getMain().getUserPool().getUser(UserPool.UserType.valueOf(userType), userName);
    }

    private boolean checkUser(User user, char[] password) {
        if (!user.isNull() && user.matchPassword(password)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, user or password is incorrect!");
            return false;
        }
    }

    private void login(User user) {
        this.setVisible(false);
        new UserMenu(getMain(), user);
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = getUser();
            char[] password = getInputInfoPanel().getPassword();
            if (checkUser(user, password)) login(user);
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            login(new NullUser());
        }
    }
}
