package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HumanResource extends User {

    private static ArrayList<HumanResource> allHumanResources = new ArrayList<HumanResource>();
    private Company company;

    public HumanResource(String username, String password, LocalDate dateCreated, Company company) {
        super(username, password, dateCreated);
        this.company = company;
        allHumanResources.add(this);
    }

    public static ArrayList<HumanResource> getAllHumanResources() {
        return allHumanResources;
    }

    public Company getCompany() {
        return this.company;
    }


    public ArrayList<Applicant> getApplicants() {
        return this.company.getApplicants();
    }

    public ArrayList<JobPosting> getJobPostings(){
        return this.company.getJobPostings();
    }

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = ((User) this).getAccount();
        result.put("Company name", this.company.getCompanyName());
        return result;
    }

    public Interviewer getInterviewer(String realName){
        ArrayList<Interviewer> interviewers = this.company.getInterviewers();
        for (Interviewer interviewer: interviewers) {
            if (realName.equals(interviewer.getRealName())) {
                return interviewer;
            }
        }
        return null;
    }

//   TODO: Unfinished methods:

    @Override
    public String toString() {
        return null;
    }


}
