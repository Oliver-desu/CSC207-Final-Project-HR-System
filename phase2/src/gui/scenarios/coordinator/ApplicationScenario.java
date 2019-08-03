package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class ApplicationScenario extends Scenario {

    public ApplicationScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    private FilterPanel<Application> leftFilter;
    private FilterPanel<Document> rightFilter;

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        CompanyWorker coordinator = test.getRandomCoordinator(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getInfoCenter().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }

        UserMenu userMenu = new UserMenu(test.getMain(), coordinator);
        new ApplicationScenario(userMenu).exampleView();
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        leftFilter.addSelectionListener(new LeftFilterListener());
        leftFilter.addSelectionListener(new ShowInfoListener(leftFilter));
        return leftFilter;
    }

    protected FilterPanel initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE);
        rightFilter.addSelectionListener(new ShowInfoListener(rightFilter));
        return rightFilter;
    }

    protected void updateFilterContent() {
        Company company = getUserMenu().getCompany();
        leftFilter.setFilterContent(company.getAllApplications());
        Application application = leftFilter.getSelectObject();
        ArrayList<Document> documents;
        if (application != null) {
            documents = application.getDocumentManager().getAllDocuments();
        } else {
            documents = new ArrayList<>();
        }
        rightFilter.setFilterContent(documents);
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            updateFilterContent();
        }
    }
}
