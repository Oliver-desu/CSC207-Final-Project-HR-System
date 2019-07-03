package domain;

import login.SearchObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Interview implements SearchObject {

    private enum Status {
        PASS,
        REJECT,
        PENDING,
        EMPTY
    }

    private String id;
    private LocalDate date;
    private String location;
    private Double duration;
    private JobPosting jobPosting;
    private Interviewer interviewer;
    private Application application;
    private String round;
    private Status status = Status.EMPTY;
    private String recommendation = "The interviewer has not updated recommendation.";

    public Interview(LocalDate date, String location, Double duration, JobPosting jobPosting, Interviewer interviewer,
                     Application application, String round, String id) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.duration = duration;
        this.jobPosting = jobPosting;
        this.interviewer = interviewer;
        this.application = application;
        this.round = round;
    }

    public Interview(Application application) {
        this.application = application;
    }

    // getter

    public String getId() {
        return this.id;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getLocation() {
        return this.location;
    }

    public Double getDuration() {
        return this.duration;
    }

    public JobPosting getJobPosting() {
        return this.jobPosting;
    }

    public Interviewer getInterviewer() {
        return this.interviewer;
    }

    public Application getApplication() {
        return this.application;
    }

    public String getRound() {
        return this.round;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getRecommandation() {
        return this.recommendation;
    }

    // setter


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setInterviewer(Interviewer interviewer) {
        this.interviewer = interviewer;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void pendingInterview() {
        this.status = Status.PENDING;
    }

    public void passInterview() {
        this.status = Status.PASS;
    }

    public void failInterview() {
        this.status = Status.REJECT;
    }

    public boolean isFinished() {
        return this.status == Status.PASS || this.status == Status.REJECT;
    }

    public boolean isPass() {
        return this.status == Status.PASS;
    }

    public boolean isReject() {
        return this.status == Status.REJECT;
    }

    public boolean isEmpty() {
        return this.status == Status.EMPTY;
    }

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("Date", this.date.toString());
        result.put("Location", this.location);
        result.put("Duration", this.duration.toString());
        result.put("Applicant name", this.application.getApplicantRealName());
        result.put("domain.Interviewer name", this.interviewer.getRealName());
        result.put("Job Posting id", this.jobPosting.getId());
        result.put("Application id", this.application.getId());
        result.put("Status", this.status.toString());
        result.put("Recommendation", this.recommendation);
        return result;
    }

    public String toStringForApplicant() {
        return "Date: " + this.date.toString() + "\n" +
                "Location: " + this.location + "\n" +
                "Duration: " + this.duration + "\n" +
                "Job Posting id: " + this.jobPosting.getId() + "\n" +
                "Round: " + this.round;
    }

    public String toStringForStaff() {
        return this.toStringForApplicant() + "\n" +
                "domain.Interviewer: " + this.interviewer.getRealName() + "\n" +
                "Applicant: " + this.application.getApplicantRealName() + "\n" +
                "Status: " + this.status + "\n" +
                "Recommendation" + this.recommendation;
    }

//    TODO: Unfinished methods

    public void match(Interviewer interviewer){}


    @Override
    public ArrayList<String> getSearchValues() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
