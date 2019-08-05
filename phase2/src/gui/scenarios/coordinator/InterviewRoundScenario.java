package gui.scenarios.coordinator;

import domain.Enums.InterviewRoundStatus;
import domain.Enums.JobPostingStatus;
import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterviewRoundScenario extends Scenario {

    private InterviewRound interviewRound;
    private InterviewRoundManager manager;
    private FilterPanel<Application> leftFilter;
    private FilterPanel<Interview> rightFilter;

    public InterviewRoundScenario(UserMenu userMenu, InterviewRound interviewRound, JobPosting jobPosting) {
        super(userMenu, "Interview Round Manager");
        this.interviewRound = interviewRound;
        this.manager = jobPosting.getInterviewRoundManager();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        CompanyWorker coordinator = test.getRandomCoordinator(company);
        JobPosting jobPosting = test.getRandomJobPosting(test.getRandomCompany());
        for (Applicant applicant : test.getStorage().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        new InterviewRoundScenario(new UserMenu(test.getMain(), coordinator), jobPosting.getInterviewRoundManager().getCurrentInterviewRound(), jobPosting).exampleView();

    }

    @Override
    protected void initComponents() {
        initLeftFilter();
        initRightFilter();
        initOutputInfoPanel();
        initButton();
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Match Interview", new MatchInterviewListener());
        buttonPanel.addButton("Hire", new HireListener());
        add(buttonPanel);
    }

    @Override
    protected void update() {
        leftFilter.setFilterContent(interviewRound.getCurrentRoundApplications());
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE, "Remaining Applications");
        leftFilter.addSelectionListener(new LeftFilterListener());
        add(leftFilter);
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE, "Application Interviews");
        addShowInfoListenerFor(rightFilter);
        add(rightFilter);
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Application application = leftFilter.getSelectObject();
            if (application != null) {
                rightFilter.setFilterContent(application.getInterviews());
                setOutputText(application.toString());
            }
        }
    }

    class HireListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (interviewRound == manager.getCurrentInterviewRound()) {
                Application application = leftFilter.getSelectObject();
                if (application != null && manager.hire(application)) {
                    showMessage("Successfully hired!");
                    initLeftFilter();
                } else {
                    showMessage("Can not hire this applicant!");
                }
            } else {
                showMessage("JobPosting already finished!");
            }
        }
    }

    class MatchInterviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu menu = getUserMenu();
            if (manager.getJobPosting().getStatus().equals(JobPostingStatus.PROCESSING)) {
                if (interviewRound.getStatus().equals(InterviewRoundStatus.MATCHING)) {
                    menu.setScenario(new MatchInterviewScenario(menu, interviewRound));
                } else {
                    showMessage("Sorry, cannot match interview now.");
                }
            } else {
                showMessage("JobPosting already finished!");
            }
        }
    }
}
