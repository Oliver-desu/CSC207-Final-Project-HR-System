package gui.scenarios.ApplicantScenarios;

import domain.applying.Application;
import domain.applying.Interview;
import domain.filter.Filterable;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;


import java.awt.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class InterviewScenario  extends Scenario implements Filterable {
    private ArrayList<Interview> interview = new ArrayList<>();
    private  Applicant applicant; // will be delete when refactoring
    private  ArrayList<String>  StringOfInterviews = new ArrayList<>();
    public InterviewScenario(UserMenu userMenu ) {
        super(userMenu , LayoutMode.REGULAR);
        super.init();
        setsize();

        try {this.applicant = (Applicant) super.getUserMenu().getUser();
            this.interview = (ArrayList<Interview>) applicant.getOngoingInterviews();
            for(Interview interview1 :interview){
                StringOfInterviews.add(interview1.toString());

        }}
        catch (NullPointerException e){
            System.out.println("No  Applicant input!!");
            throw  e;
        }
        }




    public void setsize() {
        JFrame frame = new JFrame();
        frame.setLayout(new CardLayout());
        frame.setSize(new Dimension(1000, 600));
        frame.add(this);
        frame.setVisible(true);
        setInterviewID();

        System.out.println("2");


    }
    public void setInterviewID(){
        FilterPanel left = getFilterPanel(true);
        ArrayList interviewID = new ArrayList();
        setExample();
        for(Interview interview:this.interview ){
            interviewID.add(interview.getInterviewId());
        }
        left.setFilterContent(interviewID);

    setListener(left);}

    public  void setExample(){ // this example is error
        HashMap map = new HashMap();
        HashMap map1 = new HashMap();
        map.put("applicantId","22");
        map.put("jobPostingId","222");
        map1.put("applicantId","33");
        map1.put("jobPostingId","333");
        Application a = new Application(map);
        Application a1 = new Application(map1);
        Interview i = new Interview(a);
        Interview i1 = new Interview(a1);
        this.interview.add(i);
        this.interview.add(i1);

    }
     public void setListener(FilterPanel left){
        left.addSelectionListener(new ListSelectionListener() {
            private java.lang.String object;
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String interviewID = (String) getFilterPanel(true).getSelectObject();
                setOutputText(findInterview(interviewID).toString());
                System.out.println("33");

            }
        });


        }
    public  Interview findInterview(String objct){
        Object o = new Object();
        for (Interview interview1: this.interview
             ) {if (interview1.getInterviewId().equals(objct)){o = interview1;}

        }
        return  (Interview) o ;
    }


    @Override
    public String[] getSearchValues() {
        return new String[0];
    }

    @Override
    public String[] getHeadings() {
        return new String[0];
    }
}
