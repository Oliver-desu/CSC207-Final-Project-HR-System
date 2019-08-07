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
 * Class {@code ApplicationScenario} handles the situation where the recruiter view applications of an applicant.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.general.MenuPanel
 * @since 2019-08-05
 */
public class ApplicationScenario extends Scenario {

    /**
     * The {@code FilterPanel} that shows a list of {@code Application}s on upper left of the page.
     *
     * @see #initLeftFilter()
     * @see #update()
     * @see LeftFilterListener
     */
    private FilterPanel<Application> leftFilter;

    /**
     * The {@code FilterPanel} that shows a list of {@code Document}s in the top middle of the page.
     *
     * @see #initRightFilter()
     * @see #update()
     * @see ViewDocumentListener
     */
    private FilterPanel<Document> rightFilter;

    /**
     * Construct a new {@code ApplicationScenario} that is a {@code Scenario} with title "View Company Applications     *
     *
     * @param userMenuFrame the {@code UserMenuFrame} for the new {@code ApplicationScenario}
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
     * Override {@code initComponents} in the abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    /**
     * Initialize the {@code leftFilter} such that it shows all applications of the company.
     * It is a helper method of {@link #initComponents()}.
     */
    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "All Applications");
        leftFilter.addSelectionListener(new ApplicationScenario.LeftFilterListener());
        add(leftFilter);
    }

    /**
     * Initialize the {@code rightFilter} such that it will show all {@code Document}s of selected {@code Application}.
     * It is a helper method of {@link #initComponents()}.
     */
    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    /**
     * A helper method for {@link #initComponents()} that initializes all buttons showed on the {@code buttonPanel}.
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("View Document", new ViewDocumentListener());
        add(buttonPanel);
    }

    /**
     * Override the method {@code update()} in abstract class {@code Scenario}.
     * It updates the information showed on the user interface and is called when an {@code Application} is
     * selected/deselected on list "All Documents".
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
     * Class {@code LeftFilterListener} implements the interface {@code ListSelectionListener}.
     * It deals with the situation where an {@code Application} is selected on left {@code FilterPanel}.
     *
     * @see #initLeftFilter()
     * @since 2019-08-06
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
     * Class {@code ViewDocumentListener} implements {@code ActionListener}.
     * The content will be shown by clicking "View Document" button
     * if an document on list "Application Documents' is selected.
     *
     * @see #initButton()
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
