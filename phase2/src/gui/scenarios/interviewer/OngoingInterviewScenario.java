package gui.scenarios.interviewer;

import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.Interviewer;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OngoingInterviewScenario extends Scenario {

    public OngoingInterviewScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        Interviewer interviewer = test.getRandomInterviewer(company);
        JobPosting jobPosting = test.getRandomJobPosting(company);

        for (Applicant applicant : test.getUserPool().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        UserMenu userMenu = new UserMenu(test.getMain(), interviewer);
        new OngoingInterviewScenario(userMenu).exampleView();
    }

    protected void initInput() {
        getInputInfoPanel().addTextField("Give recommendation:");
    }

    protected void initFilter() {
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        setFilterContent(filterPanel);
        filterPanel.addSelectionListener(new FilterListener());
    }

    private void setFilterContent(FilterPanel<Object> filterPanel) {
        Interviewer interviewer = (Interviewer) getUserMenu().getUser();
        filterPanel.setFilterContent(new ArrayList<>(interviewer.getUpcomingInterviews()));
    }

    protected void initButton() {
        addButton("Pass", new SetResultListener(true));
        addButton("Fail", new SetResultListener(false));
    }

    class FilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Interview interview = (Interview) getFilterPanel(true).getSelectObject();
            setOutputText(interview.toString());
        }
    }

    class SetResultListener implements ActionListener {
        private boolean isPass;

        SetResultListener(boolean isPass) {
            this.isPass = isPass;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(getUserMenu(), "Are you sure?");
            if (confirm == 0) {
                FilterPanel<Object> filterPanel = getFilterPanel(true);
                Interview interview = (Interview) filterPanel.getSelectObject();
                interview.setResult(isPass);
                setFilterContent(filterPanel);
            }
        }
    }
}