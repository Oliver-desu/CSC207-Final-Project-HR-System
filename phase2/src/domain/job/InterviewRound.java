package domain.job;

import domain.applying.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class InterviewRound {
    public enum InterviewRoundStatus {
        EMPTY,
        MATCHING,
        PENDING,
        FINISHED
    }

    private String roundName;
    private HashMap<String, Application> applications;
    private InterviewRoundStatus status;

    public InterviewRound() {
    }


    public String getRoundName() {
        return null;
    }

    public HashMap<String, Application> getApplicationMap() {
        return null;
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        return null;
    }

    public ArrayList<Application> getUnmatchedApplications() {
        return null;
    }

    public ArrayList<Application> getPassedApplications() {
        return null;
    }

    public Application getApplication(String applicantId) {
        return null;
    }

    public InterviewRoundStatus getStatus() {
        return null;
    }

    public void setStatus(InterviewRoundStatus status) {
    }

    public boolean checkStatus() {
        return false;
    }

    public void start() {
    }


}
