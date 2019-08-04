package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.InfoCenter;
import domain.user.Applicant;
import domain.user.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JobSearchingScenario extends Scenario {
    public JobSearchingScenario(UserMenu userMenu) {
        super(userMenu, "Job Searching");
    }

    private FilterPanel<JobPosting> leftFilter;

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

    @Override
    protected void update() {
        InfoCenter infoCenter = getMain().getInfoCenter();
        leftFilter.setFilterContent(infoCenter.getOpenJobPostings());
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Open Jobs");
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Create Application", new CreateApplicationListener());
        add(buttonPanel);
    }

    class CreateApplicationListener implements ActionListener {
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

