package domain.job;

import domain.applying.Application;
import domain.applying.Interview;

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
    private HashMap<String, Application> applications;  //applicantId->application
    private InterviewRoundStatus status;

    public InterviewRound(String roundName) {
        this.roundName = roundName;
        this.status = InterviewRoundStatus.EMPTY;
        this.applications = new HashMap<>();
    }


    public String getRoundName() {
        return this.roundName;
    }

    public HashMap<String, Application> getApplicationMap() {
        return this.applications;
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        return new ArrayList<>(this.applications.values());
    }

    public ArrayList<Application> getUnmatchedApplications() {
        ArrayList<Application> unmatchedApplications = new ArrayList<>();
        for (Application application : this.applications.values()) {
            if (application.getInterviewByRound(this.roundName).getStatus().equals(Interview.InterviewStatus.UNMATCHED)) {
                unmatchedApplications.add(application);
            }
        }
        return unmatchedApplications;
    }


    public ArrayList<Application> getPassedApplications() {
        ArrayList<Application> passedApplications = new ArrayList<>();
        for (Application application : this.applications.values()) {
            if (application.getInterviewByRound(this.roundName).getStatus().equals(Interview.InterviewStatus.PASS)) {
                passedApplications.add(application);
            }
        }
        return passedApplications;
    }

    public Application getApplication(String applicantId) {
        return this.applications.get(applicantId);
    }

    public InterviewRoundStatus getStatus() {
        return this.status;
    }

    public void setStatus(InterviewRoundStatus status) {
        this.status = status;
    }

    public boolean checkStatus() {
        return false;
    }

    public void start() {
    }


}
