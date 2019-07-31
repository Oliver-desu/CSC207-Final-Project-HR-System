package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.HRCoordinator;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InterviewRoundScenario extends Scenario {

    private InterviewRound interviewRound;
    private JobPosting jobPosting;

    public InterviewRoundScenario(UserMenu userMenu, InterviewRound interviewRound, JobPosting jobPosting) {
        super(userMenu, LayoutMode.REGULAR);
        this.interviewRound = interviewRound;
        this.jobPosting = jobPosting;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        HRCoordinator coordinator = test.getRandomCoordinator(company);
        JobPosting jobPosting = test.getRandomJobPosting(test.getRandomCompany());
        for (Applicant applicant : test.getUserPool().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        new InterviewRoundScenario(new UserMenu(test.getMain(), coordinator), jobPosting.getCurrentInterviewRound(), jobPosting).exampleView();

    }

    @Override
    public void init() {
        super.init();
        initButtonPanel();
        initLeftFilter();
        initRightFilter();
    }

    private void initButtonPanel() {
        addButton("Match Interview", new MatchInterviewListener());
        addButton("Hire", new HireListener());
    }

    private void initLeftFilter() {
        ArrayList<Object> filterContent = new ArrayList<>(interviewRound.getApplicationMap().values());
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        filterPanel.setFilterContent(filterContent);
        filterPanel.addSelectionListener(new LeftFilterListener());
    }

    private void initRightFilter() {
        FilterPanel<Object> rightFilter = getFilterPanel(false);
        rightFilter.addSelectionListener(new ShowInfoListener(rightFilter));
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(true);
            Application application = (Application) filterPanel.getSelectObject();
            if (application != null) {
                ArrayList<Object> filterContent = new ArrayList<>(application.getInterviews());
                getFilterPanel(false).setFilterContent(filterContent);
                setOutputText(application.toString());
            }
        }
    }

    class HireListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (interviewRound == jobPosting.getCurrentInterviewRound()) {
                FilterPanel<Object> filterPanel = getFilterPanel(true);
                Application application = (Application) filterPanel.getSelectObject();
                if (application != null && jobPosting.hire(application)) {
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
            if (jobPosting.isProcessing()) {
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
