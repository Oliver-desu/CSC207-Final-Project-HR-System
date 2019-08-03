package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

public class ViewInterviewScenario extends Scenario {

    public ViewInterviewScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    private FilterPanel<Interview> leftFilter;

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getInfoCenter().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new ViewInterviewScenario(new UserMenu(test.getMain(), applicant)).exampleView();

    }

    @Override
    protected FilterPanel initLeftFilter() {
        Applicant applicant = (Applicant) getUserMenu().getUser();
        leftFilter = new FilterPanel<>();
        leftFilter.setFilterContent(applicant.getOngoingInterviews());
        leftFilter.addSelectionListener(new ShowInfoListener(leftFilter));
        return leftFilter;
    }
}
