package gui.major;

import domain.user.*;
import gui.scenarios.NullScenario;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 550;

    static final Dimension SCENARIO_SIZE = new Dimension(WIDTH * 4 / 5, HEIGHT - 50);
    private static final Dimension MENU_SIZE = new Dimension(WIDTH / 6, HEIGHT - 50);
    private static final Dimension BUTTON_SIZE = new Dimension(WIDTH / 7, 50);

    private Main main;
    private User user;
    private JPanel menu = new JPanel();
    private Scenario scenario = new NullScenario(this);

    public UserMenu() {
    }

    UserMenu(Main main, User user) {
        this.main = main;
        this.user = user;
        setup();
        showColor();
    }

    private void showColor() {
        menu.setBackground(Color.BLUE);
        scenario.showColor();
    }

    private void setup() {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new FlowLayout());
        menuSetup();
        add(scenario);
        setVisible(true);
    }

    private void clearScenario() {
        remove(this.scenario);
    }

    public void setScenario(Scenario scenario) {
        clearScenario();
        this.scenario = scenario;
        scenario.init();
        add(scenario);
    }

    private User getUser() {
        return user;
    }

    private Main getMain() {
        return main;
    }

    private void menuSetup() {
        menu.setPreferredSize(MENU_SIZE);
        menu.setLayout(new FlowLayout());
        User user = getUser();
        if (user.isNull()) registerMenuSetup();
        else if (user instanceof Applicant) applicantMenuSetup((Applicant) user);
        else if (user instanceof Interviewer) interviewerMenuSetup((Interviewer) user);
        else if (user instanceof HRCoordinator) coordinatorMenuSetup((HRCoordinator) user);
        else if (user instanceof HRGeneralist) generalistMenuSetup((HRGeneralist) user);
        addLogoutButton();
        add(menu);
    }

    private void registerMenuSetup() {

    }

    private void interviewerMenuSetup(Interviewer interviewer) {

    }

    private void coordinatorMenuSetup(HRCoordinator hrCoordinator) {

    }

    private void generalistMenuSetup(HRGeneralist hrGeneralist) {

    }

    private void applicantMenuSetup(Applicant applicant) {

    }

    private void addMenuButton(String buttonName, Scenario scenario) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(new SwitchScenarioListener(scenario));
        menu.add(button);
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

    class SwitchScenarioListener implements ActionListener {

        private Scenario scenario;

        SwitchScenarioListener(Scenario scenario) {
            this.scenario = scenario;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setScenario(scenario);
        }
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logout();
        }
    }
}
