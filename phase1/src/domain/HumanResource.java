package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HumanResource {

    private HashMap<String, ArrayList<JobPosting>> responsibleFor;
    private Company company;

    HumanResource(Company company) {
        this.company = company;
        this.responsibleFor = new HashMap<>();
        this.responsibleFor.put("open", new ArrayList<>());
        this.responsibleFor.put("interviewing", new ArrayList<>());
        this.responsibleFor.put("waiting for next round", new ArrayList<>());
        this.responsibleFor.put("filled", new ArrayList<>());
        this.responsibleFor.put("unfilled", new ArrayList<>());
    }

    String postJob(ArrayList<Object> objects) {
        String message;
        if (objects.size() < 4) {
            message = "Please fill all fields.";
        } else {
             JobPosting jobPosting = new JobPosting(objects);
             responsibleFor.get(jobPosting.getCurrentState()).add(jobPosting);
             message = "Job posted.";
        }
        return message;
    }

    void removePost(JobPosting jobPosting) {
        for (ArrayList<JobPosting> jobPostings: this.responsibleFor.values()) {
            jobPostings.remove(jobPosting);
        }
    }

    String matchInterview(JobPosting jobPosting, Application application, Interviewer interviewer, LocalDate date) {
        Interview interview = new Interview(date, jobPosting, interviewer, application.getApplicant());
        interviewer.addInterview(interview);
        application.addInterview(interview);
        application.setState("interviewing");
        String message;
        if (jobPosting.getCurrentState().equals("waitingForNextRound")) {
            jobPosting.fromWaitingForRound();
            message = "interview created";
        } else {
            message = "can't match interview at this stage";
        }
        return message;
    }

    void hire(JobPosting jobPosting, Applicant applicant) {
        jobPosting.removeApplicant(applicant);
        jobPosting.addApplicant(applicant, "hired");
    }

    void reject(JobPosting jobPosting, Applicant applicant) {
        jobPosting.removeApplicant(applicant);
        jobPosting.addApplicant(applicant, "rejected");
    }

}








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
