package gui.major;

import domain.Exceptions.NotCompanyWorkerException;
import domain.storage.InfoCenter;
import domain.user.Company;
import domain.user.User;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame {

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 600;

    static final Dimension SCENARIO_SIZE = new Dimension(WIDTH * 4 / 5 - 20, HEIGHT - 50);
    private static final Dimension MENU_SIZE = new Dimension(WIDTH / 5 - 20, HEIGHT - 50);
    private static final Dimension BUTTON_SIZE = new Dimension(WIDTH / 6, 50);

    private Main main;
    private User user;
    private JPanel menu;
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
        addLogoutButton();
        add(menu);
        ((JButton) menu.getComponent(0)).doClick();
        setVisible(true);
    }

    public Company getCompany() {
        InfoCenter infoCenter = getMain().getInfoCenter();
        try {
            return infoCenter.getCompany(user.getCompanyId());
        } catch (NotCompanyWorkerException e) {
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

    private void Logout() {
        this.setVisible(false);
        getMain().returnToLogin();
    }

    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logout();
        }
    }
}
