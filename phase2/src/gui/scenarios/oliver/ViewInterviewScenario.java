package gui.scenarios.oliver;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class ViewInterviewScenario extends Scenario {

    public ViewInterviewScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getJobPool().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new ViewInterviewScenario(new UserMenu(test.getMain(), applicant)).exampleView();

    }

    protected void initFilter() {
        Applicant applicant = (Applicant) getUserMenu().getUser();
        ArrayList<Object> contentList = new ArrayList<>(applicant.getOngoingInterviews());
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        leftFilterPanel.setFilterContent(contentList);
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
    }
}
