package login;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class HRMenu extends Menu {
    private HumanResource humanResource;
    private static final int NUM_PAGE = 2;
    private static final Dimension ROUND_PANEL_SIZE = new Dimension(400, 150);
    private static final int ROUND_HEADING_HEIGHT = 50;

    class EditJobPosting implements ActionListener{
        private JobPosting jobPosting;

        EditJobPosting(JobPosting jobPosting){
            this.jobPosting = jobPosting;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deletePage(NUM_PAGE);
            JPanel info = newInfo();
            info.add(Tool.createDescriptionArea(exampleGetDescription()/*jobPosting.getDescription()*/));
            info.add(createRoundPanel(jobPosting));
            info.updateUI();
        }
    }

    class ViewRound implements ActionListener{
        private JobPosting jobPosting;
        private String round;

        ViewRound(JobPosting jobPosting, String round){
            this.jobPosting = jobPosting;
            this.round = round;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            deletePage(NUM_PAGE);
            String title = jobPosting.getId();
            ArrayList<Interview> interviews = exampleGetRoundInterviews(round)/*jobPosting.getRoundInterviews(round)*/;
//            boolean current = jobPosting.getCurrentRound().equals(round);
            createRoundViewPage(title, interviews, true/*current*/);
            showPage(title);
        }
    }

    public static void main(String[] args) {
        String username = "user";
        String password = "password";
        LocalDate localDate = LocalDate.now();
        Company company = new Company();
        HRMenu hrMenu = new HRMenu(new HumanResource(username, password,localDate, company));
        hrMenu.setVisible(true);
        hrMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    HRMenu(HumanResource humanResource){
        this.humanResource = humanResource;
        createApplicantSearchPage();
        createJobPostingSearchPage();
    }

    void createApplicantSearchPage(){
        ArrayList<JPanel> searchLines = new ArrayList<>();

        for (Applicant applicant: exampleGetApplicants()/*humanResource.getApplicants*/){
            searchLines.add(createSearchLine(applicant));
        }
        JPanel page = newPage("All Applicants");
        page.add(Tool.createSearchPage(searchLines));
    }

    void createJobPostingSearchPage(){
        ArrayList<JPanel> searchLines = new ArrayList<>();
        for (JobPosting jobPosting: exampleGetJobPostings()/*humanResource.getJobPostings()*/){
            searchLines.add(createSearchLine(jobPosting));
        }
        JPanel page = newPage("All JobPostings");
        page.add(Tool.createSearchPage(searchLines));
    }

    void createRoundViewPage(String title, ArrayList<Interview> interviews, boolean current){
        ArrayList<JPanel> searchLines = new ArrayList<>();
        for (Interview interview: interviews){
            searchLines.add(createSearchLine(interview));
        }
        JPanel page = newPage(title);
        page.add(Tool.createSearchPage(searchLines));
        page.add(Tool.createButton("Back", null));
        page.add(Tool.createTextLabel("Interviewer: "));
        page.add(Tool.createTextField());
        if (current){
            page.add(Tool.createButton("Next Round", null));
        }
    }

    JPanel createSearchLine(Applicant applicant){
        JButton button = Tool.createSearchButton("view", null);
        JPanel buttons = Tool.createSearchButtonsArea(button);
        JPanel info = Tool.createInfoLine(applicant);
        return Tool.createSearchLine(buttons, info);
    }

    JPanel createSearchLine(JobPosting jobPosting){
        JPanel buttons;
        JButton button1 = Tool.createSearchButton("view", null);
        if (!jobPosting.isClosed() && !jobPosting.isFilled()) {
            JButton button2 = Tool.createSearchButton("edit", new EditJobPosting(jobPosting));
            buttons = Tool.createSearchButtonsArea(button1, button2);
        }else{
            buttons = Tool.createSearchButtonsArea(button1);
        }
        JPanel info = Tool.createInfoLine(jobPosting);
        return Tool.createSearchLine(buttons, info);
    }

    JPanel createSearchLine(Interview interview){
        JPanel buttons;
        if (true/*interview.isEmpty()*/){
            JButton button = Tool.createSearchButton("match", null);
            buttons = Tool.createSearchButtonsArea(button);
        }else {
            JButton button = Tool.createSearchButton("view", null);
            buttons = Tool.createSearchButtonsArea(button);
        }
        JPanel info = Tool.createInfoLine(interview);
        return Tool.createSearchLine(buttons, info);
    }

    JPanel createRoundPanel(JobPosting jobPosting){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(ROUND_PANEL_SIZE);
        jPanel.add(Tool.createTextLabel("Interview Rounds:", ROUND_PANEL_SIZE.width, ROUND_HEADING_HEIGHT));
        for (String round: exampleGetRounds()/*jobPosting.getRounds()*/){
            jPanel.add(Tool.createButton(round, new ViewRound(jobPosting, round)));
        }
        return jPanel;
    }

    // Todo: Below methods are all temporary.

    public static ArrayList<String> exampleGetSearchValues(SearchObject searchObject){
        String string = "value";

        if(searchObject instanceof Interview ){
            string = "interview";
        }else if (searchObject instanceof JobPosting){
            string = "jobPosting";
        }
        ArrayList<String> strings = new ArrayList<>();
        strings.add(string);
        strings.add(string);
        strings.add(string);
        return strings;
    }

    public ArrayList<Applicant> exampleGetApplicants(){
        ArrayList<Applicant> list = new ArrayList<>();
        Applicant x = new Applicant("user", "pass", LocalDate.now());
        for (int i = 0; i<30; i++) {
            list.add(x);
        }
        return list;
    }
    public ArrayList<String> exampleGetRounds(){
        ArrayList<String> rounds = new ArrayList<>();
        rounds.add("Interview1");
        rounds.add("Interview2");
        rounds.add("Interview3");
        rounds.add("Interview4");
        return rounds;
    }

    public ArrayList<JobPosting> exampleGetJobPostings(){
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            jobPostings.add(exampleJobPosting());
        }
        return jobPostings;
    }

    public String exampleGetDescription(){
        return "This is a description.";
    }

    public ArrayList<Interview> exampleGetRoundInterviews(String round){
        ArrayList<Interview> interviews = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            interviews.add(exampleInterview());
        }
        return interviews;
    }

    public Interview exampleInterview(){
        return new Interview(LocalDate.now(),"loc", 30.0,exampleJobPosting(),exampleInterviewer(),
                exampleApplication(),"round");
    }

    public JobPosting exampleJobPosting(){
        ArrayList<String> requirements = new ArrayList<>();
        requirements.add("requirement1");
        return new JobPosting(new Company(),LocalDate.now(),LocalDate.now(),requirements,"job id");
    }

    public Interviewer exampleInterviewer(){
        return new Interviewer("user", "pass", LocalDate.now(), "name", new Company());
    }

    public Application exampleApplication(){
        return new Application(exampleJobPosting(),exampleApplicant());
    }

    public Applicant exampleApplicant(){
        return new Applicant("username","pass",LocalDate.now());
    }

}
