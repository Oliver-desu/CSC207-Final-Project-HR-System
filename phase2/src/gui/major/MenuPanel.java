package gui.major;

import domain.user.User;
import gui.scenarios.applicant.ApplicationManageScenario;
import gui.scenarios.applicant.DocumentManageScenario;
import gui.scenarios.applicant.JobSearchingScenario;
import gui.scenarios.applicant.ViewInterviewScenario;
import gui.scenarios.coordinator.ApplicationScenario;
import gui.scenarios.coordinator.JobManageScenario;
import gui.scenarios.generalist.JobPostingRegister;
import gui.scenarios.generalist.ViewPostingScenario;
import gui.scenarios.interviewer.OngoingInterviewScenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private UserMenu userMenu;
    private Dimension buttonSize;
    private Dimension menuSize;

    public MenuPanel(UserMenu userMenu, Dimension menuSize, Dimension buttonSize) {
        this.userMenu = userMenu;
        this.menuSize = menuSize;
        this.buttonSize = buttonSize;
        setup();
    }

    private void setup() {
        setPreferredSize(menuSize);
        setLayout(new FlowLayout());
        User user = userMenu.getUser();
        if (user.isNull()) registerMenuSetup();
        else if (user.getUserType().equals(User.UserType.APPLICANT)) applicantMenuSetup();
        else if (user.getUserType().equals(User.UserType.INTERVIEWER)) interviewerMenuSetup();
        else if (user.getUserType().equals(User.UserType.HR_COORDINATOR)) coordinatorMenuSetup();
        else if (user.getUserType().equals(User.UserType.HR_GENERALIST)) generalistMenuSetup();
    }

    private void registerMenuSetup() {
        addMenuButton("Applicant", new UserRegister(userMenu, User.UserType.APPLICANT));
        addMenuButton("HR Coordinator", new UserRegister(userMenu, User.UserType.HR_COORDINATOR));
        addMenuButton("HR Generalist", new UserRegister(userMenu, User.UserType.HR_GENERALIST));
        addMenuButton("Interviewer", new UserRegister(userMenu, User.UserType.INTERVIEWER));
    }

    private void interviewerMenuSetup() {
        addMenuButton("Ongoing Interview", new OngoingInterviewScenario(userMenu));
    }

    private void coordinatorMenuSetup() {
        addMenuButton("All Applications", new ApplicationScenario(userMenu));
        addMenuButton("JobManaging", new JobManageScenario(userMenu));
    }

    private void generalistMenuSetup() {
        addMenuButton("Create Posting", new JobPostingRegister(userMenu));
        addMenuButton("View Posting", new ViewPostingScenario(userMenu));
    }

    private void applicantMenuSetup() {
        addMenuButton("Upcoming Interviews", new ViewInterviewScenario(userMenu));
        addMenuButton("Apply Jobs", new JobSearchingScenario(userMenu));
        addMenuButton("Manage Application", new ApplicationManageScenario(userMenu));
        addMenuButton("My Documents", new DocumentManageScenario(userMenu, null));
    }

    private void addMenuButton(String buttonName, Scenario scenario) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new SwitchScenarioListener(scenario));
        add(button);
    }

    private class SwitchScenarioListener implements ActionListener {

        private Scenario scenario;

        SwitchScenarioListener(Scenario scenario) {
            this.scenario = scenario;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            userMenu.setScenario(scenario);
        }
    }
}
