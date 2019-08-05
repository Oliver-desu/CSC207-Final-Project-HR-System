package domain.job;

import domain.Enums.InterviewRoundStatus;
import domain.Enums.InterviewStatus;
import domain.applying.Application;
import domain.applying.Interview;
import domain.filter.Filterable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code InterviewRound} stores information about a specific round for a
 * {@code JobPosting} and deals with all the events happened within this round.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see JobPosting
 * @see InterviewRoundStatus
 * @see InterviewRoundManager
 * @see Interview
 * @since 2019-08-04
 */
public class InterviewRound implements Filterable, Serializable {

    /**
     * The name of this interview round.
     *
     * @see #getRoundName()
     */
    private String roundName;

    /**
     * All the applications that made into this interview round.
     *
     * @see #getInterviews()
     * @see #getCurrentRoundApplications()
     * @see #getUnmatchedApplications()
     * @see #getApplicationsByStatus(InterviewStatus)
     */
    private ArrayList<Application> applications;

    /**
     * The status of this interview round, values are from {@code InterviewRoundStatus}.
     *
     * @see InterviewRoundStatus
     * @see #getStatus()
     * @see #setStatus(InterviewRoundStatus)
     * @see #checkStatus()
     */
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
