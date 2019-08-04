package gui.scenarios.generalist;

import domain.Enums.UserType;
import domain.Test;
import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.InfoCenter;
import domain.user.Company;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

public class ViewPostingScenario extends Scenario {

    private FilterPanel<JobPosting> leftFilter;
    private FilterPanel<Application> rightFilter;

    public ViewPostingScenario(UserMenu userMenu) {
        super(userMenu, "View Company Job Postings");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        CompanyWorker generalist = test.getInfoCenter().getCompanyWorker(company.getHRGeneralistId(), UserType.HR_GENERALIST);
        test.addJobPostings(10, company);

        UserMenu userMenu = new UserMenu(test.getMain(), generalist);
        new ViewPostingScenario(userMenu).exampleView();
    }

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "All JobPostings");
        leftFilter.addSelectionListener(new ViewPostingScenario.LeftFilterListener());
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "JobPosting Applications");
        rightFilter.addSelectionListener(new ViewPostingScenario.RightFilterListener());
        add(rightFilter);
    }

    @Override
    protected void update() {
        Company company = getUserMenu().getCompany();
        InfoCenter infoCenter = getMain().getInfoCenter();
        ArrayList<JobPosting> jobPostings = infoCenter.getJobPostingsByIds(company.getJobPostingIds());
        leftFilter.setFilterContent(jobPostings);
        JobPosting jobPosting = leftFilter.getSelectObject();
        if (jobPosting != null) {
            rightFilter.setFilterContent(jobPosting.getApplications());
        }
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null) {
                rightFilter.setFilterContent(jobPosting.getApplications());
                setOutputText(jobPosting.toString());
            }
        }
    }

    class RightFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = rightFilter.getSelectObject();
            if (application != null) {
                setOutputText(application.detailedToStringForCompanyWorker(getMain().getInfoCenter()));
            }
        }
    }

}
