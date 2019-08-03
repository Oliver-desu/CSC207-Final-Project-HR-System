package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
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
        super(userMenu, LayoutMode.REGULAR);
        this.interviewRound = interviewRound;
        this.manager = jobPosting.getInterviewRoundManager();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        CompanyWorker coordinator = test.getRandomCoordinator(company);
        JobPosting jobPosting = test.getRandomJobPosting(test.getRandomCompany());
        for (Applicant applicant : test.getInfoCenter().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        new InterviewRoundScenario(new UserMenu(test.getMain(), coordinator), jobPosting.getInterviewRoundManager().getCurrentInterviewRound(), jobPosting).exampleView();

    }

    protected void initButton() {
        addButton("Match Interview", new MatchInterviewListener());
        addButton("Hire", new HireListener());
    }

    protected void updateFilterContent() {
        leftFilter.setFilterContent(interviewRound.getCurrentRoundApplications());
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>();
        leftFilter.addSelectionListener(new LeftFilterListener());
        return leftFilter;
    }

    protected FilterPanel initRightFilter() {
        rightFilter = new FilterPanel<>();
        rightFilter.addSelectionListener(new ShowInfoListener(rightFilter));
        return rightFilter;
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
                    JOptionPane.showMessageDialog(getUserMenu(), "Successfully hired!");
                    initLeftFilter();
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "Can not hire this applicant!");
                }
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "JobPosting already finished!");
            }
        }
    }

    class MatchInterviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu menu = getUserMenu();
            if (manager.getJobPosting().isProcessing()) {
                if (interviewRound.getStatus().equals(InterviewRound.InterviewRoundStatus.MATCHING)) {
                    menu.setScenario(new MatchInterviewScenario(menu, interviewRound));
                } else {
                    JOptionPane.showMessageDialog(menu, "Sorry, cannot match interview now.");
                }
            } else {
                JOptionPane.showMessageDialog(menu, "JobPosting already finished!");
            }
        }
    }
}
