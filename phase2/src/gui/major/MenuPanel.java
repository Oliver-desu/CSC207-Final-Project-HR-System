package gui.major;

import domain.Enums.UserType;
import domain.user.User;
import gui.scenarios.applicant.ApplicationManageScenario;
import gui.scenarios.applicant.DocumentManageScenario;
import gui.scenarios.applicant.JobSearchingScenario;
import gui.scenarios.applicant.ViewInterviewScenario;
import gui.scenarios.hiringManager.JobPostingRegister;
import gui.scenarios.hiringManager.ViewPostingScenario;
import gui.scenarios.interviewer.OngoingInterviewScenario;
import gui.scenarios.recruiter.ApplicationScenario;
import gui.scenarios.recruiter.JobManageScenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code MenuPanel} setup gui panel for buttons that varies in different types of user menu
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserMenu
 * @since 2019-08-05
 */
public class MenuPanel extends JPanel {

    /**
     * The user menu that contains this panel
     *
     * @see SwitchScenarioListener
     * @see #setup()
     */
    private UserMenu userMenu;

    /**
     * The dimension of buttons
     *
     * @see #addMenuButton(String, Scenario)
     */
    private Dimension buttonSize;

    /**
     * The dimension of menu
     *
     * @see #setup()
     */
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
        else if (user.getUserType().equals(UserType.APPLICANT)) applicantMenuSetup();
        else if (user.getUserType().equals(UserType.INTERVIEWER)) interviewerMenuSetup();
        else if (user.getUserType().equals(UserType.RECRUITER)) recruiterMenuSetup();
        else if (user.getUserType().equals(UserType.HIRING_MANAGER)) hiringManagerMenuSetup();
    }

    private void registerMenuSetup() {
        addMenuButton("Applicant", new UserRegister(userMenu, UserType.APPLICANT));
        addMenuButton("Employee", new UserRegister(userMenu));
    }

    private void interviewerMenuSetup() {
        addMenuButton("Ongoing Interview", new OngoingInterviewScenario(userMenu));
    }

    private void recruiterMenuSetup() {
        addMenuButton("All Applications", new ApplicationScenario(userMenu));
        addMenuButton("JobManaging", new JobManageScenario(userMenu));
    }

    private void hiringManagerMenuSetup() {
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
