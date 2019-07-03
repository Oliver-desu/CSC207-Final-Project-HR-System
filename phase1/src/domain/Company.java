package domain;

import java.util.ArrayList;

public class Company {

    private static ArrayList<Company> allCompanies = new ArrayList<>();
    private String companyName;
    private HumanResource humanResource;
    private ArrayList<Interviewer> interviewers = new ArrayList<>();
    private ArrayList<JobPosting> jobPostings = new ArrayList<>();
    private ArrayList<Applicant> applicants = new ArrayList<>();

    public Company(String companyName){
        this.companyName = companyName;
        allCompanies.add(this);
    }

    public Company(){
        this.companyName = "unnamed";
        allCompanies.add(this);
    }

    //getters
    public String getCompanyName() { return companyName; }

    public HumanResource getHumanResource() {
        return this.humanResource;
    }

    public ArrayList<Interviewer> getInterviewers() {
        return this.interviewers;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return this.jobPostings;
    }

    public ArrayList<Applicant> getApplicants() {
        return this.applicants;
    }

    public static ArrayList<Company> getAllCompanies() {
        return allCompanies;
    }


//setters


    public void setHumanResource(HumanResource humanResource) {
        this.humanResource = humanResource;
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






