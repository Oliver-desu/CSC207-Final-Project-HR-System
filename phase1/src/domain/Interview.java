package domain;

import login.SearchObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Interview extends Observable {

    public enum InterviewState {
        UPCOMING,
        PENDING,
        PAST
    }

    private LocalDate date;
    private JobPosting jobPosting;
    private Interviewer interviewer;
    private Application application;
    private InterviewState state;
    private boolean result;


    public Interview(LocalDate date, JobPosting jobPosting, Interviewer interviewer, Applicant applicant) {
        this.date = date;
        this.jobPosting = jobPosting;
        this.interviewer = interviewer;
        this.application = applicant.getApplication(jobPosting);
        this.state = InterviewState.UPCOMING;
        //addObserver (I'll write this later
    }

    public LocalDate getDate() {
        return this.date;
    }

    public InterviewState getState() {
        return this.state;
    }

    public void setResult(String result) {
        this.state = InterviewState.PAST;
        application.addInterview(this, InterviewState.PAST);
        if (result.equals("PASS")) {
            this.result = true;
            this.application.getApplicant().addApplication(this.application, Application.ApplicationState.PENDING);
        } else if (result.equals("REJECT")) {
            this.result = false;
            this.application.getApplicant().addApplication(this.application, Application.ApplicationState.REJECTED);
        }
    }

    public void checkIfPending() {
        if (LocalDate.now().isAfter(this.date)) {
            state = InterviewState.PENDING;
            this.interviewer.addInterview(this, InterviewState.PENDING);
            this.application.addInterview(this, InterviewState.PENDING);
        }
    }

    // TODO: 2019-07-08 toString method
    @Override
    public String toString() {
//        return "Date: " + this.date + "\n" +
//                "Company: " + this.jobPosting.getCompany().getCompanyName() + "\n" +
//                "Job position: " + this.jobPosting.getPosition() + "\n" +
//                "Interviewer: " + this.interviewer.getName() + "\n" +
//                "Applicant: " + this.application.getApplicant().getName() + "\n" +
//                "State: " + this.state;
        return null;
    }

    //I'll implement this later
    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }
}











    /*
    private enum Status {
        PASS,
        REJECT,
        PENDING,
        EMPTY
    }



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
        ArrayList<String> searchValues = new ArrayList<>();
        searchValues.add(status.toString());
        searchValues.add(jobPosting.getId());
        searchValues.add(application.getApplicantRealName());
        return searchValues;
    }

    @Override
    public String getInfo() {
        return toStringForStaff();
    }
}
*/