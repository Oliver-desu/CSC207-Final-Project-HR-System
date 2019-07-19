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
        return this.applications;
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(this.applications.values());
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        InterviewRound round = this.interviewRounds.get(currRound);
        return null;
    }

    public ArrayList<Application> getRemainingApplications() {
        return null;
    }

    public Application getApplication(String applicationId) {
        return this.applications.get(applicationId);
    }

    public InterviewRound getCurrentInterviewRound() {
        return this.interviewRounds.get(this.currRound);
    }

    public ArrayList<InterviewRound> getAllInterviewRounds() {
        return null;
    }

    public JobPostingStatus getStatus() {
        return this.status;
    }

    public JobInfo getJobInfo() {
        return this.jobInfo;
    }

    public String getJobId() {
        return this.jobInfo.getId();
    }

    public void addInterviewRound(InterviewRound interviewRound) {
    }

    public boolean isOpen() {
        return this.status.equals(JobPostingStatus.OPEN);
    }

    public void start() {
    }

    public void next() {
    }

    public boolean isLastRound() {
        return false;
    }

    public void hire(Application application) {
    }

    public void endJobPosting() {
    }

    public void applicationSubmit(Application application) {
    }

    public void applicationCancel(Application application) {
    }
}
