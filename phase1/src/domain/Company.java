package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Company {
    private  String companyName;
    private  HashMap<JobPostingState,ArrayList<JobPosting>> JobPostings;
    private  HumanResource hr;
    private  ArrayList<Interviewer> interviewers;




    public Company(String companyName){
        this.companyName = companyName;
        this.hr = new HumanResource(this);
    }



    //getters
    public String getCompanyName() { return companyName; }

    public HumanResource getHr() {
        return hr;
    }

    public ArrayList<Interviewer> getInterviewers() {
        return interviewers;
    }

    public ArrayList<JobPosting> getJobPostings(JobPostingState key) {
        return this.JobPostings.get(key);
    }

    //setters


    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setHr(HumanResource hr) {
        this.hr = hr;
    }

    public void setInterviews(ArrayList<Interviewer> interviewers) {
        this.interviewers = interviewers;
    }

    public void setJobPostings(HashMap<JobPostingState, ArrayList<JobPosting>> jobPostings) {
        JobPostings = jobPostings;
    }
    // end ---------------------------

    public  void removeHRCoords(User user){
        // need to be done
    }
    public void addInterviewer(Interviewer interviewer){
        interviewers.add(interviewer);
    }
    public  void removeInterviewer(Interviewer interviewer){
        interviewers.remove(interviewer);
    }

    public void addJobPosting(JobPostingState key){
        JobPostings.put(key,null);
    }
    public  void addValueToArraylistInHashmap(HashMap<Object,ArrayList> map,Object key, Object value ){

        try {ArrayList v = map.get(key);

            v.add(value);
            map.put(key,v);}
        catch (ClassCastException e){
            System.out.println("try the right type");
        }

    }
    public  void closeJobPosting(JobPosting job){

    }
}






