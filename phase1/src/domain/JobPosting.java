package domain;

import domain.JobPostingStates.*;

import java.time.LocalDate;
import java.util.*;

public class JobPosting implements Observer {


    private String position;
    private int numOfPositions;
    private Company company;
    private LocalDate postDate;
    private LocalDate closeDate;
    private ArrayList<String> requirements;
    private JobPostingState currentState;
    private int numOfRounds;
    private int unmatchedApplicants;
    private int numOfHired;
    private HashMap<String, Application> remainingApplications;


    public JobPosting(ArrayList<Object> fromTextFileds, Company company) {
        this.position = (String) fromTextFileds.get(0);
        this.numOfPositions = (Integer) fromTextFileds.get(1);
        this.requirements = (ArrayList<String>) fromTextFileds.get(2);
        this.closeDate = (LocalDate) fromTextFileds.get(3);
        this.company = company;

        this.postDate = LocalDate.now();
        this.currentState = new Open(this);
        this.numOfRounds = 0;
        this.unmatchedApplicants = 0;
        this.numOfHired = 0;
        this.remainingApplications = new HashMap<>();

        JobPostingManager.addJobPosting(this);
        this.currentState = new Open(this);
    }

    public String getId() {
        return this.company.getCompanyName() + " + " + this.getPosition() + " + " + this.postDate;
    }

    public Company getCompany() {
        return this.company;
    }

    public JobPostingState getCurrentState() {
        return this.currentState;
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    public int getUnmatchedApplicants() {
        return this.unmatchedApplicants;
    }

    public int getNumOfHired() {
        return this.numOfHired;
    }

    public int getNumOfPositions() {
        return this.numOfPositions;
    }

    public int getNumOfRounds() {
        return this.numOfRounds;
    }

    public ArrayList<Application> getRemainingApplications() {
        return (ArrayList<Application>) this.remainingApplications.values();
    }

    public String getPosition() {
        return this.position;
    }

    public LocalDate getPostDate() {
        return this.postDate;
    }

    public ArrayList<String> getRequirements() {
        return this.requirements;
    }

    public void setUnmatchedApplicants(int unmatchedApplicants) {
        this.unmatchedApplicants = unmatchedApplicants;
    }

    public void setCurrentState(JobPostingState state) {
        this.getCompany().moveJobPosting(this, state.getStatus());
        this.currentState = state;
    }

    public void addNumOfRounds() {
        this.numOfRounds ++;
    }

    public void addNumOfHired() {
        this.numOfHired ++;
    }

    public void decreaseUnmatchedApplicants() {
        this.unmatchedApplicants --;
    }

    public void removeApplication(Application application) {
        this.remainingApplications.remove(application.getHeading());
    }

    public void receiveApplication(Application application) {
        this.remainingApplications.put(application.getHeading(), application);
        this.unmatchedApplicants ++;
    }

    public Application findApplication(String heading) {
        return this.remainingApplications.get(heading);
    }

    public String hire(Application application) {
        return this.currentState.hire(application);
    }

    public String getStatus() {
        return this.currentState.getStatus();
    }

    public String matchInterview(Application application, String interviewer, LocalDate date) {
        return this.currentState.matchInterview(interviewer, application, date);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String requirement: this.requirements) {
            s.append(requirement);
        }
        return "Position: " + this.position + "\n" +
                "Number of positions: " + this.numOfPositions + "\n" +
                "Post date: " + this.postDate + "\n" +
                "Close date: " + this.closeDate + "\n" +
                "Requirements: " + "\n" + s.toString() + "\n";
    }


    //methods for observer(I'll implement later
    public void update(Observable o, Object arg){
    }




