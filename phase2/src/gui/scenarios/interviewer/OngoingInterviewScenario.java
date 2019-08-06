package gui.scenarios.interviewer;

import domain.Enums.InterviewStatus;
import domain.Test;
import domain.applying.Document;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * todo
 *
 * @see #initLeftFilter()
 */
public class OngoingInterviewScenario extends Scenario {

    /**
     * todo
     *
     * @param userMenu
     * @see gui.major.MenuPanel
     */
    public OngoingInterviewScenario(UserMenu userMenu) {
        super(userMenu, "Ongoing Interview Manager");
    }

    /**
     * The {@code FilterPanel} located leftmost in the interface. It contains a list of {@code Interview}s.
     *
     * @see #initLeftFilter()
     * @see #update()
     * @see LeftFilterListener
     * @see SetResultListener
     */
    private FilterPanel<Interview> leftFilter;
    /**
     * The {@code FilterPanel} located at middle in the interface. It contains a list of {@code Document}s.
     *
     * @see #initRightFilter()
     * @see LeftFilterListener
     * @see ViewDocumentListener
     */
    private FilterPanel<Document> rightFilter;
    /**
     * The {@code InputInfoPanel} that allows the interviewer to type recommendation.
     *
     * @see #initInput()
     * @see #getRecommendation()
     */
    private InputInfoPanel infoPanel;

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        Employee interviewer = test.getRandomInterviewer(company);
        JobPosting jobPosting = test.getRandomJobPosting(company);

        for (Applicant applicant : test.getStorage().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        UserMenu userMenu = new UserMenu(test.getMain(), interviewer);
        new OngoingInterviewScenario(userMenu).exampleView();
    }

    /**
     * Override {@code initComponents} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initInput();
        initButton();
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code infoPanel}.
     *
     * @see #initComponents()
     */
    private void initInput() {
        infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        factory.addTextArea("Recommendation:");
        add(infoPanel);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code leftFilter}.
     * This panel shows a list of ongoing interviews.
     *
     * @see #initComponents()
     */
    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Ongoing Interviews");
        leftFilter.addSelectionListener(new OngoingInterviewScenario.LeftFilterListener());
        add(leftFilter);
    }

    /**
     * A helper method for {@code initComponents()} that initializes {@code rightFilter}.
     * This panel show a list of documents when choose an interview in the left.
     *
     * @see #initComponents()
     */
    private void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     *
     * @see SetResultListener#actionPerformed(ActionEvent)
     */
    @Override
    protected void update() {
        Employee interviewer = (Employee) getUserMenu().getUser();
        leftFilter.setFilterContent(interviewer.getInterviews());
    }

    /**
     * A helper method for {@code initComponents()} that initializes and adds the new {@code ButtonPanel}.
     *
     * @see #initComponents()
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Pass", new SetResultListener(true));
        buttonPanel.addButton("Fail", new SetResultListener(false));
        buttonPanel.addButton("View document", new ViewDocumentListener());
        add(buttonPanel);
    }

    /**
     * A helper function for {@code actionPerformed(ActionEvent)} in setResultListener that gets the recommendation
     * written in the input panel.
     *
     * @return a string that contains the recommendation which an interviewer typed on the screen
     * @see SetResultListener#actionPerformed(ActionEvent)
     */
    private String getRecommendation() {
        return infoPanel.getInfoMap().get("Recommendation:");
    }

    /**
     * Class{@code LeftFilterListener} implements ListSelectionListener. It deals with actions happening on left filter panel.
     *
     * @see #initLeftFilter()
     */
    class LeftFilterListener implements ListSelectionListener {
        /**
         * Override {@code valueChanged} in interface {@code ListSelectionListener}.
         *
         * @param e the action event of selecting from a list
         */
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Interview interview = leftFilter.getSelectObject();
            if (interview != null) {
                setOutputText(interview.detailedToStringForEmployee(getMain().getStorage()));
                rightFilter.setFilterContent(interview.getApplication().getDocumentManager().getAllDocuments());
            }
        }
    }

    /**
     * Class{@code SetResultListener} implements ActionListener.
     * It deals with the situation where the button "Pass" or the button "Fail" is clicked.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-06
     */
    class SetResultListener implements ActionListener {
        private InterviewStatus result;

        /**
         * //todo
         *
         * @param isPass
         */
        SetResultListener(boolean isPass) {
            this.result = isPass ? InterviewStatus.PASS : InterviewStatus.FAIL;
        }

        /**
         * //todo
         *
         * @param e the action event of clicking a button
         * @see #update()
         * @see #getRecommendation()
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!confirmAction()) return;
            Interview interview = leftFilter.getSelectObject();
            if (interview != null && interview.getStatus().equals(InterviewStatus.PENDING)) {
                interview.setStatus(result);
                interview.setRecommendation(getRecommendation());
                update();
                showMessage("Succeed!");
            } else {
                showMessage("Can not change!");
            }

        }
    }

    /**
     * Class{@code ViewDocumentListener} implements ActionListener.
     * It handles an occasion when the button "View document" is clicked.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-06
     */
    class ViewDocumentListener implements ActionListener {

        /**
         * Override {@code actionPerformed} in interface {@code ActionListener}.
         *
         * @param e the action event of clicking on a button
         */
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