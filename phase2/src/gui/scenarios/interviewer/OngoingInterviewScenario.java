package gui.scenarios.interviewer;

import domain.Test;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ComponentFactory;
import gui.panels.FilterPanel;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OngoingInterviewScenario extends Scenario {

    public OngoingInterviewScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    private FilterPanel<Interview> leftFilter;

    public static void main(String[] args) {
        Test test = new Test();
        test.addApplicants(10);
        Company company = test.addCompany();
        CompanyWorker interviewer = test.getRandomInterviewer(company);
        JobPosting jobPosting = test.getRandomJobPosting(company);

        for (Applicant applicant : test.getInfoCenter().getAllApplicants()) {
            test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);

        UserMenu userMenu = new UserMenu(test.getMain(), interviewer);
        new OngoingInterviewScenario(userMenu).exampleView();
    }

    protected InputInfoPanel initInput() {
        InputInfoPanel infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        factory.addTextField("Recommendation:");
        return infoPanel;
    }

    protected FilterPanel initLeftFilter() {
        leftFilter = new FilterPanel<>(LIST_SIZE);
        leftFilter.addSelectionListener(new ShowInfoListener(leftFilter));
        return leftFilter;
    }

    protected void updateFilterContent() {
        CompanyWorker interviewer = (CompanyWorker) getUserMenu().getUser();
        leftFilter.setFilterContent(interviewer.getInterviews());

    }

    protected void initButton() {
        addButton("Pass", new SetResultListener(true));
        addButton("Fail", new SetResultListener(false));
    }

    class SetResultListener implements ActionListener {
        private boolean isPass;

        SetResultListener(boolean isPass) {
            this.isPass = isPass;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (JOptionPane.showConfirmDialog(getUserMenu(), "Are you sure?") != 0) return;
            Interview interview = leftFilter.getSelectObject();
            if (interview != null && interview.getStatus().equals(Interview.InterviewStatus.PENDING)) {
                interview.setResult(isPass);
                updateFilterContent();
                JOptionPane.showMessageDialog(getUserMenu(), "Succeed!");
            } else {
                JOptionPane.showMessageDialog(getUserMenu(), "Can not change!");
            }

        }
    }
}