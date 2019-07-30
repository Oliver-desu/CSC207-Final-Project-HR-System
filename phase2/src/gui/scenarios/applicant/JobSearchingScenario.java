package gui.scenarios.applicant;

import domain.Test;
import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.JobPool;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class JobSearchingScenario extends  Scenario{
    public JobSearchingScenario(UserMenu userMenu) {
        super(userMenu , Scenario.LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        test.addJobPostings(10, company);

        new JobSearchingScenario(new UserMenu(test.getMain(), applicant)).exampleView();
    }

    protected void initFilter() {
        JobPool jobPool = getMain().getJobPool();
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        filterPanel.setFilterContent(new ArrayList<>(jobPool.getOpenJobPostings()));
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

    protected void initButton() {
        addButton("Create Application", new CreateApplicationListener());
    }

    class CreateApplicationListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            Applicant applicant = (Applicant) getUserMenu().getUser();
            if (applicant.addApplication(jobPosting.getJobId(), new Application(applicant, jobPosting))) {
                ApplicationManageScenario scenario = new ApplicationManageScenario(getUserMenu());
                switchScenario(scenario);
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "Application already exists!");
            }
        }
    }
}

