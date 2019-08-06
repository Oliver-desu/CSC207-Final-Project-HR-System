package gui.scenarios.recruiter;

import domain.Enums.InterviewRoundStatus;
import domain.Enums.JobPostingStatus;
import domain.Exceptions.CurrentRoundUnfinishedException;
import domain.Exceptions.JobPostingAlreadyFilledException;
import domain.Exceptions.WrongApplicationStatusException;
import domain.Exceptions.WrongJobPostingStatusException;
import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code ViewPostingScenario} handles the situation where the hiring manager can view the interview rounds of the applicants
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.MenuPanel
 */
public class InterviewRoundScenario extends Scenario {

    /**
     * The {@code InterviewRound} that is being shown.
     *
     * @see HireListener
     * @see MatchInterviewListener
     */
    private InterviewRound interviewRound;

    /**
     * The{@code InterviewRoundManager} that is viewing interview rounds.
     *
     * @see HireListener
     * @see MatchInterviewListener
     */
    private InterviewRoundManager manager;

    /**
     * The {@code LeftFilterPanel} in this scenario.
     *
     * @see #initComponents()
     * @see #update()
     * @see #initLeftFilter()
     * @see LeftFilterListener
     * @see HireListener
     */
    private FilterPanel<Application> leftFilter;

    /**
     * The {@code RightFilterPanel} in this scenario.
     *
     * @see #initComponents()
     * @see #update()
     * @see #initRightFilter()
     *
     */
    private FilterPanel<Interview> rightFilter;

    /**
     * Create a new {@code InterviewRoundScenario} that is a {@code Scenario} with title "Interview Round Manager"
     * @param userMenu the {@code userMenu} that sets up the gui framework
     * @param interviewRound Interview Rounds that are concerned.
     * @param jobPosting JobPostings that are concerned.
     */

    public InterviewRoundScenario(UserMenu userMenu, InterviewRound interviewRound, JobPosting jobPosting) {
        super(userMenu, "Interview Round Manager");
        this.interviewRound = interviewRound;
        this.manager = jobPosting.getInterviewRoundManager();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        Employee recruiter = test.getRandomRecruiter(company);
        JobPosting jobPosting = test.getRandomJobPosting(test.getRandomCompany());
        for (Applicant applicant : test.getStorage().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        new InterviewRoundScenario(new UserMenu(test.getMain(), recruiter), jobPosting.getInterviewRoundManager().getCurrentInterviewRound(), jobPosting).exampleView();

    }

    /**
     * Override {@code initComponents()} in abstract class {@code Scenario}.
     */

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * A helper method for {@code initComponents()}.
     *
     * @see #initComponents()
     */

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Match Interview", new MatchInterviewListener());
        buttonPanel.addButton("Hire", new HireListener());
        add(buttonPanel);
    }


    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */
    @Override
    protected void update() {
        leftFilter.setFilterContent(interviewRound.getCurrentRoundApplications());
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     *
     * @see #initComponents()
     */
    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Remaining Applications");
        leftFilter.addSelectionListener(new LeftFilterListener());
        add(leftFilter);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     *
     * @see #initComponents()
     */

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Interviews");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    /**
     * Class{@code LeftFilterListener} implements ListSelectionListener. It deals with actions happening on left filter panel.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since 2019-08-05
     */

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null) {
                rightFilter.setFilterContent(application.getInterviews());
                setOutputText(application.toString());
            }
        }
    }

    /**
     * Class {@code HireListener} implements ActionListener. It deals with actions related to hiring.
     *
     * @author group 0120 of CSC207 summer 2019
     * @since 2019-08-05
     */
    class HireListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (interviewRound == manager.getCurrentInterviewRound()) {
                Application application = leftFilter.getSelectObject();
                try {
                    manager.hire(application);
                    showMessage("Succeed!");
                    initLeftFilter();
                } catch (NullPointerException e1) {
                    showMessage("No application selected!");
                } catch (WrongJobPostingStatusException e1) {
                    showMessage("The status of JobPosting is not PROCESSING, can not hire!");
                } catch (WrongApplicationStatusException e1) {
                    showMessage("The status of Application is not PENDING, can not hire!");
                } catch (CurrentRoundUnfinishedException | JobPostingAlreadyFilledException e1) {
                    showMessage(e1.getMessage());
                }
            } else {
                showMessage("Can only hire people in the most recent interview round!");
            }
        }
    }

    /**
     * Class {@code MatchInterviewListener} implements ActionListener. It deals with matching interviews.
     *
     * @author group 0120 of CSC207 summer 2019
     * @since 2019-08-05
     *
     */

    class MatchInterviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu menu = getUserMenu();
            if (!manager.getJobPosting().getStatus().equals(JobPostingStatus.PROCESSING)) {
                showMessage("JobPosting already finished!");
            } else if (!interviewRound.getStatus().equals(InterviewRoundStatus.MATCHING)) {
                showMessage("Current interview round is not in the matching stage, can not match!");
            } else {
                menu.setScenario(new MatchInterviewScenario(menu, interviewRound));
            }
        }
    }
}
