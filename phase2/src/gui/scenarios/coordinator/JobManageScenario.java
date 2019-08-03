package gui.scenarios.coordinator;

import domain.Test;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class JobManageScenario extends Scenario {

    public JobManageScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    private FilterPanel<JobPosting> leftFilter;
    private FilterPanel<InterviewRound> rightFilter;

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

    protected InputInfoPanel initInput() {
        InputInfoPanel infoPanel = new InputInfoPanel();
        infoPanel.setup(REGULAR_INPUT_SIZE, false);
        infoPanel.addTextField("Round name:");
        return infoPanel;
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>();
        leftFilter.addSelectionListener(new JobManageScenario.LeftFilterListener());
        return leftFilter;
    }

    protected void updateFilterContent() {
        CompanyWorker hrCoordinator = (CompanyWorker) getUserMenu().getUser();
        leftFilter.setFilterContent(hrCoordinator.getJobPostings());
        JobPosting jobPosting = leftFilter.getSelectObject();
        if (jobPosting != null) {
            jobPosting.getInterviewRoundManager().checkStatus();
            rightFilter.setFilterContent(jobPosting.getInterviewRoundManager().getInterviewRounds());
        }
    }

    protected FilterPanel initRightFilter() {
        rightFilter = new FilterPanel<>();
        return rightFilter;
    }

    protected void initButton() {
        addButton("View/Edit", new JobManageScenario.ViewEditListener());
        addButton("Add Round", new JobManageScenario.AddRoundListener());
        addButton("Next Round", new JobManageScenario.NextRoundListener());
        addButton("End JobPosting", new JobManageScenario.EndJobPostingListener());

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
                    JOptionPane.showMessageDialog(getUserMenu(), "Failed.");
                }
            }
        }
    }

    class AddRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            String roundName = getInputInfoMap().get("Round name:");
            if (jobPosting != null && jobPosting.getStatus().equals(JobPosting.JobPostingStatus.PROCESSING)) {
                jobPosting.getInterviewRoundManager().addInterviewRound(new InterviewRound(roundName));
                JOptionPane.showMessageDialog(getUserMenu(), "Succeed!");
                initRightFilter();
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "Failed!");
            }
        }
    }

    class NextRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null && jobPosting.getInterviewRoundManager().nextRound()) {
                JOptionPane.showMessageDialog(getUserMenu(), "Succeeds!");
                initRightFilter();
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "Failed!");
            }
        }
    }

    class EndJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = leftFilter.getSelectObject();
            if (jobPosting != null && !jobPosting.getStatus().equals(JobPosting.JobPostingStatus.FINISHED)) {
                jobPosting.endJobPosting();
                JOptionPane.showMessageDialog(getUserMenu(), "The jobPosting is now closed.");
                updateFilterContent();
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "Failed.");
            }
        }
    }
}
