package domain;

import login.SearchObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class JobPosting implements SearchObject {
    private enum Status {Posted, Closed, Filled}
    private LocalDate postDate;
    private LocalDate closeDate;
    private int numPositions = 1;
    private Status status = Status.Posted;
    private JobDecidingProcess decidingProcess;
    private Company company;
    private ArrayList<String> requirements;
    private String id;
//    private static List<domain.JobPosting> allJobPostings = new ArrayList<>();

    public JobPosting(HashMap<String, String> account) {
        this.postDate = LocalDate.parse(account.get("Post Date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.closeDate = LocalDate.parse(account.get("Close Date"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.requirements = new ArrayList<>(Arrays.asList(account.get("Requirement").split(",")));
        this.numPositions = Integer.parseInt(account.get("Number of Positions"));
    }

    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, String id) {
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getRequirement() {
        return requirements;
    }

    public void setRequirement(ArrayList<String> requiredDocument) {
        this.requirements = requiredDocument;
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

    //    Methods for HR:
    public ArrayList<Application> getAllApplications() {
        return this.decidingProcess.getAllApplications();
    }

    public Company getCompany() {
        return company;
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

    HashMap<String, String> getAccount() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Company", "Company Name");  //change to company name
        map.put("Job Title", "Job Title");
        map.put("Post Date", this.postDate.toString());
        map.put("Close Date", this.closeDate.toString());
        map.put("Number of Positions", Integer.toString(this.numPositions));
        map.put("Status", this.status.toString());
        map.put("Requirement", this.requirements.toString());
        return map;
    }

    public boolean checkLastRound() {
        return this.decidingProcess.checkLastRound();
    }

    //    Method for System:
    public void addApplication(Application application) {
        this.decidingProcess.addApplication(application);
    }

    public void withdrawApplication(Application application) {
        this.decidingProcess.withdrawApplication(application);
    }

    //    Static methods
//    public static void addJobPostings(domain.JobPosting jobPosting) {
//        allJobPostings.add(jobPosting);
//    }

//    public static void deleteJobPostings(domain.JobPosting jobPosting) {
//        allJobPostings.remove(jobPosting);
//    }

    public void nextRound() {
        decidingProcess.nextRound();
    }

    public boolean isValidApplication(Application application) {
//        return decidingProcess.hasApplicant(application.getApplicant());
        return true;
    }

    // GUI:
    public ArrayList<Interview> getRoundInterviews(String round) {
        return this.decidingProcess.getRoundInterviews(round);
    }

    public ArrayList<String> getInterviewRounds() {
        return this.decidingProcess.getInterviewRounds();
    }

    public void setInterviewRounds(ArrayList<String> interviewRounds) {
        decidingProcess = new JobDecidingProcess(interviewRounds);
    }

    @Override
    public String getInfo() {
        return "Description"
                + "Post Date: " + this.postDate.toString()
                + "; Close Date: " + this.closeDate.toString()
                + "; Requirements: " + this.requirements.toString()
                + "; Number of Positions: " + this.numPositions;
    }

    @Override
    public ArrayList<String> getSearchValues() {
        ArrayList<String> searchValues = new ArrayList<>();
        searchValues.add(id);
        searchValues.add(postDate.toString());
        searchValues.add(closeDate.toString());
        return searchValues;
    }
}
