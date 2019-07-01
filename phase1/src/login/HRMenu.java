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

    HRMenu(HumanResource humanResource) {
        super(NUM_PAGE);
        this.humanResource = humanResource;
        createApplicantSearchPage();
        createJobPostingSearchPage();
    }

    static ArrayList<String> exampleGetSearchValues(SearchObject searchObject) {
        String string = "value";

        if (searchObject instanceof Interview) {
            string = "interview";
        } else if (searchObject instanceof JobPosting) {
            string = "jobPosting";
        }
        ArrayList<String> strings = new ArrayList<>();
        strings.add(string);
        strings.add(string);
        strings.add(string);
        return strings;
    }

    public static void main(String[] args) {
        String username = "user";
        String password = "password";
        LocalDate localDate = LocalDate.now();
        Company company = new Company();
        HRMenu hrMenu = new HRMenu(new HumanResource(username, password, localDate, company));
        hrMenu.setVisible(true);
        hrMenu.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createApplicantSearchPage() {
        ArrayList<JPanel> searchLines = new ArrayList<>();

        for (Applicant applicant : exampleGetApplicants()/*humanResource.getApplicants*/) {
            searchLines.add(createSearchLine(applicant));
        }
        JPanel page = new JPanel();
        page.add(Tool.createSearchPage(searchLines));
        addPage("All Applicants", page);
        showPage("All Applicants");
    }

    private void createJobPostingSearchPage() {
        ArrayList<JPanel> searchLines = new ArrayList<>();
        for (JobPosting jobPosting : exampleGetJobPostings()/*humanResource.getJobPostings()*/) {
            searchLines.add(createSearchLine(jobPosting));
        }
        JPanel page = new JPanel();
        page.add(Tool.createSearchPage(searchLines));
        addPage("All Postings", page);
        showPage("All Postings");
    }

    private void createRoundViewPage(String title, ArrayList<Interview> interviews, boolean current) {
        ArrayList<JPanel> searchLines = new ArrayList<>();
        JTextField interviewer = Tool.createTextField();
        for (Interview interview: interviews){
            searchLines.add(createSearchLine(interview, interviewer));
        }
        JPanel page = new JPanel();
        page.add(Tool.createSearchPage(searchLines));
        page.add(Tool.createButton("Back", new ReturnToInfo((JPanel) infoPanel.getComponent(0))));
        page.add(Tool.createTextLabel("Interviewer: "));
        page.add(interviewer);
        if (current){
            page.add(Tool.createButton("Next Round", null));
        }
        addPage(title, page);
        showPage(title);
    }

    private JPanel createSearchLine(Applicant applicant) {
        JButton button = Tool.createSearchButton("view", new ViewDescription("No Description"/* applicant.getDescription*/));
        JPanel buttons = Tool.createSearchButtonsArea(button);
        JPanel info = Tool.createInfoLine(applicant);
        return Tool.createSearchLine(buttons, info);
    }

    private JPanel createSearchLine(JobPosting jobPosting) {
        JPanel buttons;
        JButton button1 = Tool.createSearchButton("view", new ViewDescription("No Description"/* jobPosting.getDescription*/));
        if (!jobPosting.isClosed() && !jobPosting.isFilled()) {
            JButton button2 = Tool.createSearchButton("edit", new EditJobPosting(jobPosting));
            buttons = Tool.createSearchButtonsArea(button1, button2);
        }else{
            buttons = Tool.createSearchButtonsArea(button1);
        }
        JPanel info = Tool.createInfoLine(jobPosting);
        return Tool.createSearchLine(buttons, info);
    }

    private JPanel createSearchLine(Interview interview, JTextField interviewer) {
        JPanel buttons;
        if (true/*interview.isEmpty()*/){
            MatchInterviewer match = new MatchInterviewer(interview, exampleInterviewer()/*humanResource.getInterviewer(interviewer.getText())*/);
            JButton button = Tool.createSearchButton("match", match);
            match.setButton(button);
            buttons = Tool.createSearchButtonsArea(button);
        }else {
            JButton button = Tool.createSearchButton("view", new ViewDescription("No Description"/* interview.getDescription*/));
            buttons = Tool.createSearchButtonsArea(button);
        }
        JPanel info = Tool.createInfoLine(interview);
        return Tool.createSearchLine(buttons, info);
    }

    private JPanel createRoundPanel(JobPosting jobPosting) {
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(ROUND_PANEL_SIZE);
        jPanel.add(Tool.createTextLabel("Interview Rounds:", ROUND_PANEL_SIZE.width, ROUND_HEADING_HEIGHT));
        for (String round: exampleGetRounds()/*jobPosting.getRounds()*/){
            jPanel.add(Tool.createButton(round, new ViewRound(jobPosting, round)));
        }
        return jPanel;
    }

    @Override
    JPanel createDefaultInfo() {
        JPanel info = new JPanel();
        String text = "Welcome to HRMenu!\n\n\n" +
                "Click 'All Applicant' to see all applicant who apply to your company.\n" +
                "   Each applicant is available to see its details.\n\n" +
                "Click 'All JobPostings' to see all job postings in your company.\n" +
                "   Ongoing job posting is available for edit.\n" +
                "   For each ongoing job posting you can see its interviews in each round and start a new round.\n" +
                "   To match a interview, you should go to current round.\n" +
                "   To start a new round, you should make sure all interviews in current round is matched and completed.";
        info.add(Tool.createDescriptionArea(text));
        return info;
    }

    private ArrayList<Applicant> exampleGetApplicants() {
        ArrayList<Applicant> list = new ArrayList<>();
        Applicant x = new Applicant("user", "pass", LocalDate.now());
        for (int i = 0; i<30; i++) {
            list.add(x);
        }
        return list;
    }

    private ArrayList<String> exampleGetRounds() {
        ArrayList<String> rounds = new ArrayList<>();
        rounds.add("Interview1");
        rounds.add("Interview2");
        rounds.add("Interview3");
        rounds.add("Interview4");
        return rounds;
    }

    // Todo: Below methods are all temporary.

    private ArrayList<JobPosting> exampleGetJobPostings() {
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            jobPostings.add(exampleJobPosting());
        }
        return jobPostings;
    }

    private String exampleGetDescription() {
        return "This is a description.";
    }

    private ArrayList<Interview> exampleGetRoundInterviews(String round) {
        ArrayList<Interview> interviews = new ArrayList<>();
        for (int i = 0; i<30; i++) {
            interviews.add(exampleInterview());
        }
        return interviews;
    }

    private Interview exampleInterview() {
        return new Interview(LocalDate.now(),"loc", 30.0,exampleJobPosting(),exampleInterviewer(),
                exampleApplication(),"round");
    }

    private JobPosting exampleJobPosting() {
        ArrayList<String> requirements = new ArrayList<>();
        requirements.add("requirement1");
        return new JobPosting(new Company(), LocalDate.now(), LocalDate.now(), requirements, "job id");
    }

    private Interviewer exampleInterviewer() {
        return new Interviewer("user", "pass", LocalDate.now(), "name", new Company());
    }

    private Application exampleApplication() {
        return new Application(exampleJobPosting(),exampleApplicant());
    }

    private Applicant exampleApplicant() {
        return new Applicant("username", "pass", LocalDate.now());
    }

    class EditJobPosting implements ActionListener {
        private JobPosting jobPosting;

        EditJobPosting(JobPosting jobPosting) {
            this.jobPosting = jobPosting;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deletePage();
            JPanel info = new JPanel();
            info.add(Tool.createDescriptionArea(exampleGetDescription()/*jobPosting.getDescription()*/));
            info.add(createRoundPanel(jobPosting));
            changeInfo(info);
        }
    }

    class ViewRound implements ActionListener {
        private JobPosting jobPosting;
        private String round;

        ViewRound(JobPosting jobPosting, String round) {
            this.jobPosting = jobPosting;
            this.round = round;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deletePage();
            String title = jobPosting.getId();
            ArrayList<Interview> interviews = exampleGetRoundInterviews(round)/*jobPosting.getRoundInterviews(round)*/;
//            boolean current = jobPosting.getCurrentRound().equals(round);
            createRoundViewPage(title, interviews, true/*current*/);
            showPage(title);
        }
    }

    class MatchInterviewer implements ActionListener {
        private JButton button;
        private Interview interview;
        private Interviewer interviewer;

        MatchInterviewer(Interview interview, Interviewer interviewer) {
            this.interview = interview;
            this.interviewer = interviewer;
        }

        private void setButton(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            /* interview.matchInterviewer(interviewer);*/
            button.setText("view");
            button.removeActionListener(this);
            button.addActionListener(new ViewDescription("just matched"/*interview.getDescription()*/, false));
            button.doClick();
        }
    }

}
