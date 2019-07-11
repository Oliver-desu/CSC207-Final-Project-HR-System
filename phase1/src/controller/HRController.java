package controller;

import domain.Application;
import domain.Company;
import domain.JobPosting;
import domain.JobPostingManager;
import views.ViewFrame;
import views.components.ButtonPanel;
import views.components.ComboBoxPanel;
import views.components.ViewList;
import views.panels.MainPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HRController {
    private ViewFrame view;
    private MainPanel mainPanel;
    private Company company;
    private JobPostingManager manager;

    public HRController(ViewFrame view, MainPanel mainPanel, Company company, JobPostingManager manager) {
        this.view = view;
        this.mainPanel = mainPanel;
        this.company = company;
        this.manager = manager;
        setup();
    }

    private void setup() {
        view.setAllVisibility(true);
        view.setMenuType(ViewList.Type.HR);
        mainPanel.setAllVisibility(true);
        mainPanel.setPartVisibility(MainPanel.Part.RIGHT_LIST, false);
        mainPanel.setPartVisibility(MainPanel.Part.BOX_PANEL, false);
        mainPanel.setButtonView(ButtonPanel.View.HR);

        view.addListSelectionListener(new ChangeContent());
        mainPanel.addListSelectionListener(true, new ChangeInfo());
        view.addActionListener("Post Job", new PostJob());
        view.addActionListener("View Applicant", new ViewApplicant());
    }


    class ChangeContent implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String key = view.getMenuSelectedValue();
            ArrayList<JobPosting> postings = company.findJobPostings(key);
            ArrayList<String> keys = new ArrayList<>();
            for (JobPosting posting : postings) {
                keys.add(posting.getId());
            }
            String[] content = new String[keys.size()];
            keys.toArray(content);
            mainPanel.setListContent(true, content);
        }
    }

    class ChangeInfo implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            String id = mainPanel.getListSelectedValue(true);
            JobPosting jobPosting = manager.findJobPosting(id);
            mainPanel.setInfo(jobPosting.toString());
        }
    }

    class PostJob implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.setAllVisibility(false);
            mainPanel.setPartVisibility(MainPanel.Part.BOX_PANEL, true);
        }
    }

    class ViewApplicant implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainPanel.setAllVisibility(true);
            mainPanel.setBoxView(ComboBoxPanel.View.HR);
            String id = mainPanel.getListSelectedValue(true);
            JobPosting jobPosting = manager.findJobPosting(id);
            ArrayList<Application> applications = jobPosting.getRemainingApplications();
            ArrayList<String> keys = new ArrayList<>();
            for (Application application : applications) {
                keys.add(application.getHeading());
            }
            String[] content = new String[keys.size()];
            keys.toArray(content);
            mainPanel.setListContent(true, content);
        }
    }

}
