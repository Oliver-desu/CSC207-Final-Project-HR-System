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

    public UserMenu(Main main, User user) {
        this.main = main;
        this.user = user;
        setup();
        showColor();
    }

    private void showColor() {
        menu.setBackground(Color.BLUE);
    }

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

    public Company getCompany() {
        Storage storage = getMain().getStorage();
        try {
            return storage.getCompany(user.getCompanyId());
        } catch (NotEmployeeException e) {
            return null;
        }
    }

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

    private void addLogoutButton() {
        String text;
        if (user.isNull()) text = "Back";
        else text = "Logout";
        JButton button = new JButton(text);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(new LogoutListener());
        menu.add(button);
    }

    private void logout() {
        this.setVisible(false);
        getMain().returnToLogin();
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            logout();
        }
    }
}
