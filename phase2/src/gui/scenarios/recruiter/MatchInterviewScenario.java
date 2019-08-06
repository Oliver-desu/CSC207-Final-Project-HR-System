package gui.scenarios.recruiter;

import domain.Enums.InterviewStatus;
import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *  Class {@code MatchInterviewScenario} handles the situation of matching interview.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.MenuPanel
 * @since 2019-08-05
 */
public class MatchInterviewScenario extends Scenario {

    /**
     * An {@code InterviewRound}
     *
     * @see #update()
     * @see MatchListener
     */
    private InterviewRound interviewRound;

    /**
     * The {@code LeftFilterPanel} in this scenario.
     *
     * @see #initComponents()
     * @see #initLeftFilter()
     * @see #update()
     * @see MatchListener
     */
    private FilterPanel<Application> leftFilter;

    /**
     * The {@code RightFilterPanel} in this scenario.
     *
     * @see #update()
     * @see #initRightFilter()
     * @see MatchListener
     */
    private FilterPanel<Employee> rightFilter;

    /**
     * Create a new {@code MatchInterviewScenario} that is a {@code Scenario} with title "Match Interview"
     * @param userMenu the {@code userMenu} that sets up the gui framework
     * @param interviewRound Interview Rounds being matched
     */
    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound) {
        super(userMenu, "Match Interview");
        this.interviewRound = interviewRound;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        test.addInterviewersForCompany(9, company);
        Employee recruiter = test.getRandomRecruiter(company);
        JobPosting jobPosting = test.addJobPosting(company);
        for (Applicant applicant : test.getStorage().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        InterviewRound interviewRound = test.addNewRound(jobPosting);

        new MatchInterviewScenario(new UserMenu(test.getMain(), recruiter), interviewRound).exampleView();
    }

    /**
     * Override {@code initComponents} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * A helper method for {@code initComponents()} that initializes and add the new {@code ButtonPanel}
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Match", new MatchListener());
        add(buttonPanel);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     *
     * @see #initComponents()
     */

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Unmatched Applications");
        add(leftFilter);
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */

    @Override
    protected void update() {
        leftFilter.setFilterContent(interviewRound.getUnmatchedApplications());
        Storage Storage = getMain().getStorage();
        Company company = getUserMenu().getCompany();
        ArrayList<Employee> interviewers = Storage.getInterviewers(company.getInterviewerIds());
        rightFilter.setFilterContent(interviewers);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     *
     * @see #initComponents()
     */
    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Company Interviewers");
        add(rightFilter);
    }

    /**
     * Class{@code MatchListener} implements ActionListener. It deals with actions related to matching interviews.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since 2019-08-05
     */
    class MatchListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * 1 if {@code Application} is not null ,then get the {@code Interview} of this Application ,
         * 2 if interviews is unmatched then match these interview with interviewer and add them to the application
         * 3  show a massage "succeed!" and update GUI.
         * 4 otherwise show a massage""There is no interview to match!""
         *
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Employee interviewer = rightFilter.getSelectObject();
            Application application = leftFilter.getSelectObject();
            Interview interview;
            if (application != null) {
                interview = application.getInterviewByRound(interviewRound.getRoundName());
                if (interview.getStatus().equals(InterviewStatus.UNMATCHED)) {
                    interview.match(interviewer);
                    application.addInterview(interviewRound.getRoundName(), interview);
                    showMessage("Succeed!");
                    update();
                } else {
                    showMessage("Failed!");
                }
            } else {
                showMessage("There is no interview to match!");
            }
        }
    }
}
