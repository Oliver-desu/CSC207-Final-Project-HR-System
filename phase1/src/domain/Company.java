package domain;

import java.util.ArrayList;

public class Company {
    private ArrayList<HumanResource> HumanResourceCoordinators = new ArrayList<>();


    private static ArrayList<Company> allCompanies = new ArrayList<>();
    private String companyName;

    public Company() {
    }
    private ArrayList<Interviewer> interviewers = new ArrayList<>();
    private ArrayList<JobPosting> jobPostings = new ArrayList<>();
    private ArrayList<Applicant> applicants = new ArrayList<>();

    public Company(String companyName){
        this.companyName = companyName;
        allCompanies.add(this);
    }

    //getters
    public String getCompanyName() { return companyName; }

    public ArrayList<HumanResource> getHumanResourceCoordinators() {
        return HumanResourceCoordinators;
    }

    public ArrayList<Interviewer> getInterviewers() {
        return interviewers;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return jobPostings;
    }


//setters


    public void addCoordinators(HumanResource newCoordinator) {
        HumanResourceCoordinators.add(newCoordinator);
    }

    public void addInterviewers(Interviewer newInterviewer) {
        interviewers.add(newInterviewer);
    }

    public void addJobPostings(JobPosting newJobPosting) {
        jobPostings.add(newJobPosting);
    }

    public void addApplicants (Applicant newApplicant) {
        applicants.add(newApplicant);
    }


}






