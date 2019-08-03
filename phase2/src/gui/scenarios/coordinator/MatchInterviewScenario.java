package gui.scenarios.coordinator;

import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.InfoCenter;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MatchInterviewScenario extends Scenario {

    private InterviewRound interviewRound;
    private FilterPanel<Application> leftFilter;
    private FilterPanel<CompanyWorker> rightFilter;

    public MatchInterviewScenario(UserMenu userMenu, InterviewRound interviewRound) {
        super(userMenu, LayoutMode.REGULAR);
        this.interviewRound = interviewRound;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        test.addInterviewersForCompany(9, company);
        CompanyWorker coordinator = test.getRandomCoordinator(company);
        JobPosting jobPosting = test.addJobPosting(company);
        for (Applicant applicant : test.getInfoCenter().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        InterviewRound interviewRound = test.addNewRound(jobPosting);

        new MatchInterviewScenario(new UserMenu(test.getMain(), coordinator), interviewRound).exampleView();
    }

    protected void initButton() {
        addButton("Match", new MatchListener());
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        return leftFilter;
    }

    protected void updateFilterContent() {
        leftFilter.setFilterContent(interviewRound.getUnmatchedApplications());
        InfoCenter infoCenter = getMain().getInfoCenter();
        Company company = getUserMenu().getCompany();
        ArrayList<CompanyWorker> interviewers = infoCenter.getInterviewers(company.getInterviewerIds());
        rightFilter.setFilterContent(interviewers);
    }

    protected FilterPanel initRightFilter() {
        rightFilter = new FilterPanel<>(LIST_SIZE);
        return rightFilter;
    }

    class MatchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CompanyWorker interviewer = rightFilter.getSelectObject();
            Application application = leftFilter.getSelectObject();
            Interview interview;
            if (application != null) {
                interview = application.getInterviewByRound(interviewRound.getRoundName());
                if (interview.getStatus().equals(Interview.InterviewStatus.UNMATCHED)) {
                    interview.match(interviewer);
                    application.addInterview(interviewRound.getRoundName(), interview);
                    JOptionPane.showMessageDialog(getUserMenu(), "Succeed!");
                    updateFilterContent();
                } else {
                    JOptionPane.showMessageDialog(getUserMenu(), "Failed!");
                }
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "There is no interview to match!");
            }
        }
    }
}
