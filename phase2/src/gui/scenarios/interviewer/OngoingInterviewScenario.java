package gui.scenarios.interviewer;

import domain.Enums.InterviewStatus;
import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.CompanyWorker;
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
    private InputInfoPanel infoPanel;

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        CompanyWorker interviewer = test.getRandomInterviewer(company);
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

    @Override
    protected void update() {
        CompanyWorker interviewer = (CompanyWorker) getUserMenu().getUser();
        leftFilter.setFilterContent(interviewer.getInterviews());
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Pass", new SetResultListener(true));
        buttonPanel.addButton("Fail", new SetResultListener(false));
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
                setOutputText(interview.detailedToStringForCompanyWorker(getMain().getStorage()));
            }
        }
    }

    class SetResultListener implements ActionListener {
        private boolean isPass;

        SetResultListener(boolean isPass) {
            this.isPass = isPass;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!confirmAction()) return;
            Interview interview = leftFilter.getSelectObject();
            if (interview != null && interview.getStatus().equals(InterviewStatus.PENDING)) {
                interview.setResult(isPass);
                interview.setRecommendation(getRecommendation());
                update();
                showMessage("Succeed!");
            } else {
                showMessage("Can not change!");
            }

        }
    }
}