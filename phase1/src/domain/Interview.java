package domain;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Observable;

public class Interview extends Observable implements Serializable {

    private String interviewer;
    private Application application;
    private LocalDate date;
    private String currentState;
    private String heading;
    private Company company;

    public Interview(String interviewer, Application application, LocalDate date, Company company) {
        this.interviewer = interviewer;
        this.application = application;
        this.date = date;
        this.currentState = "upcoming";
        this.heading = interviewer + ", " + application.getApplicantName() + " at " + date;
        this.company = company;
        company.addToUpcomingInterviews(this);
        this.addObserver(application);
    }

    public LocalDate getDate() {
        return this.date;
    }

    public String getHeading() {
        return this.heading;
    }

    public Company getCompany() {
        return this.company;
    }

    public Application getApplication() {
        return this.application;
    }

    public String getCurrentState() {
        return this.currentState;
    }

    public String getInterviewer() {
        return this.interviewer;
    }

    public String giveRecommendation(String result) {
        if (this.currentState.equals("pending")) {
            this.update(result);
            return "You've sent your recommendation.";
        } else {
            return "You can't give recommendation at this time.";
        }
    }

    @Override
    public String toString() {
        return "Interviewer: " + this.interviewer + "\n" +
                "Applicant: " + this.application.getApplicantName() + "\n" +
                "Date: " + this.date + "\n" +
                "Current State: " + this.currentState;
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    public void update(String state) {
        this.currentState = state;
        setChanged();
        notifyObservers(state);
    }


}







