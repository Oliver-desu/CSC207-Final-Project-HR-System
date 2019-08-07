package gui.scenarios.applicant;

import gui.general.Scenario;
import gui.general.UserMenuFrame;
import gui.panels.FilterPanel;
import model.Test;
import model.job.Interview;
import model.job.JobPosting;
import model.user.Applicant;
import model.user.Company;

/**
 * Class {@code  ViewInterviewScenario} viewing all interviews scenario
 *
 * @see gui.general.MenuPanel
 * @since 2019-08-06
 */
public class ViewInterviewScenario extends Scenario {
    /**
     * the {@code FilterPanel} on the left stored the {@code Interview}
     */
    private FilterPanel<Interview> leftFilter;
    /**
     * the {@code FilterPanel} in the middle  stored the {@code Interview}
     */
    private FilterPanel<Interview> rightFilter;

    /**
     * constructor for a new {@code ViewInterviewScenario}
     *
     * @param userMenuFrame given {@code UserMenuFrame}
     */
    public ViewInterviewScenario(UserMenuFrame userMenuFrame) {
        super(userMenuFrame, "View Interview");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getEmploymentCenter().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new ViewInterviewScenario(new UserMenuFrame(test.getMain(), applicant)).exampleView();

    }

    /**
     * override the method in parent class  {@code Scenario}
     * initial the leftFilter , right Filter and ButtonPanel.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
    }

    /**
     * override the method in parent class  {@code Scenario}
     * set the content in the leftFilterPanel  to be the OngoingInterviews of the {@code Applicant}
     * set the content in the rightFilterPanel  to be the PastInterviews of the {@code Applicant}
     */
    @Override
    protected void update() {
        Applicant applicant = (Applicant) getUserMenuFrame().getUser();
        leftFilter.setFilterContent(applicant.getOngoingInterviews());
        rightFilter.setFilterContent(applicant.getPastInterviews());
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "My Ongoing Interviews");
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "My Past Interviews");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }
}
