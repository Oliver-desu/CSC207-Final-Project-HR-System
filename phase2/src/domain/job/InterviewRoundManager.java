package domain.job;

import domain.Enums.ApplicationStatus;
import domain.Enums.InterviewRoundStatus;
import domain.Enums.JobPostingStatus;
import domain.applying.Application;

import java.util.ArrayList;

public class InterviewRoundManager {

    private JobPosting jobPosting;
    private ArrayList<InterviewRound> interviewRounds;
    private ArrayList<Application> remainingApplications;


    InterviewRoundManager(JobPosting jobPosting, ArrayList<Application> applications) {
        this.jobPosting = jobPosting;
        this.interviewRounds = new ArrayList<>();
        this.remainingApplications = applications;
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public ArrayList<InterviewRound> getInterviewRounds() {
        return interviewRounds;
    }

    public ArrayList<Application> getRemainingApplications() {
        return remainingApplications;
    }

    public InterviewRound getCurrentInterviewRound() {
        InterviewRound currInterviewRound = null;
        for (InterviewRound interviewRound : interviewRounds) {
            if (interviewRound.getStatus().equals(InterviewRoundStatus.EMPTY)) {
                break;
            } else {
                currInterviewRound = interviewRound;
            }
        }
        return currInterviewRound;
    }

    public void addInterviewRound(InterviewRound interviewRound) {
        interviewRounds.add(interviewRound);
    }

    void updateRemainingApplications() {
        ArrayList<Application> tempApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (!application.getStatus().equals(ApplicationStatus.REJECTED)) {
                tempApplications.add(application);
            }
        }
        remainingApplications = tempApplications;
    }

    public boolean nextRound() {
        InterviewRound currentRound = getCurrentInterviewRound();
        if (jobPosting.getStatus().equals(JobPostingStatus.PROCESSING) &&
                (currentRound == null || currentRound.getStatus().equals(InterviewRoundStatus.FINISHED))) {
            InterviewRound nextRound = interviewRounds.get(interviewRounds.indexOf(currentRound) + 1);
            nextRound.start(remainingApplications);
            return true;
        } else {
            return false;
        }
    }

    public void checkStatus() {
        InterviewRound interviewRound = getCurrentInterviewRound();
        if (interviewRound != null) interviewRound.checkStatus();
        updateRemainingApplications();
    }

    private ArrayList<Application> getHiredApplications() {
        ArrayList<Application> hiredApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (application.getStatus().equals(ApplicationStatus.HIRE)) {
                hiredApplications.add(application);
            }
        }
        return hiredApplications;
    }

    private boolean currentRoundFinished() {
        InterviewRound currentRound = getCurrentInterviewRound();
        return currentRound == null || currentRound.getStatus().equals(InterviewRoundStatus.EMPTY);
    }

    public boolean hire(Application application) {
        if (jobPosting.getStatus().equals(JobPostingStatus.PROCESSING) &&
                application.getStatus().equals(ApplicationStatus.PENDING) && currentRoundFinished() &&
                jobPosting.getNumOfPositions() > getHiredApplications().size()) {
            application.setStatus(ApplicationStatus.HIRE);
            return true;
        } else {
            return false;
        }
    }

    void applicationCancel(Application application) {
        remainingApplications.remove(application);
        InterviewRound interviewRound = getCurrentInterviewRound();
        if (interviewRound != null) interviewRound.applicationCancel(application);
    }

}
