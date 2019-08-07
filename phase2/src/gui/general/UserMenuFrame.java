package gui.general;

import main.Main;
import model.exceptions.NotEmployeeException;
import model.storage.EmploymentCenter;
import model.user.Company;
import model.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code UserMenuFrame} sets up gui frame for user menu with common buttons and provides getters of information.
 * It is a subclass of {@code JFrame}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see Scenario
 * @since 2019-08-05
 */
public class UserMenuFrame extends JFrame {

    // Frame Size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    // Related dimension of components in certain ratio
    static final Dimension SCENARIO_SIZE = new Dimension(WIDTH * 4 / 5 - 20, HEIGHT - 50);
    private static final Dimension MENU_SIZE = new Dimension(WIDTH / 5 - 20, HEIGHT - 50);
    private static final Dimension BUTTON_SIZE = new Dimension(WIDTH / 6, 50);

    /**
     * //todo
     *
     * @see Main
     * @see #getMain()
     * @see #logout()
     */
    private Main main;

    /**
     * The {@code User} related to this {@code UserMenuFrame}.
     *
     * @see #getUser()
     * @see #getCompany()
     * @see #addLogoutButton()
     */
    private User user;

    /**
     * The {@code MenuPanel} that contains all buttons in this {@code UserMenuFrame}.
     *
     * @see #setup()
     * @see #addLogoutButton()
     * @see #showColor()
     */
    private MenuPanel menu;

    /**
     * The {@code Scenario} associated with this {@code UserMenuFrame}.
     *
     * @see #clearScenario()
     */
    private Scenario scenario;

    public UserMenuFrame() {
    }

    /**
     * Construct a new {@code UserMenuFrame}.
     *
     * @param main the {@code Main} for the frame
     * @param user the interrelated {@code User} for this frame
     * @see LoginFrame
     */
    public UserMenuFrame(Main main, User user) {
        this.main = main;
        this.user = user;
        setup();
        showColor();
    }

    /**
     * A helper function that helps the constructor to set background color.
     *
     * @see #UserMenuFrame(Main, User)
     */
    private void showColor() {
        menu.setBackground(Color.BLUE);
    }

    /**
     * A helper function that helps the constructor to set up the gui.
     *
     * @see #UserMenuFrame(Main, User)
     */
    private void setup() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        menu = new MenuPanel(this, MENU_SIZE, BUTTON_SIZE);
        menu.setBackground(Color.BLUE);
        addLogoutButton();
        add(menu);
        ((JButton) menu.getComponent(0)).doClick();
        setVisible(true);
    }

    /**
     * Get the {@code Company} of the {@code user} if he/she is an employee.
     *
     * @return the {@code Company} that the {@code user} belongs to; {@code null} only when the user is not an employee
     */
    public Company getCompany() {
        EmploymentCenter employmentCenter = getMain().getEmploymentCenter();
        try {
            return employmentCenter.getCompany(user.getCompanyId());
        } catch (NotEmployeeException e) {
            return null;
        }
    }

    /**
     * Clear a {@code Scenario} if the field {@code scenario} is not {@code null}.
     * It is a helper method for {@link #setScenario(Scenario)}.
     */
    private void clearScenario() {
        if (scenario != null) remove(this.scenario);
    }

    /**
     * Replace the old {@code Scenario} with a new one.
     *
     * @param scenario the new {@code Scenario} to be set
     * @see MenuPanel
     * @see Scenario#switchScenario(Scenario)
     */
    public void setScenario(Scenario scenario) {
        clearScenario();
        this.scenario = scenario;
        scenario.init();
        add(scenario);
        scenario.updateUI();
    }

    public User getUser() {
        return user;
    }

    Main getMain() {
        return main;
    }

    /**
     * It is a helper method for {@link #setup()}
     * that adds "Back" button for the register page or adds "Logout" button for a user menu.
     */
    private void addLogoutButton() {
        String text;
        if (user.isNull()) text = "Back";
        else text = "Logout";
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(new LogoutListener());
        menu.add(button);
    }

    /**
     * A helper method that helps to return back to the log in frame.
     *
     * @see UserMenuFrame.LogoutListener
     */
    private void logout() {
        this.setVisible(false);
        getMain().returnToLogin();
    }

    /**
     * Class {@code LogoutListener} implements {@code ActionListener}.
     * It deals with the occasion when "Log out" button is clicked.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see UserMenuFrame
     * @since 2019-08-05
     */
    private class LogoutListener implements ActionListener {
        /**
         * Override the method {@code actionPerformed} in the interface {@code ActionListener}.
         *
         * @param e the action event that a button is clicked
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }
}
