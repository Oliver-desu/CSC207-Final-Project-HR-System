package gui.scenarios.oliver;

import domain.Test;
import domain.applying.Application;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
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
    private Company company;
    private JobPosting jobPosting;

    public InterviewRoundScenario(UserMenu userMenu, InterviewRound interviewRound, Company company, JobPosting jobPosting) {
        super(userMenu, LayoutMode.REGULAR);
        this.company = company;
        this.interviewRound = interviewRound;
        this.jobPosting = jobPosting;
    }

    public static void main(String[] args) {
        Test test = new Test();

        test.addCompanies(1);
        Company company = test.getRandomCompany();
        test.addInterviewersForCompany(10, company);
        test.addJobPostingsForCompany(1, company);
        JobPosting jobPosting = test.getRandomJobPosting(company);
        test.addApplicationsForJobPosting(3, jobPosting);
        test.addNewRoundForJobPosting(jobPosting, company);

        System.out.println(jobPosting.getRemainingApplications());

        InterviewRoundScenario scenario = new InterviewRoundScenario(new UserMenu(),
                jobPosting.getCurrentInterviewRound(), company, jobPosting);
        scenario.exampleView();
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
        getFilterPanel(false).addSelectionListener(new RightFilterListener());
    }

    class LeftFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(true);
            Application application = (Application) filterPanel.getSelectObject();
            ArrayList<Object> filterContent = new ArrayList<>(application.getInterviews());
            getFilterPanel(false).setFilterContent(filterContent);
            setOutputText(application.toString());
        }
    }

    class RightFilterListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(false);
            Interview interview = (Interview) filterPanel.getSelectObject();
            if (interview != null) setOutputText(interview.toString());
        }
    }

    class HireListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FilterPanel<Object> filterPanel = getFilterPanel(true);
            Application application = (Application) filterPanel.getSelectObject();
            jobPosting.hire(application);
            System.out.println("Hire Successful");
        }
    }

    class MatchInterviewListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu menu = getUserMenu();
            if (interviewRound.getStatus().equals(InterviewRound.InterviewRoundStatus.MATCHING)) {
                menu.setScenario(new MatchInterviewScenario(menu, interviewRound, company));
            } else {
                JOptionPane.showMessageDialog(menu, "Sorry, cannot match interview now.");
            }
        }
    }
}
