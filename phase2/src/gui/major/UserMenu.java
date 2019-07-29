package gui.major;

import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.user.*;
import gui.scenarios.ApplicationManageScenario;
import gui.scenarios.NullScenario;
import gui.scenarios.coordinator.ApplicationScenario;
import gui.scenarios.coordinator.JobManageScenario;
import gui.scenarios.generalist.JobPostingRegister;
import gui.scenarios.generalist.ViewPostingScenario;
import gui.scenarios.interviewer.OngoingInterviewScenario;
import gui.scenarios.oliver.DocumentManageScenario;
import gui.scenarios.oliver.JobSearchingScenario;
import gui.scenarios.oliver.ViewInterviewScenario;
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

    public UserMenu(Main main, User user) {
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
        ((JButton) menu.getComponent(0)).doClick();
        setVisible(true);
    }

    public Company getCompany() {
        CompanyPool companyPool = getMain().getCompanyPool();
        return companyPool.getCompany(user.getCompanyId());
    }

    private void clearScenario() {
        remove(this.scenario);
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

    private void menuSetup() {
        menu.setPreferredSize(MENU_SIZE);
        menu.setLayout(new FlowLayout());
        User user = getUser();
        if (user.isNull()) registerMenuSetup();
        else if (user instanceof Applicant) applicantMenuSetup();
        else if (user instanceof Interviewer) interviewerMenuSetup();
        else if (user instanceof HRCoordinator) coordinatorMenuSetup();
        else if (user instanceof HRGeneralist) generalistMenuSetup();
        addLogoutButton();
        add(menu);
    }

    private void registerMenuSetup() {
        addMenuButton("Applicant", new UserRegister(this, UserRegister.RegisterType.APPLICANT));
        addMenuButton("HR Coordinator", new UserRegister(this, UserRegister.RegisterType.HR_COORDINATOR));
        addMenuButton("HR Generalist", new UserRegister(this, UserRegister.RegisterType.HR_GENERALIST));
        addMenuButton("Interviewer", new UserRegister(this, UserRegister.RegisterType.INTERVIEWER));
    }

    private void interviewerMenuSetup() {
        addMenuButton("Ongoing Interview", new OngoingInterviewScenario(this));
    }

    private void coordinatorMenuSetup() {
        addMenuButton("All Applications", new ApplicationScenario(this));
        addMenuButton("JobManaging", new JobManageScenario(this));
    }

    private void generalistMenuSetup() {
        addMenuButton("Create Posting", new JobPostingRegister(this));
        addMenuButton("View Posting", new ViewPostingScenario(this));
    }

    private void applicantMenuSetup() {
        Applicant applicant = (Applicant) getUser();
        addMenuButton("Upcoming Interviews", new ViewInterviewScenario(this));
        addMenuButton("Apply Jobs", new JobSearchingScenario(this));
        addMenuButton("Manage Application", new ApplicationManageScenario(this));
        addMenuButton("My Documents", new DocumentManageScenario(this,
                applicant.getDocumentManager(), null));
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
