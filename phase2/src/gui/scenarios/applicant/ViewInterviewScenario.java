package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

/**
 * Class {@code  ViewInterviewScenario} viewing all interviews scenario
 *
 * @see gui.major.MenuPanel
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
     * @param userMenu given {@code UserMenu}
     */
    public ViewInterviewScenario(UserMenu userMenu) {
        super(userMenu, "View Interview");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getStorage().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new ViewInterviewScenario(new UserMenu(test.getMain(), applicant)).exampleView();

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
        // Todo: need left filter be ongoing interviews, right filter be past interviews
        Applicant applicant = (Applicant) getUserMenu().getUser();
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
