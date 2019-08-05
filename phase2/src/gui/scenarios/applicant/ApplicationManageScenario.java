package gui.scenarios.applicant;

import domain.Enums.ApplicationStatus;
import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationManageScenario extends Scenario {
    private Applicant applicant;
    private FilterPanel<Application> leftFilter;
    private FilterPanel<Document> rightFilter;

    public ApplicationManageScenario(UserMenu userMenu) {
        super(userMenu, "Application Manager");
        this.applicant = (Applicant) getUserMenu().getUser();
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getStorage().getJobPostings()) {
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
        leftFilter = new FilterPanel<>(LIST_SIZE, "My Applications");
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    @Override
    protected void update() {
        leftFilter.setFilterContent(applicant.getApplications());
        rightFilter.setFilterContent(applicant.getDocumentManager().getAllDocuments());
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
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
                showDocument(document);
            } else {
                showMessage("No document selected.");
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
                showMessage("The application cannot be edited.");
            }
        }
    }

    class ApplyListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                if (application.apply(getMain().getStorage())) {
                    showMessage("Submission succeeds!");
                    update();
                } else {
                    showMessage("The process failed.");
                }
            } else {
                showMessage("This application has been submitted.");
            }
        }
    }

    class WithdrawListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
//            conditions are checked in cancel()
            if (application != null && application.getStatus().equals(ApplicationStatus.PENDING)) {
                if (application.cancel(getMain().getStorage())) {
                    showMessage("Withdrawal succeeds!");
                    update();
                } else {
                    showMessage("The process failed.");
                }
            } else if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                showMessage("This application has not yet been submitted.");
            } else {
                showMessage("This application can no longer be canceled.");
            }
        }
    }

    class DeleteApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null && application.getStatus().equals(ApplicationStatus.DRAFT)) {
                if (applicant.deleteApplication(application)) {
                    showMessage("Successfully deleted!");
                    update();
                    return;
                }
            }
            showMessage("Mission failed!");
        }
    }
}
