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
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code MenuPanel} setup gui panel for buttons that varies in different types of user menu.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserMenu
 * @since 2019-08-05
 */
public class MenuPanel extends JPanel {

    /**
     * The user menu that contains this panel
     *
     * @see #setup()
     * @see #registerMenuSetup()
     * @see #interviewerMenuSetup()
     * @see #recruiterMenuSetup()
     * @see  #hiringManagerMenuSetup()
     * @see #applicantMenuSetup()
     * @see MenuPanel.SwitchScenarioListener#actionPerformed(ActionEvent)
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

    /**
     * creat a new {@code MenuPanel} with given usermenu{@code UserMenu} , with dimension of menuSize and buttonSize
     *
     * @param userMenu   given UserMenu{@code Usermenu}
     * @param menuSize   dimension of the menuSize
     * @param buttonSize dimension of the buttonSize
     * @see null
     */
    public MenuPanel(UserMenu userMenu, Dimension menuSize, Dimension buttonSize) {
        this.userMenu = userMenu;
        this.menuSize = menuSize;
        this.buttonSize = buttonSize;
        setup();
    }

    /**
     * set the menuSize ,setup the layout to FlowLayout ,call the different MenuSetup() depends on the type of User.
     *
     * @see #MenuPanel(UserMenu, Dimension, Dimension)
     */
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

    /**
     * add "Applicant" button  and UserMenu with UserType.APPLICANT.
     * @see #setup()
     */
    private void registerMenuSetup() {
        addMenuButton("Applicant", new UserRegister(userMenu, UserType.APPLICANT));
        addMenuButton("Employee", new UserRegister(userMenu));
    }

    /**
     * add "Ongoing Interview" button  and setup a new OngoingInterviewScenario with usermenu.
     * @see #setup()
     */
    private void interviewerMenuSetup() {
        addMenuButton("Ongoing Interview", new OngoingInterviewScenario(userMenu));
    }

    /**
     * add "All Applications" button and "JobManaging" button then set up ApplicationScenario and
     * JobManageScenario.
     * @see #setup()
     */
    private void recruiterMenuSetup() {
        addMenuButton("All Applications", new ApplicationScenario(userMenu));
        addMenuButton("JobManaging", new JobManageScenario(userMenu));
    }

    /**
     * add "Create Posting" button and "View Posting" button then set up JobPostingRegister and
     * ViewPostingScenario.
     * @see #setup()
     */
    private void hiringManagerMenuSetup() {
        addMenuButton("Create Posting", new JobPostingRegister(userMenu));
        addMenuButton("View Posting", new ViewPostingScenario(userMenu));
    }

    /**
     * add "Upcoming Interviews","Apply Jobs" ,"Manage Application","My Documents"buttons
     * then set up ViewInterviewScenario,JobSearchingScenario ,ApplicationManageScenario
     * and DocumentManageScenario(with no applicationDocument).
     * @see #setup()
     */
    private void applicantMenuSetup() {
        addMenuButton("Upcoming Interviews", new ViewInterviewScenario(userMenu));
        addMenuButton("Apply Jobs", new JobSearchingScenario(userMenu));
        addMenuButton("Manage Application", new ApplicationManageScenario(userMenu));
        addMenuButton("My Documents", new DocumentManageScenario(userMenu, null));
    }

    /**
     * add a new button to this panel , and add a new SwitchScenarioListener to the button.
     * @param buttonName  a string represent the name of button.
     * @param scenario  the page that want to be added
     * @see #registerMenuSetup()
     * @see  #interviewerMenuSetup()
     * @see  #recruiterMenuSetup()
     * @see #hiringManagerMenuSetup()
     * @see #applicantMenuSetup()
     */
    private void addMenuButton(String buttonName, Scenario scenario) {
        JButton button = new JButton(buttonName);
        button.setPreferredSize(buttonSize);
        button.addActionListener(new SwitchScenarioListener(scenario));
        add(button);
    }

    /**
     * Class {@code SwitchScenarioListener} the actionListener used to switch between different scenario.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see MenuPanel#addAncestorListener(AncestorListener)
     * @since 2019-08-05
     */
    private class SwitchScenarioListener implements ActionListener {
        /**
         * the scenario that want to switch to.
         * @see #SwitchScenarioListener(Scenario)
         */
        private Scenario scenario;

        /**
         * set this scenario to the given scenario.
         * @param scenario the scenario need to switch to
         * @see #addAncestorListener(AncestorListener)
         */
        SwitchScenarioListener(Scenario scenario) {
            this.scenario = scenario;
        }

        /**
         * overrides the method in interface class{@code ActionListener}
         * @param e the ActionEvent that be passed in
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            userMenu.setScenario(scenario);
        }
    }
}
