package gui.scenarios.applicant;

import gui.general.Scenario;
import gui.general.UserMenuFrame;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import model.Test;
import model.exceptions.ApplicationAlreadyExistsException;
import model.job.Application;
import model.job.JobPosting;
import model.storage.EmploymentCenter;
import model.user.Applicant;
import model.user.Company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class {@code  JobSearchingScenario} the page used for Job searching
 *
 * @see gui.general.MenuPanel
 * @since 2019-08-06
 */
public class JobSearchingScenario extends Scenario {
    /**
     * the {@code FilterPanel} on the left stored the {@code JobPosting}
     */
    private FilterPanel<JobPosting> leftFilter;

    /**
     * constructor for JobSearchingScenario,
     * @param userMenuFrame given {@code UserMenuFrame}
     */
    public JobSearchingScenario(UserMenuFrame userMenuFrame) {
        super(userMenuFrame, "Job Searching");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);

        new JobSearchingScenario(new UserMenuFrame(test.getMain(), applicant)).exampleView();
    }

    @Override
    protected void initComponents() {
        initLeftFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * override the method in parent class  {@code scenario}
     *update the content on the leftFilterPanel  by get the latest version of JobPosting from {@code EmploymentCenter}
     */
    @Override
    protected void update() {
        EmploymentCenter EmploymentCenter = getMain().getEmploymentCenter();
        leftFilter.setFilterContent(EmploymentCenter.getOpenJobPostings());
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
     * Class {@code  CreateApplicationListener} listener used for create a new application
     * @see #initButton()
     * @since 2019-08-06
     */
    private class CreateApplicationListener implements ActionListener {
        /**
         * override the method in interface {@code ActionListener}
         * 1 get the {@code JobPosting} selected by user .2 get the {@code Applicant } stored in the
         * {@code UserMenuFrame} 3  if the {@code JobPosing} is not null the add it to the {@code Applicant}
         * then switch to the {@code ApplicationManageScenario}
         * otherwise interfaces a massage "Failed!".
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            Applicant applicant = (Applicant) getUserMenuFrame().getUser();
            try {
                applicant.addApplication(jobPosting.getJobId(), new Application(applicant, jobPosting));
                ApplicationManageScenario scenario = new ApplicationManageScenario(getUserMenuFrame());
                switchScenario(scenario);
            } catch (NullPointerException e1) {
                showMessage("No job posting selected!");
            } catch (ApplicationAlreadyExistsException e1) {
                showMessage(e1.getMessage());
            }
        }
    }
}

