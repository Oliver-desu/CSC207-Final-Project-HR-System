package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicationManageScenario extends Scenario {
    private Applicant applicant;

    public ApplicationManageScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
        this.applicant = (Applicant) getUserMenu().getUser();
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getJobPool().getJobPostings()) {
            test.addDraftApplicationForJobPosting(applicant, jobPosting);
        }

        new ApplicationManageScenario(new UserMenu(test.getMain(), applicant)).exampleView();
    }

    protected void initFilter() {
        initLeftFilter();
        initRightFilter();
    }

    private void initLeftFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        setLeftFilterContent();
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

    private void setLeftFilterContent() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        ArrayList<Object> applications = new ArrayList<>(this.applicant.getApplications());
        filterPanel.setFilterContent(applications);
    }

    private void initRightFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(false);
        ArrayList<Object> documents = new ArrayList<>(this.applicant.getDocumentManager().getAllDocuments());
        filterPanel.setFilterContent(documents);
    }

    protected void initButton() {
        addButton("Edit Application", new EditApplicationListener());
        addButton("Delete Application", new DeleteApplicationListener());
        addButton("Apply", new ApplyListener());
        addButton("Withdraw", new WithdrawListener());
        addButton("View Document", new ViewDocumentListener());
    }

    class ViewDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document = (Document) getFilterPanel(false).getSelectObject();
            showDocument(document.toString());
        }
    }

    class EditApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = (Application) getFilterPanel(true).getSelectObject();
            if (application != null && application.getStatus().equals(Application.ApplicationStatus.DRAFT)) {
                DocumentManageScenario documentManageScenario = new DocumentManageScenario(getUserMenu(), applicant.getDocumentManager(), application.getDocumentManager());
                switchScenario(documentManageScenario);
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "The application cannot be edited.");
            }
        }
    }

    class ApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = (Application) getFilterPanel(true).getSelectObject();
            if (application != null && application.getStatus().equals(Application.ApplicationStatus.DRAFT)) {
                if (application.apply(getMain().getJobPool(), getMain().getCompanyPool())) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Submission succeeds!");
                    setLeftFilterContent();
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "The process failed.");
                }
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "This application has been submitted.");
            }
        }
    }

    class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = (Application) getFilterPanel(true).getSelectObject();
//            conditions are checked in cancel()
            if (application != null && application.getStatus().equals(Application.ApplicationStatus.PENDING)) {
                if (application.cancel(getMain().getJobPool(), getMain().getCompanyPool())) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Withdrawal succeeds!");
                    setLeftFilterContent();
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "The process failed.");
                }
            } else if (application != null && application.getStatus().equals(Application.ApplicationStatus.DRAFT)) {
                JOptionPane.showMessageDialog(getUserMenu(), "This application has not yet been submitted.");
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "This application can no longer be canceled.");
            }
        }
    }

    class DeleteApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(true);
            Application application = (Application) filterPanel.getSelectObject();
            if (application != null && application.getStatus().equals(Application.ApplicationStatus.DRAFT)) {
                if (applicant.deleteApplication(application)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Successfully deleted!");
                    setLeftFilterContent();
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Mission failed!");
        }
    }
}
