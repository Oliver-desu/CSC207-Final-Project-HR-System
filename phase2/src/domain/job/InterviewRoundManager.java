package domain.job;

import domain.applying.Application;

import java.util.ArrayList;

public class InterviewRoundManager {

    private JobPosting jobPosting;
    private ArrayList<InterviewRound> interviewRounds;
    private ArrayList<Application> remainingApplications;


    public InterviewRoundManager(JobPosting jobPosting, ArrayList<Application> applications) {
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
        InterviewRound interviewRound = null;
        for (int i = interviewRounds.size() - 1; i >= 0; i--) {
            if (interviewRounds.get(i).isFinished()) {
                break;
            } else {
                interviewRound = interviewRounds.get(i);
            }
        }
        return interviewRound;
    }

    public void addInterviewRound(InterviewRound interviewRound) {
        interviewRounds.add(interviewRound);
    }

    public void updateRemainingApplications() {
        ArrayList<Application> tempApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (!application.getStatus().equals(Application.ApplicationStatus.REJECTED)) {
                tempApplications.add(application);
            }
        }
        remainingApplications = tempApplications;
    }

    public boolean nextRound() {
        InterviewRound currentRound = getCurrentInterviewRound();
        if (jobPosting.getStatus().equals(JobPosting.JobPostingStatus.PROCESSING) && currentRound != null) {
            currentRound.start(remainingApplications);
            return true;
        } else {
            return false;
        }
    }

    public void checkStatus() {
        getCurrentInterviewRound().checkStatus();
        updateRemainingApplications();
    }

    public ArrayList<Application> getHiredApplications() {
        ArrayList<Application> hiredApplications = new ArrayList<>();
        for (Application application : remainingApplications) {
            if (application.getStatus().equals(Application.ApplicationStatus.HIRE)) {
                hiredApplications.add(application);
            }
        }
        return hiredApplications;
    }

    private boolean currentRoundFinished() {
        InterviewRound currentRound = getCurrentInterviewRound();
        return currentRound == null || currentRound.getStatus().equals(InterviewRound.InterviewRoundStatus.EMPTY);
    }

    public boolean hire(Application application) {
        if (jobPosting.isProcessing() && application.getStatus().equals(Application.ApplicationStatus.PENDING) &&
                currentRoundFinished() && jobPosting.getNumOfPositions() > getHiredApplications().size()) {
            application.hired();
            return true;
        } else {
            return false;
        }
    }


}
