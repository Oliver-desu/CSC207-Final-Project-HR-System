package domain.job;

import domain.applying.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPosting {

    public enum JobPostingStatus {
        OPEN,
        PROCESSING,
        FINISHED
    }

    private HashMap<String, Application> applications;
    private HashMap<Integer, InterviewRound> interviewRounds;
    private int currRound;
    private JobPostingStatus status;
    private JobInfo jobInfo;

    public JobPosting() {
    }

    public HashMap<String, Application> getApplicationMap() {
        return null;
    }

    public ArrayList<Application> getApplcations() {
        return null;
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        return null;
    }

    public ArrayList<Application> getRemainingApplications() {
        return null;
    }

    public Application getApplication(String applicationId) {
        return null;
    }

    public InterviewRound getCurrentInterviewRound() {
        return null;
    }

    ArrayList<InterviewRound> getAllInterviewRounds() {
        return null;
    }

    public JobPostingStatus getStatus() {
        return null;
    }

    public JobInfo getJobInfo() {
        return null;
    }

    public String getJobId() {
        return null;
    }


    public void addInterviewRound(InterviewRound interviewRound) {
    }

    void start() {
    }

    void next() {
    }

    boolean isLastRound() {
        return false;
    }

    void hire(Application application) {
    }

    void endJobPosting() {
    }

    void applicationSubmit(Application application) {
    }

    void applicationCancel(Application application) {
    }
}
