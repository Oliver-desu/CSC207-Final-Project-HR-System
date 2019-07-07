package domain;

import login.SearchObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JobPosting implements Observer {

//implements SearchObject


    //private enum Status {Posted, Closed, Filled}
    private String position;
    private LocalDate postDate;
    private LocalDate closeDate;
    private int numPositions = 1;
    //private Status status = Status.Posted;
    //private JobDecidingProcess decidingProcess;
    private Company company;
    private ArrayList<String> requirements;
    private ArrayList<Applicant> remainingApplicants;
    private JobPostingState currentSate;
    //private String id;
    //private static List<domain.JobPosting> allJobPostings = new ArrayList<>();


    public JobPosting(String position, LocalDate closeDate, Company company, ArrayList<String> requirements) {

        this.position = position;
        this.closeDate = closeDate;
        this.company = company;
        this.requirements = requirements;
        //initialize other attributes
    }


    //Set states
    public void setToOpen(LocalDate date) {
    }

    public void setToWaitingForNextRound() {
    }

    public void setToUnfilledForNextRound() {
    }

    public void setTofilled() {
    }

    public void setToUnfilled() {
    }


    //methods for observer(I'll implement later
    public void update(Observable o, Object arg){
    }


}














































    /*

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

    public int getNumPositions() {
        return numPositions;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setRequirement(ArrayList<String> requiredDocument) {
        this.requirements = requiredDocument;
    }

    public void setClosed() {
        this.status = Status.Closed;
    }

    public void setFilled(){
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

    public boolean isOpen() {
        return this.status == Status.Posted;
    }

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

    public boolean isFinalRound() {
        return this.decidingProcess.isFinalRound();
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

    public boolean isValidApplicant(Applicant applicant) {
        return !decidingProcess.hasApplicant(applicant);
    }

    public void updateJobPosting(LocalDate currentDate) {
        if (!this.isFilled() && currentDate.isAfter(this.closeDate)) {
            this.setClosed();
        }
    }

    public boolean canStartNextRound() {
        if (this.isClosed()) {
            ArrayList<Interview> interviews = this.getRoundInterviews(this.getCurrentRound());
            for (Interview interview: interviews) {
                if (!interview.isFinished()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }

    public ArrayList<Application> getPassApplications() {
        return this.decidingProcess.getPassApplications();
    }

    public boolean canHire() {
        return  this.isClosed() && this.canStartNextRound() &&
                (this.isFinalRound() || this.getPassApplications().size() <= this.numPositions);
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
                + "\nPost Date: " + this.postDate.toString()
                + "\nClose Date: " + this.closeDate.toString()
//                + "\nRequirements: " + this.requirements.toString()
                + "\nNumber of Positions: " + this.numPositions;
    }

    @Override
    public ArrayList<String> getSearchValues() {
        ArrayList<String> searchValues = new ArrayList<>();
        searchValues.add(id);
        searchValues.add(postDate.toString());
        searchValues.add(status.toString());
        return searchValues;
    }
}
*/