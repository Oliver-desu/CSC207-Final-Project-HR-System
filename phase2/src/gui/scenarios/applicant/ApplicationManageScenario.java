package gui.scenarios.applicant;

import domain.Enums.ApplicationStatus;
import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.MenuPanel;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code  ApplicationManageScenario} setup the scenario use for managing application of an applicant
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.MenuPanel
 * @see gui.scenarios.applicant
 * @since 2019-08-06
 */
public class ApplicationManageScenario extends Scenario {
    /**
     * which {@code Applicant} need to be managing he application
     */
    private Applicant applicant;
    /**
     * the {@code FilterPanel}at the leftmost of the page
     */
    private FilterPanel<Application> leftFilter;
    /**
     * the {@code FilterPanel} at the middle of the page
     */
    private FilterPanel<Document> rightFilter;

    /**
     * constructor of {@code ApplicationManageScenario}
     *
     * @param userMenu the {@code UserMenu} given
     * @see MenuPanel
     * @see gui.scenarios.applicant.ApplicationManageScenario
     */
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

    /**
     * override the method in the parent class
     * initial the components fo this page include LeftFilter,RightFilter,OutputInfoPanel nad BUttonPanel
     */
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

    /**
     * override the method in parent class
     * add all {@code Applications} to the leftFilterPanel , and add all {@code Document} of this {@code Applicant}
     * to the rightFilterPanel
     *
     * @see Scenario#init()
     */
    @Override
    protected void update() {
        leftFilter.setFilterContent(applicant.getApplications());
        rightFilter.setFilterContent(applicant.getDocumentManager().getAllDocuments());
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        add(rightFilter);
    }

    /**
     * initial five buttons , "Edit Application" button with new EditApplicationListener ,
     * "Delete Application" button with  new DeleteApplicationListener,
     * "Apply" button with new ApplyListener,
     * "Withdraw" button with new WithdrawListener,
     * "View Document" button with new ViewDocumentListener. then add them to the buttonPanel
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Edit Application", new EditApplicationListener());
        buttonPanel.addButton("Delete Application", new DeleteApplicationListener());
        buttonPanel.addButton("Apply", new ApplyListener());
        buttonPanel.addButton("Withdraw", new WithdrawListener());
        buttonPanel.addButton("View Document", new ViewDocumentListener());
        add(buttonPanel);
    }

    /**
     * Class {@code ViewDocumentListener } the listener used for show the document which have been selected
     * @see #initButton()
     * @since 2019-08-06
     */
    class ViewDocumentListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * show the document which have been  selected
         * @param e ActionEvent
         */
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

    /**
     * Class {@code  EditApplicationListener} listener used for edit the {@code Application }
     * @see #initButton()
     * @since 2019-08-06
     */
    class EditApplicationListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * edit the selected {@code Application } if the {@code Application} is {@code ApplicationStatus.DRAFT} ,
         * otherwise show a message with text "The application cannot be edited."
         * @param e ActionEvent
         */
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

    /**
     * Class {@code  ApplyListener} Listener used for apply for a jobPosting
     * @see  #initButton()
     * @since 2019-08-06
     */
    class ApplyListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         *  submit the Application if the {@code ApplicationStatus} is {@code ApplicationStatus.DRAFT} ,
         *  and then update the page , otherwise show a massage with text "This application has been submitted."
         * @param e ActionEvent
         */
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

    /**
     * Class {@code   WithdrawListener} Listener used for withdraw the {@code Application}
     * @see  #initButton()
     * @since 2019-08-06
     */
    class WithdrawListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         *  if {@code Application} is not null and {@code ApplicationStatus} is {@code ApplicationStatus.PENDING},
         *  withdraw this {@code Application}  and  show a massage "Withdrawal succeeds!" , then update the GUI,
         *  if {@code Application} is not null and {@code ApplicationStatus} is {@code ApplicationStatus.DRAFT},
         *    show a massage "This application has not yet been submitted.",
         *    otherwise  show a massage "This application can no longer be canceled."
         * @param e ActionEvent
         */
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

    /**
     * Class {@code   DeleteApplicationListener}
     * @see #initButton()
     * @since 2019-08-06
     */
    class DeleteApplicationListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * delete {@code Application} if  {@code ApplicationStatus} is {@code ApplicationStatus.DRAFT},
         * then show a massage "Successfully deleted!" and update GUI,
         * otherwise show a massage "Mission failed!".
         * @param e ActionEvent
         */
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
