package gui.major;

import domain.user.*;
import gui.scenarios.NullScenario;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame {

    static final Dimension SCENARIO_SIZE = new Dimension(800, 500);
    private static final int WIDTH = 0;
    private static final int HEIGHT = 0;
    private static final Dimension MENU_SIZE = new Dimension(0, 0);
    private static final Dimension BUTTON_SIZE = new Dimension(0, 0);

    private Main main;
    private User user;
    private JPanel menu = new JPanel();
    private Scenario scenario = new NullScenario(this);

    public UserMenu() {
    }

    public UserMenu(Main main, User user) {
        this.main = main;
        this.user = user;
        basicSetup();
        setupMenu();
    }

    private void basicSetup() {
        menu.setPreferredSize(MENU_SIZE);
        menu.setLayout(new FlowLayout());

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        add(menu);
        add(scenario);
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

    private void setupMenu() {
        User user = getUser();
        if (user instanceof Applicant) setupApplicantMenu((Applicant) user);
        else if (user instanceof Interviewer) setupInterviewerMenu((Interviewer) user);
        else if (user instanceof HRCoordinator) setupHRCoordinatorMenu((HRCoordinator) user);
        else if (user instanceof HRGeneralist) setupHRGeneralistMenu((HRGeneralist) user);
    }

    private void setupInterviewerMenu(Interviewer interviewer) {

    }

    private void setupHRCoordinatorMenu(HRCoordinator hrCoordinator) {

    }

    private void setupHRGeneralistMenu(HRGeneralist hrGeneralist) {

    }

    private void setupApplicantMenu(Applicant applicant) {

    }

    private void addMenuButton(String buttonName, Scenario scenario) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(new SwitchScenario(scenario));
        menu.add(button);
    }

    class SwitchScenario implements ActionListener {

        private Scenario scenario;

        SwitchScenario(Scenario scenario) {
            this.scenario = scenario;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setScenario(scenario);
        }
    }
}
