package gui.major;

import domain.Enums.UserType;
import domain.user.NullUser;
import domain.user.User;
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.InputInfoPanel;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Class {@code Login} setup gui frame for login and deal with user input
 *
 * @author group 0120 of CSC207 summer 2019
 * @see InputInfoPanel
 * @see ButtonPanel
 * @see Main
 * @since 2019-08-05
 */
public class Login extends JFrame {

    // Frame size
    private static final int WIDTH = 520;
    private static final int HEIGHT = 300;

    // Related dimension of components in certain ratio
    private static final Dimension INPUT_SIZE = new Dimension(WIDTH - 20, HEIGHT - 100);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - 20, 50);

    // Login user types
    private static final String[] USER_TYPE = new String[]{"Applicant", "Hiring_Manager", "Recruiter", "Interviewer"};

    /**
     * Todo
     *
     * @see #getMain()
     * @see #login(User)
     */
    private Main main;

    /**
     * The panel deal with all user input
     *
     * @see InputInfoPanel
     * @see LoginListener
     * @see #getInputInfoPanel()
     * @see #infoPanelSetup()
     * @see #getUser()
     */
    private InputInfoPanel inputInfoPanel = new InputInfoPanel(INPUT_SIZE, true);

    /**
     * The panel deal with all buttons
     *
     * @see ButtonPanel
     * @see #buttonPanelSetup()
     */
    private ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);

    public Login(Main main) {
        this.main = main;
        setup();
    }

    private void setup() {
        setTitle("Login");
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        infoPanelSetup();
        buttonPanelSetup();
        setVisible(true);
    }

    private void buttonPanelSetup() {
        buttonPanel.addButton("Login", new LoginListener());
        buttonPanel.addButton("Register", new RegisterListener());
        add(buttonPanel);
    }

    private void infoPanelSetup() {
        ComponentFactory factory = getInputInfoPanel().getComponentFactory();
        factory.addTextField("Username:");
        factory.addPasswordField("Password:");
        factory.addComboBox("UserType:", USER_TYPE, "Applicant", false);
        add(getInputInfoPanel());
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
        return getMain().getStorage().getUser(userName, UserType.valueOf(userType));
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
            if (checkUser(user, password)) {
                getMain().getStorage().updateOpenJobPostings();
                login(user);
            }
        }
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            login(new NullUser());
        }
    }
}
