package gui.general;

import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.InputInfoPanel;
import main.Main;
import model.enums.UserType;
import model.exceptions.CannotSaveSystemException;
import model.exceptions.InvalidInputException;
import model.user.NullUser;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

/**
 * Class {@code LoginFrame} setup gui frame for login and deal with user input
 *
 * @author group 0120 of CSC207 summer 2019
 * @see InputInfoPanel
 * @see ButtonPanel
 * @see Main
 * @since 2019-08-05
 */
public class LoginFrame extends JFrame {

    // Frame size
    private static final int WIDTH = 520;
    private static final int HEIGHT = 300;

    // Related dimension of components in certain ratio
    private static final Dimension INPUT_SIZE = new Dimension(WIDTH - 20, HEIGHT - 100);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(WIDTH - 20, 50);

    // LoginFrame user types
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

    /**
     * Constructor for {@code LoginFrame}.
     * @param main #Todo : don t know how to describe
     */
    public LoginFrame(Main main) {
        this.main = main;
        if (!getMain().isSuccessfullyLoaded()) {
            JOptionPane.showMessageDialog(this, "Warning! Not successfully loaded data!");
        }
        setup();
    }

    /**
     * setup the title ,size,Layout, infoPanel , buttonPanel , ant set this frame to be visible.
     */
    private void setup() {
        setTitle("LoginFrame");
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        infoPanelSetup();
        buttonPanelSetup();
        setVisible(true);
    }

    /**
     * add  "LoginFrame" button with new LoginListener and "Register"to buttonPanel  with new RegisterListener.
     *
     * @see #setup()
     */
    private void buttonPanelSetup() {
        buttonPanel.addButton("LoginFrame", new LoginListener());
        buttonPanel.addButton("Register", new RegisterListener());
        buttonPanel.addButton("Save & Restart", new SaveSystemListener());
        add(buttonPanel);
    }

    /** setup the infoPanel,  add TextField with string"Username" , add a addPasswordField with name "Password",
     * add a ComboBox with  UserType (defaultValue is Applicant)
     */
    private void infoPanelSetup() {
        ComponentFactory factory = getInputInfoPanel().getComponentFactory();
        factory.addTextField("Username:");
        factory.addPasswordField("Password:");
        factory.addComboBox("UserType:", USER_TYPE, "Applicant", false);
        add(getInputInfoPanel());
    }

    /**
     * @return main of this page
     */
    public Main getMain() {
        return main;
    }

    /**
     * @return the inputInfoPanel of this frame.
     * @see  #infoPanelSetup()
     * @see #getUser()
     */
    private InputInfoPanel getInputInfoPanel() {
        return inputInfoPanel;
    }

    /**
     * get the User name and User type  stored in the infoMap , then ask storage{@code storage} to return a
     * User with given information.
     */
    private User getUser() {
        HashMap<String, String> infoMap = getInputInfoPanel().getInfoMap();
        String userType = infoMap.get("UserType:").toUpperCase();
        String userName = infoMap.get("Username:");
        return getMain().getEmploymentCenter().getUser(userName, UserType.valueOf(userType));
    }

    /**
     * check if the user's password matched the given password.
     * @param user the User want to be check if the passed in password is matched
     * @param password  the password passed in
     */
    private boolean checkUser(User user, char[] password) {
        if (!user.isNull() && user.matchPassword(password)) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, user or password is incorrect!");
            return false;
        }
    }

    /**
     * set this JFrame to be invisible and create a new UserMenuFrame with given user.
     * @param user the given user  that need to be create with new userMenu.
     */
    private void login(User user) {
        this.setVisible(false);
        new UserMenuFrame(getMain(), user);
    }

    /**
     * Class {@code LoginListener} setup the LoginListener ,this listener is used for check if the password is matched
     *
     * @see LoginFrame {@link #buttonPanelSetup()}
     */
    private class LoginListener implements ActionListener {
        /**
         * overrides the method in interface{@code ActionListener}
         * set up the actionPerformed in this listener , if the password stored in InputInfoPanel matched
         * this user , then login with this user's information
         * @param e the actionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = getUser();
            char[] password = getInputInfoPanel().getPassword();
            if (checkUser(user, password)) {
                getMain().getEmploymentCenter().updateOpenJobPostings();
                login(user);
            }
        }
    }

    /**
     * Class {@code RegisterListener} setup the RegisterListener .
     *
     * @see LoginFrame {@link #buttonPanelSetup()}
     */
    private class RegisterListener implements ActionListener {
        /**
         * overrides the method in interface{@code ActionListener}
         *  login with a NullUser in order to register.
         * @param e actionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            login(new NullUser());
        }
    }

    private class SaveSystemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Main main = getMain();
            String message = "How many days elapse before restart system";
            try {
                main.saveSystem();
                Main.setDaysElapse(JOptionPane.showInputDialog(LoginFrame.this, message));
            } catch (CannotSaveSystemException | InvalidInputException e1) {
                JOptionPane.showMessageDialog(LoginFrame.this, e1.getMessage());
            }
        }
    }
}
