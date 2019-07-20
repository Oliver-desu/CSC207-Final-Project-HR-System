package domain.applying;

import domain.job.JobPosting;
import domain.storage.JobPool;

import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    public enum ApplicationStatus {
        DRAFT,
        PENDING,
        HIRE,
        REJECTED
    }

    private HashMap<String, Interview> interviews;
    private String applicantId;
    private String jobPostingId;
    private DocumentManager documentManager;
    private ApplicationStatus status;


    public Application(HashMap<String, String> values) {
        this.interviews = new HashMap<>();
        this.applicantId = values.get("applicantId");
        this.jobPostingId = values.get("jobPostingId");
        this.documentManager = new DocumentManager(false);
        this.status = ApplicationStatus.DRAFT;
    }

    public HashMap<String, Interview> getInterviewMap() {
        return this.interviews;
    }

    public ArrayList<Interview> getInterviews() {
        return (ArrayList<Interview>) this.interviews.values();
    }

    public Interview getInterviewByRound(String round) {
        return this.interviews.get(round);
    }

    public String getApplicantId() {
        return this.applicantId;
    }

    public String getJobPostingId() {
        return this.jobPostingId;
    }

    public DocumentManager getDocumentManager() {
        return this.documentManager;
    }

    public ApplicationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void addInterview(String round, Interview interview) {
        this.interviews.put(round, interview);
    }

    public void apply() {
        this.setStatus(ApplicationStatus.PENDING);
        JobPosting jobPosting = JobPool.getJobPosting(this.jobPostingId);
        jobPosting.applicationSubmit(this);
    }

    public boolean cancel() {
        if (!this.status.equals(ApplicationStatus.HIRE) && !this.status.equals(ApplicationStatus.REJECTED)) {
            this.setStatus(ApplicationStatus.DRAFT);
            JobPosting jobPosting = JobPool.getJobPosting(this.jobPostingId);
            jobPosting.applicationCancel(this);
            return true;
        } else {
            return false;
        }
    }

    public void hired() {
        this.setStatus(ApplicationStatus.HIRE);
    }

    public void rejected() {
        this.setStatus(ApplicationStatus.REJECTED);
    }

}
