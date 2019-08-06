package gui.major;

import domain.Exceptions.NotEmployeeException;
import domain.storage.Storage;
import domain.user.Company;
import domain.user.User;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code UserMenu} setup gui frame for user menu with common buttons and provide getters of information
 *
 * @author group 0120 of CSC207 summer 2019
 * @see Main
 * @see User
 * @see MenuPanel
 * @see Scenario
 * @since 2019-08-05
 */
public class UserMenu extends JFrame {

    // Frame Size
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    // Related dimension of components in certain ratio
    static final Dimension SCENARIO_SIZE = new Dimension(WIDTH * 4 / 5 - 20, HEIGHT - 50);
    private static final Dimension MENU_SIZE = new Dimension(WIDTH / 5 - 20, HEIGHT - 50);
    private static final Dimension BUTTON_SIZE = new Dimension(WIDTH / 6, 50);

    /**
     * Todo
     *
     * @see Main
     * @see #getMain()
     * @see #logout()
     */
    private Main main;

    /**
     * The user
     *
     * @see User
     * @see #getUser()
     * @see #getCompany()
     * @see #addLogoutButton()
     */
    private User user;

    /**
     * The panel contains all buttons
     *
     * @see MenuPanel
     * @see #setup()
     * @see #addLogoutButton()
     */
    private MenuPanel menu;

    /**
     * The panel be able to do main operations
     *
     * @see Scenario
     * @see #clearScenario()
     * @see #setScenario(Scenario)
     */
    private Scenario scenario;

    public UserMenu() {
    }

    /**
     * create a new {@code UserMenu} , and then setup for this menu .
     *
     * @param main given java.main
     * @param user given user
     */
    public UserMenu(Main main, User user) {
        this.main = main;
        this.user = user;
        setup();
        showColor();
    }

    private void showColor() {
        menu.setBackground(Color.BLUE);
    }

    /**
     * setup the width and height , set the Layout to flowout and set visible to true
     *
     * @see #UserMenu(Main, User)
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
     * return a {@code Company} that this {@code User} belongs to
     *
     * @return a {@code Company} that this {@code User}  belongs to
     * @throws NotEmployeeException if this user is not a Employee.
     */
    public Company getCompany() {
        Storage storage = getMain().getStorage();
        try {
            return storage.getCompany(user.getCompanyId());
        } catch (NotEmployeeException e) {
            return null;
        }
    }

    /**
     * remove scenario from Jframe
     */
    private void clearScenario() {
        if (scenario != null) remove(this.scenario);
    }

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
     * add the LogoutButton to {@code UserMenu}  if the user is not a {@code NullUser} , then ,
     * add the LogoutListener to this button
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
     * set this {@code UserMenu to be invisible and return to login scenario}
     */
    private void logout() {
        this.setVisible(false);
        getMain().returnToLogin();
    }

    /**
     * Class {@code LogoutListener} The listener used for logout event
     *
     * @author group 0120 of CSC207 summer 2019
     * @see UserMenu
     * @since 2019-08-05
     */
    private class LogoutListener implements ActionListener {
        /**
         * overrides the method  in parent class{@code ActionListener}
         *
         * @param e the actionevent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }
}
