package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.HRCoordinator;
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

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        HRCoordinator coordinator = test.getRandomCoordinator(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getUserPool().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }

        UserMenu userMenu = new UserMenu(test.getMain(), coordinator);
        new ApplicationScenario(userMenu).exampleView();
    }

    @Override
    protected void initFilter() {
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        setLeftFilterContent(leftFilterPanel);
        setRightFilterContent(leftFilterPanel, rightFilterPanel);
        leftFilterPanel.addSelectionListener(new LeftFilterListener());
        leftFilterPanel.addSelectionListener(new ShowInfoListener(leftFilterPanel));
        rightFilterPanel.addSelectionListener(new ShowInfoListener(rightFilterPanel));
    }

    private void setLeftFilterContent(FilterPanel<Object> leftFilterPanel) {
        Company company = getUserMenu().getCompany();
        leftFilterPanel.setFilterContent(new ArrayList<>(company.getAllApplications()));
    }

    private void setRightFilterContent(FilterPanel<Object> leftFilterPanel, FilterPanel<Object> rightFilterPanel) {
        Application application = (Application) leftFilterPanel.getSelectObject();
        ArrayList<Document> documents;
        if (application != null) {
            documents = application.getDocumentManager().getAllDocuments();
        } else {
            documents = new ArrayList<>();
        }
        rightFilterPanel.setFilterContent(new ArrayList<>(documents));
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            setRightFilterContent(getFilterPanel(true), getFilterPanel(false));
        }
    }


}
