package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

public class ViewInterviewScenario extends Scenario {

    private FilterPanel<Interview> leftFilter;
    private FilterPanel<Interview> rightFilter;

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

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
    }

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
