package domain;


import java.time.LocalDate;
import java.util.Observable;

public class Interview extends Observable {

    private String interviewer;
    private Application application;
    private LocalDate date;
    private String currentState;

    public Interview(String interviewer, Application application, LocalDate date) {
        this.interviewer = interviewer;
        this.application = application;
        this.date = date;
        this.currentState = "upcoming";
        this.addObserver(application);
    }

    public LocalDate getDate() {
        return this.date;
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
                "Applicant: " + this.application.getApplicant().getUserName() + "\n" +
                "Date: " + this.date + "\n" +
                "Current State: " + this.currentState;
    }


    // TODO: 2019-07-09

    //I'll implement this later
    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    public void update(String result) {

    }


}







