package gui.scenarios.applicant;

import domain.Enums.ApplicationStatus;
import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationManageScenario extends Scenario {
    private Applicant applicant;
    private FilterPanel<Application> leftFilter;
    private FilterPanel<Document> rightFilter;

    public ApplicationManageScenario(UserMenu userMenu) {
        super(userMenu);
        this.applicant = (Applicant) getUserMenu().getUser();
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getInfoCenter().getJobPostings()) {
            test.addDraftApplicationForJobPosting(applicant, jobPosting);
        }

        new ApplicationManageScenario(new UserMenu(test.getMain(), applicant)).exampleView();
    }

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        leftFilter.addSelectionListener(new ShowInfoListener(leftFilter));
        add(leftFilter);
    }

    @Override
    protected void update() {
        leftFilter.setFilterContent(applicant.getApplications());
        rightFilter.setFilterContent(applicant.getDocumentManager().getAllDocuments());
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE);
        add(rightFilter);
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Edit Application", new EditApplicationListener());
        buttonPanel.addButton("Delete Application", new DeleteApplicationListener());
        buttonPanel.addButton("Apply", new ApplyListener());
        buttonPanel.addButton("Withdraw", new WithdrawListener());
        buttonPanel.addButton("View Document", new ViewDocumentListener());
        add(buttonPanel);
    }

    class ViewDocumentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Document document = rightFilter.getSelectObject();
            if (document != null) {
                showDocument(document.toString());
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "No document selected.");
            }
        }
    }

    class EditApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                DocumentManageScenario scenario = new DocumentManageScenario(getUserMenu(),
                        application.getDocumentManager());
                switchScenario(scenario);
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "The application cannot be edited.");
            }
        }
    }

    class ApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                if (application.apply(getMain().getInfoCenter())) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Submission succeeds!");
                    update();
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
            Application application = leftFilter.getSelectObject();
//            conditions are checked in cancel()
            if (application != null && application.getStatus().equals(ApplicationStatus.PENDING)) {
                if (application.cancel(getMain().getInfoCenter())) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Withdrawal succeeds!");
                    update();
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "The process failed.");
                }
            } else if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                JOptionPane.showMessageDialog(getUserMenu(), "This application has not yet been submitted.");
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "This application can no longer be canceled.");
            }
        }
    }

    class DeleteApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                if (applicant.deleteApplication(application)) {
                    JOptionPane.showMessageDialog(getUserMenu(), "Successfully deleted!");
                    update();
                    return;
                }
            }
            JOptionPane.showMessageDialog(getUserMenu(), "Mission failed!");
        }
    }
}
