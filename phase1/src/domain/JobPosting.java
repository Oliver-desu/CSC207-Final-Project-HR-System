package domain;

import login.SearchObject;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class JobPosting implements SearchObject {
    private enum Status {Posted, Closed, Filled}
    private LocalDate postDate;
    private LocalDate closeDate;
    private int numPositions = 1;
    private Status status = Status.Posted;
    private JobDecidingProcess decidingProcess;
    private Company company;
    private List<String> requirement;
    private String id;
//    private static List<domain.JobPosting> allJobPostings = new ArrayList<>();

    public JobPosting(HashMap<String, String> map, Company company, String id) {
        this.company = company;
        this.decidingProcess = new JobDecidingProcess();
        this.id = id;
        this.postDate = LocalDate.parse(map.get("Post Date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.closeDate = LocalDate.parse(map.get("Close Date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.requirement = new ArrayList<>(Arrays.asList(map.get("Requirement").split(",")));
        this.numPositions = Integer.parseInt(map.get("Number of Positions"));
    }

    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, List<String> requirement, String id) {
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.decidingProcess = new JobDecidingProcess();
        this.requirement = requirement;
        this.id = id;
//        addJobPostings(this);
    }

    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, List<String> requirement, String id, int numPositions) {
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.numPositions = numPositions;
        this.decidingProcess = new JobDecidingProcess();
        this.requirement = requirement;
        this.id = id;
//        addJobPostings(this);
//        Oliver to YiChun: there should be a new method to add new Account to the entire collection. instead of
//        adding it in the constructor.
    }

    public void setRequirement(List<String> requiredDocument) {
        this.requirement = requiredDocument;
    }

    void setRounds(List<String> interviewRounds) {
        this.decidingProcess.setRounds(interviewRounds);
        if (!interviewRounds.isEmpty()) {
            this.decidingProcess.setCurrentRound(interviewRounds.get(0));
        }
    }

    void setClosed() {
        this.status = Status.Closed;
    }

    void setFilled(){
        this.status = Status.Filled;
    }


    public void setNumPositions(int numPositions) {
        this.numPositions = numPositions;
    }

    public List<String> getRequirement() {
        return requirement;
    }

    public Company getCompany() {
        return company;
    }

    public String getDescription(){
        return "Description"
                + "Post Date: " + this.postDate.toString()
                + "; Close Date: " + this.closeDate.toString()
                + "; Requirements: " + this.requirement.toString()
                + "; Number of Positions: " + this.numPositions;
    }

    public Status getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getCurrentRound() {
        return this.decidingProcess.getCurrentRound();
    }

//    public List<domain.JobPosting> getAllJobPostings() {
//        return allJobPostings;
//    }

    public boolean isClosed() {
        return this.status == Status.Closed;
    }

    public boolean isFilled() {
        return this.status == Status.Filled;
    }

    //    Methods for HR:
    public List<Application> getAllApplications() {
        return this.decidingProcess.getAllApplications();
    }

    public List<Application> getRemainingApplications() {
        return this.decidingProcess.getRemainingApplications();
    }

    public List<Interview> getNextRoundInterviews() {
        return this.decidingProcess.getNextRoundInterviews();
    }

    public boolean checkLastRound() {
        return this.decidingProcess.checkLastRound();
    }

    public void addInterviews(Interview interview) {
        this.decidingProcess.addInterviews(interview);
    }

    public void addInterviews(ArrayList<Interview> interviews) {
        this.decidingProcess.addInterviews(interviews);
    }

    // Methods for Applicant:
    @Override
    public String toString() {
        return "Post Date: " + this.postDate.toString()
                + "; Close Date: " + this.closeDate.toString()
                + "; Requirements: " + this.requirement.toString()
                + "; Number of Positions: " + this.numPositions;
    }

    public void setNextRound(List<Application> applications) {
        this.decidingProcess.setRound(applications);
    }

    //    Method for System:
    public void addApplication(Application application) {
        this.decidingProcess.addApplication(application);
    }

    public void withdrawApplication(Application application) {
        this.decidingProcess.withdrawApplication(application);
    }

    public void withdrawApplication(List<Application> applications) {
        this.decidingProcess.withdrawApplication(applications);
    }

    //    Static methods
//    public static void addJobPostings(domain.JobPosting jobPosting) {
//        allJobPostings.add(jobPosting);
//    }

//    public static void deleteJobPostings(domain.JobPosting jobPosting) {
//        allJobPostings.remove(jobPosting);
//    }

    HashMap<String, String> getAccount() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Company", "Company Name");  //change to company name
        map.put("Job Title", "Job Title");
        map.put("Post Date", this.postDate.toString());
        map.put("Close Date", this.closeDate.toString());
        map.put("Number of Positions", Integer.toString(this.numPositions));
        map.put("Status", this.status.toString());
        map.put("Requirement", this.requirement.toString());
        return map;
    }


    // Unfinished:
    void setRounds(ArrayList<String> interviewRounds, int insertPos) {
    }

    // GUI:
    public List<Interview> getRoundInterviews(String round) {
        return this.decidingProcess.getRoundInterviews(round);
    }

    public List<String> getInterviewRounds(){
        return this.decidingProcess.getInterviewRounds();
    }

    @Override
    public String getSearchValue1() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }
}
