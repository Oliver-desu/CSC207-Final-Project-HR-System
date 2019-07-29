package gui.scenarios.coordinator;

import domain.job.InterviewRound;
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

import domain.job.JobPosting;


public class JobManageScenario extends Scenario {

    public JobManageScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGULAR);
    }

    protected void initInput() {
        getInputInfoPanel().addTextField("Round name:");
    }

    protected void initFilter() {
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        FilterPanel<Object> rightFilterPanel = getFilterPanel(false);
        HRCoordinator hrCoordinator = (HRCoordinator) getUserMenu().getUser();
        ArrayList<Object> jobPostings = new ArrayList<>(hrCoordinator.getJobPostings());
        leftFilterPanel.setFilterContent(jobPostings);
        leftFilterPanel.addSelectionListener(new JobManageScenario.LeftFilterListener());
        JobPosting jobPosting = (JobPosting) leftFilterPanel.getSelectObject();
        ArrayList<Object> interviewRounds = new ArrayList<>(jobPosting.getAllInterviewRounds());
        rightFilterPanel.setFilterContent(interviewRounds);
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
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            ArrayList<Object> interviewRounds = new ArrayList<>(jobPosting.getAllInterviewRounds());
            getFilterPanel(false).setFilterContent(interviewRounds);
            setOutputText(jobPosting.toString());
        }
    }

    class ViewEditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            InterviewRound interviewRound = (InterviewRound) getFilterPanel(false).getSelectObject();
            InterviewRoundScenario interviewRoundScenario = new InterviewRoundScenario(getUserMenu(), interviewRound, jobPosting);
            switchScenario(interviewRoundScenario);
        }
    }

    class AddRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            String roundName = getInputInfoMap().get("Round name:");
            InterviewRound interviewRound = new InterviewRound(roundName);
            jobPosting.addInterviewRound(interviewRound);
            JOptionPane.showMessageDialog(getUserMenu(), "Succeeds!");

        }
    }

    class NextRoundListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            jobPosting.nextRound();
            JOptionPane.showMessageDialog(getUserMenu(), "Succeeds!");
        }
    }

    class EndJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JobPosting jobPosting = (JobPosting) getFilterPanel(true).getSelectObject();
            jobPosting.endJobPosting();
            JOptionPane.showMessageDialog(getUserMenu(), "The jobPosting is now closed.");
        }
    }
}
