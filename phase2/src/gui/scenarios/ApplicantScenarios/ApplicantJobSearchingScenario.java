package gui.scenarios.ApplicantScenarios;

import domain.applying.Application;
import domain.applying.Interview;
import domain.job.JobPosting;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ApplicantJobSearchingScenario extends Scenario {
    private  ArrayList<JobPosting> Jobpostings = new ArrayList<JobPosting>();

    protected ApplicantJobSearchingScenario(UserMenu userMenu, LayoutMode mode) {
        super(userMenu, mode);
        super.init();
        Applicant applicant = (Applicant) userMenu.getUser();

    }

    private ArrayList<Interview> interview = new ArrayList<>();
    private Applicant applicant; // will be delete when refactoring
    private ArrayList<String> StringOfInterviews = new ArrayList<>();


    public void setsize() {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(this);
        frame.setVisible(true);

        System.out.println("2");


    }


}