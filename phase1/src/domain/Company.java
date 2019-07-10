package domain;

import domain.JobPostingStates.JobPostingState;

import java.util.ArrayList;
import java.util.HashMap;

public class Company extends  User{
    private  String companyName;
    private  HashMap<String,ArrayList<JobPosting>> JobPostings;
    private  HumanResource hr;
    private  HashMap<String,ArrayList<JobPosting>> JobPostingByState;
    // i will try to add limitation on this attributye
    ////Key: "open", "interviewing", "waiting for next round", "pending", "filled", "unfilled"

    private  HashMap<String,ArrayList<JobPosting>> interviewers;
    private  HashMap<String,ArrayList<JobPosting>> hrCoordinator;



    public  Company(String username,String password){
        super(username,password);
    }

    //getters
    public HashMap<String,ArrayList<JobPosting>> getJobPostingByState(){
        return  JobPostingByState;
    }

    public HashMap<String, ArrayList<JobPosting>> getJobPostings() {
        return JobPostings;
    }

    public HashMap<String, ArrayList<JobPosting>> getHrCoordinator() {
        return hrCoordinator;
    }

    public String getCompanyName() { return companyName; }

    public HumanResource getHr() {
        return hr;
    }

    public HashMap<String,ArrayList<JobPosting>> getInterviewers() {
        return interviewers;    }

    public ArrayList<JobPosting> getJobPostings(JobPostingState key) {
        return this.JobPostings.get(key);
    }

    //setters

    public void setJobPostingByState(HashMap<String, ArrayList<JobPosting>> jobPostingByState) {
        JobPostingByState = jobPostingByState;
    }

    public void setInterviewers(HashMap<String, ArrayList<JobPosting>> interviewers) {
        this.interviewers = interviewers;
    }

    public void setJobPostings(HashMap<String, ArrayList<JobPosting>> jobPostings) {
        JobPostings = jobPostings;
    }

    public void setHrCoordinator(HashMap<String, ArrayList<JobPosting>> hrCoordinator) {
        this.hrCoordinator = hrCoordinator;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setHr(HumanResource hr) {
        this.hr = hr;
    }




    // end ---------------------------

    public  void removeHRCoords(User user){
        // need to be done
    }
    public void addInterviewer(String interviewer){
        //need to be done
    }
    public  void  removeInterviewer(String interviewer){
        // need to be done
    }
    public void  assignInterviewer(String interviewer){
        //need to be done
    }

    public  void  addCoordinator(String hr){
        //need to be done
    }
    public  void  removeCoordinator(String hr){
        //need to be done
    }

    public ArrayList<Interview> findInterviews(String interviewer , Interview.InterviewState state){
            return  new ArrayList<>();
            // need to be done
    }
    public ArrayList<String> findInterviewers(JobPosting jobPosting){
        return  new ArrayList<>();
        // need to be done
    }
    public ArrayList<JobPosting> findJobPosting(String hr, JobPostingState state){
        return  new ArrayList<>();
        // need to be done
    }
    public  void  postJob(String hr ,ArrayList job){

    }

    public  void  removePost(String  hr, JobPosting job ){

    }
    public static void addJobPosting(JobPosting job){






    }
    public  void addValueToArraylistInHashmap(HashMap<Object,ArrayList> map,Object key, Object value ){

        try {ArrayList v = map.get(key);

            v.add(value);
            map.put(key,v);}
        catch (ClassCastException e){
            System.out.println("try the right type");
        }

    }
    
}






