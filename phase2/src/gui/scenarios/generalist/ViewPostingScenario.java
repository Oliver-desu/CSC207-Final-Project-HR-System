package gui.scenarios.generalist;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.InfoCenter;
import domain.user.CompanyWorker;
import domain.user.User;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class ViewPostingScenario extends Scenario {

    public ViewPostingScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        CompanyWorker generalist = test.getInfoCenter().getCompanyWorker(company.getHRGeneralistId(), User.UserType.HR_GENERALIST);
        test.addJobPostings(10, company);

        UserMenu userMenu = new UserMenu(test.getMain(), generalist);
        new ViewPostingScenario(userMenu).exampleView();
    }

    @Override
    protected void initFilter() {
        Company company = getUserMenu().getCompany();
        InfoCenter infoCenter = getMain().getInfoCenter();
        ArrayList<JobPosting> jobPostings = infoCenter.getJobPostingsByIds(company.getJobPostingIds());
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        filterPanel.setFilterContent(new ArrayList<>(jobPostings));
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

}
