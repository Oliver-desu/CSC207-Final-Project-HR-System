package domain.job;

import domain.Enums.InterviewRoundStatus;
import domain.Enums.InterviewStatus;
import domain.applying.Application;
import domain.applying.Interview;
import domain.filter.Filterable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class InterviewRound implements Filterable, Serializable {

    private String roundName;
    private ArrayList<Application> applications;
    private InterviewRoundStatus status;


    public InterviewRound(String roundName) {
        this.roundName = roundName;
        this.status = InterviewRoundStatus.EMPTY;
        this.applications = new ArrayList<>();
    }

    public String getRoundName() {
        return this.roundName;
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        return this.applications;
    }

    public ArrayList<Application> getUnmatchedApplications() {
        return getApplicationsByStatus(InterviewStatus.UNMATCHED);
    }

    private ArrayList<Application> getApplicationsByStatus(InterviewStatus status) {
        ArrayList<Application> passedApplications = new ArrayList<>();
        for (Application application : this.applications) {
            if (application.getInterviewByRound(this.roundName).getStatus().equals(status)) {
                passedApplications.add(application);
            }
        }
        return passedApplications;
    }

    public InterviewRoundStatus getStatus() {
        return this.status;
    }

    public void setStatus(InterviewRoundStatus status) {
        this.status = status;
    }

    public void checkStatus() {
        ArrayList<Interview> interviews = this.getInterviews();
        boolean finished = true;
        for (Interview interview : interviews) {
            if (interview.getStatus().equals(InterviewStatus.UNMATCHED)) {
                this.setStatus(InterviewRoundStatus.MATCHING);
                finished = false;
                break;
            } else if (interview.getStatus().equals(InterviewStatus.PENDING)) {
                this.setStatus(InterviewRoundStatus.PENDING);
                finished = false;
                break;
            }
        }
        if (finished && !this.applications.isEmpty()) {
            this.setStatus(InterviewRoundStatus.FINISHED);
        }
    }

    private ArrayList<Interview> getInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application : this.applications) {
            interviews.add(application.getInterviewByRound(this.roundName));
        }
        return interviews;
    }

    void start(ArrayList<Application> applications) {
        this.setStatus(InterviewRoundStatus.MATCHING);
        for (Application application: applications) {
            this.applications.add(application);
            application.addInterview(this.roundName, new Interview(application));
        }
    }

    void applicationCancel(Application application) {
        applications.remove(application);
        Interview interview = application.getInterviewByRound(roundName);
        interview.cancel();
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("round name", getRoundName());
        map.put("remaining applications", Integer.toString(this.getCurrentRoundApplications().size()));
        map.put("status", status.toString());
        return map;
    }
}
