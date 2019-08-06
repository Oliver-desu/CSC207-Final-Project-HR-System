package gui.scenarios.recruiter;

import domain.Test;
import domain.applying.Application;
import domain.applying.Document;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class {@code ApplicationScenario} handles with the situation where the company can view applications from the applicants.
 *
 * @author group 0120 of CSC207 summer 2019
 * @since 2019-08-05
 */
public class ApplicationScenario extends Scenario {

    /**
     * Create a new {@code ApplicationScenario} that is a {@code Scenario} with title "View Company Applications
     * @param userMenu the {@code userMenu} that sets up the gui framework
     * @see gui.major.MenuPanel
     */

    public ApplicationScenario(UserMenu userMenu) {
        super(userMenu, "View Company Applications");
    }

    /**
     * The {@code LeftFilterPanel} in this scenario
     *
     * @see #initLeftFilter()
     * @see #update()
     * @see LeftFilterListener
     */
    private FilterPanel<Application> leftFilter;
    /**
     * The {@code RightFilterPanel} in this scenario
     *
     * @see #initRightFilter()
     * @see #update()
     * @see ViewDocumentListener
     */
    private FilterPanel<Document> rightFilter;

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        Employee recruiter = test.getRandomRecruiter(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getStorage().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }

        UserMenu userMenu = new UserMenu(test.getMain(), recruiter);
        new ApplicationScenario(userMenu).exampleView();
    }

    /**
     * Override {@code initComponents} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     *
     * @see #initComponents()
     */
    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "All Applications");
        leftFilter.addSelectionListener(new ApplicationScenario.LeftFilterListener());
        add(leftFilter);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     *
     * @see #initComponents()
     */

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    /**
     * A helper method for {@code initComponents()}.
     *
     * @see #initComponents()
     */

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("View Document", new ViewDocumentListener());
        add(buttonPanel);
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */

    @Override
    protected void update() {
        Company company = getUserMenu().getCompany();
        leftFilter.setFilterContent(company.getAllApplications());
        Application application = leftFilter.getSelectObject();
        ArrayList<Document> documents;
        if (application != null) {
            documents = application.getDocumentManager().getAllDocuments();
        } else {
            documents = new ArrayList<>();
        }
        rightFilter.setFilterContent(documents);
    }

    /**
     * Class{@code LeftFilterListener} implements ListSelectionListener. It deals with actions happening on left filter panel.
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since since 2019-08-05
     */

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null) {
                rightFilter.setFilterContent(application.getDocumentManager().getAllDocuments());
                setOutputText(application.detailedToStringForEmployee(getMain().getStorage()));
            }
        }
    }

    /**
     * Class {@code ViewDocumentListener} implements ActionListener. It deals with actions happening to document.
     */
    class ViewDocumentListener implements ActionListener {
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

}
