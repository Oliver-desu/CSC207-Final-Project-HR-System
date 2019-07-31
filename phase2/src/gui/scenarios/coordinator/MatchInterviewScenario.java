package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.Info;
import domain.storage.UserPool;
import domain.user.Applicant;
import domain.user.HRCoordinator;
import domain.user.Interviewer;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class MatchInterviewScenario extends Scenario {

    private InterviewRound interviewRound;

    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound) {
        super(userMenu, LayoutMode.REGULAR);
        this.interviewRound = interviewRound;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        test.addInterviewersForCompany(9, company);
        HRCoordinator coordinator = test.getRandomCoordinator(company);
        JobPosting jobPosting = test.addJobPosting(company);
        for (Applicant applicant : test.getUserPool().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        InterviewRound interviewRound = test.addNewRound(jobPosting);

        new MatchInterviewScenario(new UserMenu(test.getMain(), coordinator), interviewRound).exampleView();
    }

    protected void initInput() {
        getInputInfoPanel().addTextField("Dead line:");
    }

    protected void initButton() {
        addButton("Match", new MatchListener());
    }

    protected void initFilter() {
        initLeftFilter();
        initRightFilter();
    }

    private void initLeftFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        ArrayList<Object> applications = new ArrayList<>(interviewRound.getUnmatchedApplications());
        filterPanel.setFilterContent(applications);
    }

    private void initRightFilter() {
        UserPool userPool = getMain().getUserPool();
        Company company = getUserMenu().getCompany();
        ArrayList<Interviewer> interviewers = userPool.getInterviewers(company.getInterviewerIds());
        FilterPanel<Object> filterPanel = getFilterPanel(false);
        filterPanel.setFilterContent(new ArrayList<>(interviewers));
    }

    class MatchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            HashMap<String, String> infoMap = getInputInfoMap();
            Interviewer interviewer = (Interviewer) getFilterPanel(false).getSelectObject();

            Application application = (Application) getFilterPanel(true).getSelectObject();
            Interview interview;
            if (application != null) {
                interview = application.getInterviewByRound(interviewRound.getRoundName());
                if (interview.getStatus().equals(Interview.InterviewStatus.UNMATCHED)) {
                    Info interviewInfo = new Info(interview, infoMap);
                    interview.match(interviewer, interviewInfo);
                    application.addInterview(interviewRound.getRoundName(), interview);
                    JOptionPane.showMessageDialog(getUserMenu(), "Succeed!");
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "Failed!");
                }
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "There is no interview to match!");
            }
        }
    }
}
