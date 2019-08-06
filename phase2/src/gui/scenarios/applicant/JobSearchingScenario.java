package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code  JobSearchingScenario} the page used for Job searching
 *
 * @see gui.major.MenuPanel
 * @since 2019-08-06
 */
public class JobSearchingScenario extends Scenario {
    /**
     * the {@code FilterPanel} on the left stored the {@code JobPosting}
     */
    private FilterPanel<JobPosting> leftFilter;

    /**
     * constructor for JobSearchingScenario,
     * @param userMenu given {@code UserMenu}
     */
    public JobSearchingScenario(UserMenu userMenu) {
        super(userMenu, "Job Searching");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);

        new JobSearchingScenario(new UserMenu(test.getMain(), applicant)).exampleView();
    }

    @Override
    protected void initComponents() {
        initLeftFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * override the method in parent class  {@code scenario}
     *update the content on the leftFilterPanel  by get the latest version of JobPosting from {@code Storage}
     */
    @Override
    protected void update() {
        Storage Storage = getMain().getStorage();
        leftFilter.setFilterContent(Storage.getOpenJobPostings());
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Open Jobs");
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    /**
     * Initial a button  called "Create Application" with  new {@code CreateApplicationListener},
     * then add this button to the ButtonPanel
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Create Application", new CreateApplicationListener());
        add(buttonPanel);
    }

    /**
     * Class {@code  CreateApplicationListener} listener used for creat a new application
     * @see #initButton()
     * @since 2019-08-06
     */
    class CreateApplicationListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * 1 get the {@code JobPosting} selected by user .2 get the {@code Applicant } stored in the
         * {@code UserMenu} 3  if the {@code JobPosing} is not null the add it to the {@code Applicant}
         * then switch to the {@code ApplicationManageScenario}
         * otherwise show a massage "Failed!".
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            Applicant applicant = (Applicant) getUserMenu().getUser();
            if (jobPosting != null &&
                    applicant.addApplication(jobPosting.getJobId(), new Application(applicant, jobPosting))) {
                ApplicationManageScenario scenario = new ApplicationManageScenario(getUserMenu());
                switchScenario(scenario);
            } else {
                showMessage("Failed!");
            }
        }
    }
}

