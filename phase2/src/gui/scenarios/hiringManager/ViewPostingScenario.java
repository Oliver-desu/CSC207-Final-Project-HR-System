package gui.scenarios.hiringManager;

import domain.Enums.UserType;
import domain.Test;
import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * Class {@code ViewPostingScenario} handles the situation where the hiring manager want to view job postings in the company.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see #initLeftFilter()
 * @see #initRightFilter()
 * @see gui.major.MenuPanel
 * @since 2019-08-05
 */
public class ViewPostingScenario extends Scenario {

    /**
     * The {@code FilterPanel} located leftmost in the interface. It contains a list of {@code JobPosting}s.
     *
     * @see #initLeftFilter()
     * @see #update()
     * @see LeftFilterListener
     */
    private FilterPanel<JobPosting> leftFilter;

    /**
     * The {@code FilterPanel} located middle in this interface. It contains a list of {@code Application}s.
     *
     * @see #initRightFilter()
     * @see #update()
     * @see LeftFilterListener
     * @see RightFilterListener
     */
    private FilterPanel<Application> rightFilter;

    /**
     * Create a new {@code ViewPostingScenario} that is a {@code Scenario} with title "View Company Job Postings".
     *
     * @param userMenu the {@code userMenu} that sets up the gui framework
     * @see gui.major.MenuPanel
     */
    public ViewPostingScenario(UserMenu userMenu) {
        super(userMenu, "View Company Job Postings");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        Employee hiringManager = test.getStorage().getEmployee(company.getHiringManagerId(), UserType.HIRING_MANAGER);
        test.addJobPostings(10, company);

        UserMenu userMenu = new UserMenu(test.getMain(), hiringManager);
        new ViewPostingScenario(userMenu).exampleView();
    }

    /**
     * Override method {@code initComponents} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     *
     * @see #initComponents()
     */
    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "All JobPostings");
        leftFilter.addSelectionListener(new ViewPostingScenario.LeftFilterListener());
        add(leftFilter);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     *
     * @see #initComponents()
     */
    private void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "JobPosting Applications");
        rightFilter.addSelectionListener(new ViewPostingScenario.RightFilterListener());
        add(rightFilter);
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */
    @Override
    protected void update() {
        Company company = getUserMenu().getCompany();
        Storage Storage = getMain().getStorage();
        ArrayList<JobPosting> jobPostings = Storage.getJobPostingsByIds(company.getJobPostingIds());
        leftFilter.setFilterContent(jobPostings);
        JobPosting jobPosting = leftFilter.getSelectObject();
        if (jobPosting != null) {
            rightFilter.setFilterContent(jobPosting.getApplications());
        }
    }

    /**
     * Class{@code LeftFilterListener} implements ListSelectionListener.
     * It deals with actions happening on left filter panel.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since 2019-08-05
     */
    private class LeftFilterListener implements ListSelectionListener {

        /**
         * Override {@code valueChanged} in interface {@code ListSelectionListener}.
         *
         * @param e the action event of selecting from a list
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null) {
                rightFilter.setFilterContent(jobPosting.getApplications());
                setOutputText(jobPosting.toString());
            }
        }
    }

    /**
     * Class{@code RightFilterListener} implements ListSelectionListener.
     * It deals with actions happening on right filter panel.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initRightFilter()
     * @since 2019-08-05
     */
    private class RightFilterListener implements ListSelectionListener {

        /**
         * Override {@code ListSelectionEvent} in interface {@code ListSelectionListener}.
         *
         * @param e the action event of selecting from a list
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = rightFilter.getSelectObject();
            if (application != null) {
                setOutputText(application.detailedToStringForEmployee(getMain().getStorage()));
            }
        }
    }

}
