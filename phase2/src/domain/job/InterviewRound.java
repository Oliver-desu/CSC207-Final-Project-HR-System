package domain.job;

import domain.applying.Application;
import domain.applying.Interview;
import domain.filter.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterviewRound implements Filterable {

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

    public void checkStatus() {
        ArrayList<Interview> interviews = this.getInterviews();
        boolean finished = true;
        for (Interview interview: interviews) {
            if (interview.getStatus().equals(Interview.InterviewStatus.UNMATCHED)) {
                this.setStatus(InterviewRoundStatus.MATCHING);
                finished = false;
                break;
            } else if (interview.getStatus().equals(Interview.InterviewStatus.PENDING)) {
                this.setStatus(InterviewRoundStatus.PENDING);
                finished = false;
                break;
            }
        }
        if (finished) {
            this.setStatus(InterviewRoundStatus.FINISHED);
        }
    }

    private ArrayList<Interview> getInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application: this.applications.values()) {
            interviews.add(application.getInterviewByRound(this.roundName));
        }
        return interviews;
    }

    public void start() {
        this.setStatus(InterviewRoundStatus.MATCHING);
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("roundName");
        headings.add("numOfApplications");
        headings.add("status");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.getRoundName());
        values.add(Integer.toString(this.getCurrentRoundApplications().size()));
        values.add(this.status.toString());
        return values.toArray(new String[0]);
    }
}
