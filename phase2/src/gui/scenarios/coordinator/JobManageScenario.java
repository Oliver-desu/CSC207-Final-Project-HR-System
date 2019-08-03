package gui.scenarios.coordinator;

import domain.Enums.JobPostingStatus;
import domain.Test;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
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


public class JobManageScenario extends Scenario {

    public JobManageScenario(UserMenu userMenu) {
        super(userMenu);
    }

    private FilterPanel<JobPosting> leftFilter;
    private FilterPanel<InterviewRound> rightFilter;
    private InputInfoPanel infoPanel;

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        CompanyWorker coordinator = test.getRandomCoordinator(company);
        test.addJobPostings(10, company);
        for (JobPosting jobPosting : test.getInfoCenter().getJobPostings()) {
            for (Applicant applicant : test.getInfoCenter().getAllApplicants()) {
                test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
            }
            test.addNewRoundAndFinishMatching(jobPosting, company);
        }

        new JobManageScenario(new UserMenu(test.getMain(), coordinator)).exampleView();
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
        factory.addTextField("Round name:");
        add(infoPanel);
    }

    protected void initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        leftFilter.addSelectionListener(new JobManageScenario.LeftFilterListener());
        add(leftFilter);
    }

    @Override
    protected void update() {
        CompanyWorker hrCoordinator = (CompanyWorker) getUserMenu().getUser();
        leftFilter.setFilterContent(hrCoordinator.getJobPostings());
        JobPosting jobPosting = leftFilter.getSelectObject();
        if (jobPosting != null) {
            jobPosting.getInterviewRoundManager().checkStatus();
            rightFilter.setFilterContent(jobPosting.getInterviewRoundManager().getInterviewRounds());
        }
    }

    protected void initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE);
        add(rightFilter);
    }

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("View/Edit", new JobManageScenario.ViewEditListener());
        buttonPanel.addButton("Add Round", new JobManageScenario.AddRoundListener());
        buttonPanel.addButton("Next Round", new JobManageScenario.NextRoundListener());
        buttonPanel.addButton("End JobPosting", new JobManageScenario.EndJobPostingListener());
        add(buttonPanel);
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null) {
                rightFilter.setFilterContent(jobPosting.getInterviewRoundManager().getInterviewRounds());
                setOutputText(jobPosting.toString());
            }
        }
    }

    class ViewEditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null) {
                InterviewRound interviewRound = rightFilter.getSelectObject();
                if (interviewRound != null) {
                    InterviewRoundScenario interviewRoundScenario = new InterviewRoundScenario(getUserMenu(), interviewRound, jobPosting);
                    switchScenario(interviewRoundScenario);
                } else {
                    showMessage("Failed.");
                }
            }
        }
    }

    class AddRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            String roundName = infoPanel.getInfoMap().get("Round name:");
            if (jobPosting != null && jobPosting.getStatus().equals(JobPostingStatus.PROCESSING)) {
                jobPosting.getInterviewRoundManager().addInterviewRound(new InterviewRound(roundName));
                showMessage("Succeed!");
                initRightFilter();
            } else {
                showMessage("Failed!");
            }
        }
    }

    class NextRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null && jobPosting.getInterviewRoundManager().nextRound()) {
                showMessage("Succeeds!");
                initRightFilter();
            } else {
                showMessage("Failed!");
            }
        }
    }

    class EndJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null && !jobPosting.getStatus().equals(JobPostingStatus.FINISHED)) {
                jobPosting.endJobPosting();
                showMessage("The jobPosting is now closed.");
                update();
            } else {
                showMessage("Failed.");
            }
        }
    }
}
