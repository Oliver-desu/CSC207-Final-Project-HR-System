package gui.scenarios.generalist;

import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.JobPool;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;

public class ViewPostingScenario extends Scenario {

    public ViewPostingScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    @Override
    protected void initFilter() {
        Company company = getUserMenu().getCompany();
        JobPool jobPool = getMain().getJobPool();
        ArrayList<JobPosting> jobPostings = jobPool.getJobPostingsByIds(company.getJobPostingIds());
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        filterPanel.setFilterContent(new ArrayList<>(jobPostings));
        filterPanel.addSelectionListener(new ShowInfoListener(filterPanel));
    }

}
