package gui.scenarios.generalist;

import domain.Enums.UserType;
import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.InfoCenter;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class ViewPostingScenario extends Scenario {

    private FilterPanel<JobPosting> leftFilter;

    public ViewPostingScenario(UserMenu userMenu) {
        super(userMenu);
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
        initOutputInfoPanel();
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        addShowInfoListenerFor(leftFilter);
        add(leftFilter);
    }

    @Override
    protected void update() {
        Company company = getUserMenu().getCompany();
        InfoCenter infoCenter = getMain().getInfoCenter();
        ArrayList<JobPosting> jobPostings = infoCenter.getJobPostingsByIds(company.getJobPostingIds());
        leftFilter.setFilterContent(jobPostings);
    }
}
