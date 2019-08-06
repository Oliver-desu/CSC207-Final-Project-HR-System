package gui.scenarios.recruiter;

import domain.Enums.JobPostingStatus;
import domain.Exceptions.NextRoundDoesNotExistException;
import domain.Exceptions.WrongEmployeeTypeException;
import domain.Exceptions.WrongInterviewRoundStatusException;
import domain.Exceptions.WrongJobPostingStatusException;
import domain.Test;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenuFrame;
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class {@code JobManageScenario} handles with the situation of managing jobs.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.MenuPanel
 * @since 2019-08-05
 */

public class JobManageScenario extends Scenario {

    /**
     * Create a new {@code JobManageScenario} that is a {@code Scenario} with title "Job Manager"
     * @param userMenuFrame the {@code userMenuFrame} that sets up the gui framework
     * @see gui.major.MenuPanel
     */
    public JobManageScenario(UserMenuFrame userMenuFrame) {
        super(userMenuFrame, "Job Manager");
    }

    /**
     * The {@code LeftFilterPanel} in this scenario.
     *
     * @see #initLeftFilter()
     * @see #initComponents()
     * @see #update()
     * @see ViewEditListener
     * @see AddRoundListener
     * @see NextRoundListener
     * @see EndJobPostingListener
     */
    private FilterPanel<JobPosting> leftFilter;

    /**
     * The {@code RightFilterPanel} in this scenario.
     *
     * @see #initRightFilter()
     * @see #initComponents()
     * @see #update()
     * @see ViewEditListener
     */
    private FilterPanel<InterviewRound> rightFilter;

    /**
     * An {@code InputInfoPanel} that sets up gui in this scenario.
     *
     * @see #update()
     * @see #initInput()
     * @see AddRoundListener
     */
    private InputInfoPanel infoPanel;

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        Employee recruiter = test.getRandomRecruiter(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getEmploymentCenter().getJobPostings()) {
            for (Applicant applicant : test.getEmploymentCenter().getAllApplicants()) {
                test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            }
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new JobManageScenario(new UserMenuFrame(test.getMain(), recruiter)).exampleView();
    }

    /**
     * Override {@code initComponents()} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initInput();
        initButton();
    }

    /**
     * A helper method for {@code initComponents()} that initializes the {@code infoPanel}.
     *
     * @see #initComponents()
     */

    protected void initInput() {
        infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        factory.addTextField("Round name:");
        add(infoPanel);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     *
     * @see #initComponents()
     */

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Jobs I Responsible to");
        leftFilter.addSelectionListener(new JobManageScenario.LeftFilterListener());
        add(leftFilter);
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */
    @Override
    protected void update() {
        Employee recruiter = (Employee) getUserMenuFrame().getUser();
        try {
            leftFilter.setFilterContent(recruiter.getJobPostings());
        } catch (WrongEmployeeTypeException e) {
            leftFilter.setFilterContent(new ArrayList<>());
        }
        JobPosting jobPosting = leftFilter.getSelectObject();
        if (jobPosting != null) {
            rightFilter.setFilterContent(jobPosting.getInterviewRoundManager().getInterviewRounds());
        } else {
            rightFilter.setFilterContent(new ArrayList<>());
        }
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     *
     * @see #initComponents()
     */
    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Job Interview Rounds");
        add(rightFilter);
    }

    /**
     * A helper method for {@code initComponents()} that initializes and add the new {@code ButtonPanel}.
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("View/Edit", new JobManageScenario.ViewEditListener());
        buttonPanel.addButton("Add Round", new JobManageScenario.AddRoundListener());
        buttonPanel.addButton("Next Round", new JobManageScenario.NextRoundListener());
        buttonPanel.addButton("End JobPosting", new JobManageScenario.EndJobPostingListener());
        add(buttonPanel);
    }

    /**
     * Class{@code LeftFilterListener} implements ListSelectionListener. It deals with actions happening on left filter panel.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since 2019-08-05
     */
    private class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null) {
                jobPosting.getInterviewRoundManager().checkStatus();
                rightFilter.setFilterContent(jobPosting.getInterviewRoundManager().getInterviewRounds());
                setOutputText(jobPosting.toString());
            }
        }
    }

    /**
     * Class {@code ViewEditListener} implements ActionListener. It deals with edits happening to the filters.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-05
     */
    private class ViewEditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            InterviewRound interviewRound = rightFilter.getSelectObject();
            if (interviewRound == null || jobPosting == null) {
                showMessage("No interviewRound selected!");
            } else {
                InterviewRoundScenario interviewRoundScenario = new InterviewRoundScenario(
                        getUserMenuFrame(), interviewRound, jobPosting);
                switchScenario(interviewRoundScenario);
            }
        }
    }

    /**
     * Class {@code AddRoundListener} implements ActionListener. It deals with situations of adding interview rounds
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-05
     */
    private class AddRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            String roundName = infoPanel.getInfoMap().get("Round name:");
            if (jobPosting == null) {
                showMessage("No job posting selected!");
            } else if (!jobPosting.getStatus().equals(JobPostingStatus.PROCESSING)) {
                showMessage("The status of job posting is not PROCESSING, can not add round!");
            } else {
                jobPosting.getInterviewRoundManager().addInterviewRound(new InterviewRound(roundName));
                showMessage("Succeed!");
                update();
            }
        }
    }

    /**
     * Class {@code AddRoundListener} implements ActionListener. It deals with situations of going to next round.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-05
     */
    private class NextRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            try {
                jobPosting.getInterviewRoundManager().nextRound();
                update();
                showMessage("Succeeds");
            } catch (NullPointerException e1) {
                showMessage("No job posting selected!");
            } catch (WrongJobPostingStatusException e1) {
                showMessage("The status of job posting is not PROCESSING, can not start next round!");
            } catch (WrongInterviewRoundStatusException e1) {
                showMessage("The status of current round is not FINISHED, can not start next round!");
            } catch (NextRoundDoesNotExistException e1) {
                showMessage(e1.getMessage());
            }
        }
    }

    /**
     * Class {@code EndJobPostingListener} implements ActionListener. It deals with closing job postings.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-05
     */
    private class EndJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting == null) {
                showMessage("No job posting selected!");
            } else if (jobPosting.getStatus().equals(JobPostingStatus.FINISHED)) {
                showMessage("The job posting has already closed!");
            } else {
                jobPosting.endJobPosting();
                update();
                showMessage("The jobPosting is now closed.");
            }
        }
    }
}
