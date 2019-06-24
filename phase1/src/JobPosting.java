import login.SearchObject;

import java.util.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class JobPosting implements SearchObject {
    private enum Status {Posted, Closed, Filled;}

    private LocalDate postDate;
    private LocalDate closeDate;
    private int numPositions = 1;
    private Status status = Status.Posted;
    private JobDecidingProcess decidingProcess;
    private Company company;
    private List<String> requirement;
    private static List<JobPosting> allJobPostings = new ArrayList<>();

    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, List<String> requirement) {
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.decidingProcess = new JobDecidingProcess();
        this.requirement = requirement;
        addJobPostings(this);
    }

    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, List<String> requirement, int numPositions) {
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.numPositions = numPositions;
        this.decidingProcess = new JobDecidingProcess();
        this.requirement = requirement;
        addJobPostings(this);
//        Oliver to YiChun: there should be a new method to add new JobPosting to the entire collection. instead of
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

    void setStatus(Status status) {
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public String getCurrentRound() {
        return this.decidingProcess.getCurrentRound();
    }

    public List<JobPosting> getAllJobPostings() {
        return allJobPostings;
    }

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
    public static void addJobPostings(JobPosting jobPosting) {
        allJobPostings.add(jobPosting);
    }

    public static void deleteJobPostings(JobPosting jobPosting) {
        allJobPostings.remove(jobPosting);
    }


    // Unfinished:
    void setRounds(ArrayList<String> interviewRounds, int insertPos) {
    }

    // GUI:
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
