package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Company extends  User{

    private String companyName;
    private HashMap<String, ArrayList<JobPosting>> jobPostingsByState = new HashMap<>();
    // i will try to add limitation on this attribute
    ////Key: "open", "interviewing", "waiting for next round", "pending", "filled", "unfilled"

    private HashMap<String, ArrayList<JobPosting>> interviewers = new HashMap<>();
    private HashMap<String, ArrayList<Interview>> upcomingInterviews = new HashMap<>();
    private HashMap<String, ArrayList<Interview>> waitingForResultInterviews = new HashMap<>();


    public  Company(String username,String password){
        super(username,password);
    }

    //getters

    public HashMap<String, ArrayList<JobPosting>> getJobPostings() {
        return this.jobPostingsByState;
    }

    public HashMap<String, ArrayList<Interview>> getUpcomingInterviews() {
        return upcomingInterviews;
    }

    public HashMap<String, ArrayList<Interview>> getWaitingForResultInterviews() {
        return waitingForResultInterviews;
    }

    public String getCompanyName() { return companyName; }

    public HashMap<String,ArrayList<JobPosting>> getInterviewers() {
        return interviewers;
    }


    //setters


    public void setWaitingForResultInterviews(HashMap<String, ArrayList<Interview>> waitingForResultInterviews) {
        this.waitingForResultInterviews = waitingForResultInterviews;
    }

    public void setUpcomingInterviews(HashMap<String, ArrayList<Interview>> upcomingInterviews) {
        this.upcomingInterviews = upcomingInterviews;
    }

    public void setInterviewers(HashMap<String, ArrayList<JobPosting>> interviewers) {
        this.interviewers = interviewers;
    }

    public void setJobPostingsByState(HashMap<String, ArrayList<JobPosting>> jobPostings) {
        this.jobPostingsByState = jobPostings;   }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    // end ---------------------------
    
    public void moveJobPosting(JobPosting jobPosting, String moveTo) {
        this.jobPostingsByState.get(jobPosting.getCurrentState().getStatus()).remove(jobPosting);
        this.jobPostingsByState.get(moveTo).add(jobPosting);
    }

    public String assignInterview(String interviewer, Application application, LocalDate date){
        try {
            Interview interview = new Interview(interviewer, application, date, this);
            application.addInterview(interview);
        }
        catch (ClassCastException e){
            return  "not matched";
        }
        return  "Interview Matched";
    }

    public ArrayList<JobPosting> findJobPostings(String state){
        return jobPostingsByState.get(state);
    }

    public String postJob(ArrayList<Object> fromTextFiles){
        JobPosting jobPosting = new JobPosting(fromTextFiles,this);
        JobPostingManager.addJobPosting(jobPosting);
        this.jobPostingsByState.get(jobPosting.getStatus()).add(jobPosting);
        return "Job posted.";
    }


    public ArrayList<Interview> findUpcomingInterveiws(String interviewer){
        return upcomingInterviews.get(interviewer);
    }

    public ArrayList<Interview> findWaitingForResultInterviews(String interviewer){
        return waitingForResultInterviews.get(interviewer);
    }

    public void removeInterview(Interview interview){
        String interviewer = interview.getInterviewer();
        this.removeFromWaitingForResultInterviews(interview);
    }

    public void moveInterview(Interview interview){
        String interviewer = interview.getInterviewer();
        this.removeFromWaitingForResultInterviews(interview);
        this.addToWaitingForResultInterviews(interview);
    }

    public void addToUpcomingInterviews(Interview interview) {
        if (!this.upcomingInterviews.containsKey(interview.getInterviewer())) {
            this.upcomingInterviews.put(interview.getInterviewer(), new ArrayList<>());
        }
        this.upcomingInterviews.get(interview.getInterviewer()).add(interview);
    }

    public void removeFromUpcomingInterviews(Interview interview) {
        this.upcomingInterviews.get(interview.getInterviewer()).remove(interview);
        if (this.upcomingInterviews.get(interview.getInterviewer()).isEmpty()) {
            this.upcomingInterviews.remove(interview.getInterviewer());
        }
    }

    public void addToWaitingForResultInterviews(Interview interview) {
        if (!this.waitingForResultInterviews.containsKey(interview.getInterviewer())) {
            this.waitingForResultInterviews.put(interview.getInterviewer(), new ArrayList<>());
        }
        this.waitingForResultInterviews.get(interview.getInterviewer()).add(interview);
    }

    public void removeFromWaitingForResultInterviews(Interview interview) {
        this.waitingForResultInterviews.remove(interview.getInterviewer()).remove(interview);
        if (this.waitingForResultInterviews.get(interview.getInterviewer()).isEmpty()) {
            this.waitingForResultInterviews.remove(interview.getInterviewer());
        }
    }




    public ArrayList<Interview> getValuesFromMap(HashMap<String,Interview> map, ArrayList lst){

            for (String key:map.keySet()
            ) {lst.add(map.get(key));

            }

        return  lst;
    }


    public void addInterviewer(String interviewer){
        //need to be done
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


    public ArrayList<String> findInterviewers(JobPosting jobPosting){
        return  new ArrayList<>();
        // need to be done
    }



    public  void  removePost(String  hr, JobPosting job ){

    }

}






