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

public class OngoingInterviewScenario extends Scenario {

    public OngoingInterviewScenario(UserMenu userMenu) {
        super(userMenu, "Ongoing Interview Manager");
    }

    private FilterPanel<Interview> leftFilter;
    private FilterPanel<Document> rightFilter;
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

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initInput();
        initButton();
    }

    protected void initInput() {
        infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        factory.addTextArea("Recommendation:");
        add(infoPanel);
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Ongoing Interviews");
        leftFilter.addSelectionListener(new OngoingInterviewScenario.LeftFilterListener());
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Documents");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    @Override
    protected void update() {
        Employee interviewer = (Employee) getUserMenu().getUser();
        leftFilter.setFilterContent(interviewer.getInterviews());
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Pass", new SetResultListener(true));
        buttonPanel.addButton("Fail", new SetResultListener(false));
        buttonPanel.addButton("View document", new ViewDocumentListener());
        add(buttonPanel);
    }

    private String getRecommendation() {
        return infoPanel.getInfoMap().get("Recommendation:");
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Interview interview = leftFilter.getSelectObject();
            if (interview != null) {
                setOutputText(interview.detailedToStringForEmployee(getMain().getStorage()));
                rightFilter.setFilterContent(interview.getApplication().getDocumentManager().getAllDocuments());
            }
        }
    }

    class SetResultListener implements ActionListener {
        private InterviewStatus result;

        SetResultListener(boolean isPass) {
            this.result = isPass ? InterviewStatus.PASS : InterviewStatus.FAIL;
        }

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