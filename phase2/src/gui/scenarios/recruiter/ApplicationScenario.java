package gui.scenarios.recruiter;

import gui.general.Scenario;
import gui.general.UserMenuFrame;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;
import model.Test;
import model.job.Application;
import model.job.Document;
import model.job.JobPosting;
import model.user.Applicant;
import model.user.Company;
import model.user.Employee;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class {@code ApplicationScenario} handles with the situation where the company can view applications from the applicants.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.general.MenuPanel
 * @since 2019-08-05
 */
public class ApplicationScenario extends Scenario {

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

    /**
     * Create a new {@code ApplicationScenario} that is a {@code Scenario} with title "View Company Applications
     *
     * @param userMenuFrame the {@code userMenuFrame} that sets up the gui framework
     * @see gui.general.MenuPanel
     */

    public ApplicationScenario(UserMenuFrame userMenuFrame) {
        super(userMenuFrame, "View Company Applications");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Applicant applicant = test.addApplicant();
        Company company = test.addCompany();
        Employee recruiter = test.getRandomRecruiter(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getEmploymentCenter().getJobPostings()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }

        UserMenuFrame userMenuFrame = new UserMenuFrame(test.getMain(), recruiter);
        new ApplicationScenario(userMenuFrame).exampleView();
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
        Company company = getUserMenuFrame().getCompany();
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
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initLeftFilter()
     * @since since 2019-08-05
     */

    private class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null) {
                rightFilter.setFilterContent(application.getDocumentManager().getAllDocuments());
                setOutputText(application.detailedToStringForEmployee(getMain().getEmploymentCenter()));
            }
        }
    }

    /**
     * Class {@code ViewDocumentListener} implements ActionListener. It deals with actions happening to document.
     */
    private class ViewDocumentListener implements ActionListener {
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