    //    public enum JobPostingState {
//            OPEN,
//            INTERVIEWING,
//            WAITING_FOR_NEXT_ROUND,
//            PENDING,
//            FILLED,
//            UNFILLED
//    }

//    public JobPosting(ArrayList<Object> objects, HumanResource humanResource) {
//        this.position = (String) objects.get(0);
//        this.numOfPositions = (Integer) objects.get(1);
//        this.requirements = (ArrayList<String>) objects.get(2);
//        this.closeDate = (LocalDate) objects.get(3);
//        this.humanResource = humanResource;
//        this.company = humanResource.getCompany();
//
//        this.postDate = LocalDate.now();
//        this.currentState = JobPostingState.OPEN;
//        this.numOfRounds = 0;
//        this.unmatchedApplicants = 0;
//
//        this.applicants = new HashMap<Application.ApplicationState, ArrayList<Applicant>>();
//        for (Application.ApplicationState state: Application.ApplicationState.values()) {
//            this.applicants.put(state, new ArrayList<>());
//        }
//        this.interviews = new HashMap<Interview.InterviewState, ArrayList<Interview>>();
//        for (Interview.InterviewState state: Interview.InterviewState.values()) {
//            this.interviews.put(state, new ArrayList<>());
//        }
//    }
//
//    public Company getCompany() {
//        return this.company;
//    }
//
//    public String getPosition() {
//        return this.position;
//    }
//
//    public JobPostingState getCurrentState() {
//        return this.currentState;
//     }
//
//     public ArrayList<Applicant> getApplicants(Application.ApplicationState state) {
//        return this.applicants.get(state);
//     }
//
//     void addApplicant(Applicant applicant) {
//        this.applicants.get(Application.ApplicationState.WAITING_FOR_NEXT_ROUND).add(applicant);
//     }
//
//     void addApplicant(Applicant applicant, Application.ApplicationState state) {
//        this.applicants.get(state).add(applicant);
//     }
//
//     void removeApplicant(Applicant applicant) {
//        for (ArrayList<Applicant> tempApplicants: this.applicants.values()) {
//            tempApplicants.remove(applicant);
//        }
//     }
//
//     boolean hasNextRound() {
//        if (this.numOfRounds == 4) {
//            this.currentState = JobPostingState.PENDING;
//            return false;
//        } else {
//            this.numOfRounds ++;
//            return true;
//        }
//     }
//
//     void checkRemainingApplicants() {
//        ArrayList<Applicant> remainingApplicants = applicants.get(Application.ApplicationState.WAITING_FOR_NEXT_ROUND);
//        if (this.numOfPositions > remainingApplicants.size()) {
//            this.currentState = JobPostingState.PENDING;
//            for (Applicant applicant: remainingApplicants) {
//                this.applicants.get(Application.ApplicationState.WAITING_FOR_NEXT_ROUND).remove(applicant);
//                this.applicants.get(Application.ApplicationState.PENDING).add(applicant);
//            }
//        } else {
//            this.currentState = JobPostingState.WAITING_FOR_NEXT_ROUND;
//        }
//     }
//
//     void fromOpen() {
//        if (this.closeDate.isAfter(LocalDate.now())) {
//            this.checkRemainingApplicants();
//        }
//     }
//
//     void fromWaitingForNextRound() {
//        this.unmatchedApplicants --;
//        if (this.unmatchedApplicants == 0) {
//            this.currentState = JobPostingState.INTERVIEWING;
//        }
//     }
//
//     void fromInterviewing() {
//        if (this.hasNextRound()) {
//            this.checkRemainingApplicants();
//        }
//     }
//
//     void fromPending() {
//        if (applicants.get(Application.ApplicationState.HIRED) < this.numOfPositions) {
//            this.currentState = JobPostingState.UNFILLED;
//        } else {
//            this.currentState = JobPostingState.FILLED;
//        }
//     }
//
//    @Override
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (String requirement: this.requirements) {
//            s.append(requirement);
//        }
//        return "Position: " + this.position + "\n" +
//                "Number of positions: " + this.numOfPositions + "\n" +
//                "Post date: " + this.postDate + "\n" +
//                "Close date: " + this.closeDate + "\n" +
//                "Requirements: " + "\n" + s.toString() + "\n";
//    }

    //Set states
//    public void setToOpen(LocalDate date) {
//    }
//
//    public void setToWaitingForNextRound() {
//    }
//
//    public void setToUnfilledForNextRound() {
//    }
//
//    public void setTofilled() {
//    }
//
//    public void setToUnfilled() {
//    }

}




// HumanResource -----------------------------------------------------------------------------------------------------
//public class HumanResource {
//
//    private HashMap<JobPosting.JobPostingState, ArrayList<JobPosting>> responsibleFor;
//    private Company company;
//
//    HumanResource(Company company) {
//        this.company = company;
//        this.responsibleFor = new HashMap<>();
//        for (JobPosting.JobPostingState state: JobPosting.JobPostingState.values()) {
//            this.responsibleFor.put(state, new ArrayList<>());
//        }
//    }
//
//    Company getCompany() {
//        return this.company;
//    }
//
//    String postJob(ArrayList<Object> objects) {
//        String message;
//        if (objects.size() < 4) {
//            message = "Please fill all fields.";
//        } else {
//            JobPosting jobPosting = new JobPosting(objects, this);
//            responsibleFor.get(jobPosting.getCurrentState()).add(jobPosting);
//            message = "Job posted.";
//        }
//        return message;
//    }
//
//    void removePost(JobPosting jobPosting) {
//        for (ArrayList<JobPosting> jobPostings: this.responsibleFor.values()) {
//            jobPostings.remove(jobPosting);
//        }
//    }
//
//    String matchInterview(JobPosting jobPosting, Application application, Interviewer interviewer, LocalDate date) {
//        Interview interview = new Interview(date, jobPosting, interviewer, application.getApplicant());
//        interviewer.addInterview(interview);
//        application.addInterview(interview);
//        application.setState("interviewing");
//        String message;
//        if (jobPosting.getCurrentState().equals(JobPosting.JobPostingState.WAITING_FOR_NEXT_ROUND)) {
//            jobPosting.fromWaitingForNextRound();
//            message = "interview created";
//        } else {
//            message = "can't match interview at this stage";
//        }
//        return message;
//    }
//
//    void hire(JobPosting jobPosting, Applicant applicant) {
//        jobPosting.removeApplicant(applicant);
//        jobPosting.addApplicant(applicant, Application.ApplicationState.HIRED);
//    }
//
//    void reject(JobPosting jobPosting, Applicant applicant) {
//        jobPosting.removeApplicant(applicant);
//        jobPosting.addApplicant(applicant, Application.ApplicationState.REJECTED);
//    }
//
//}







// Old version HumanResource ------------------------------------------------------------------------------------------


//public class HumanResource extends User {

//    private static ArrayList<HumanResource> allHumanResources = new ArrayList<HumanResource>();
//    private Company company;
//
//    public HumanResource(String username, String password, LocalDate dateCreated, Company company) {
//        super(username, password, dateCreated);
//        this.company = company;
//        allHumanResources.add(this);
//    }
//
//    public static ArrayList<HumanResource> getAllHumanResources() {
//        return allHumanResources;
//    }
//
//    public Company getCompany() {
//        return this.company;
//    }
//
//
//    public ArrayList<Applicant> getApplicants() {
//        return this.company.getApplicants();
//    }
//
//    public ArrayList<JobPosting> getJobPostings(){
//        return this.company.getJobPostings();
//    }
//
//    public HashMap<String, String> getAccount() {
//        HashMap<String, String> result = ((User) this).getAccount();
//        result.put("Company name", this.company.getCompanyName());
//        return result;
//    }
//
//    public Interviewer getInterviewer(String realName){
//        ArrayList<Interviewer> interviewers = this.company.getInterviewers();
//        for (Interviewer interviewer: interviewers) {
//            if (realName.equals(interviewer.getRealName())) {
//                return interviewer;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public String toString() {
//        return ((User) this).toString() + "\n" +
//                "Company name: " + this.company.getCompanyName();
//    }
//
//
//}






































// Old version JobPosting ---------------------------------------------------------------------------------------------



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