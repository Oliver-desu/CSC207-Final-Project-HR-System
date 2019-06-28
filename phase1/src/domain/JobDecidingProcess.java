package domain;

import java.util.*;

class JobDecidingProcess {
    private List<Application> allApplications;
    private List<Application> remainingApplications;
    private List<String> interviewRounds;
    private Map<String, List<Interview>> interviews;
    private String currentRound;

    JobDecidingProcess() {
        this.allApplications = new ArrayList<>();
        this.remainingApplications = new ArrayList<>();
        this.interviewRounds = new ArrayList<>();
        this.interviews = new HashMap<>();
    }

    void setRounds(List<String> interviewRounds) {
        this.interviewRounds = interviewRounds;
    }

    void setCurrentRound(String currentRound) {
        this.currentRound = currentRound;
    }

    boolean checkLastRound() {
        return this.currentRound.equals(this.interviewRounds.get(this.interviewRounds.size() - 1));
    }

    void addApplication(Application application) {
        this.allApplications.add(application);
        this.remainingApplications.add(application);
    }

    void withdrawApplication(Application application) {
        this.remainingApplications.remove(application);
    }

    void withdrawApplication(List<Application> applications) {
        for (Application application : applications) {
            this.remainingApplications.remove(application);
        }
    }

    void addInterviews(Interview interview) {
        List<Interview> roundInterviews = this.interviews.get(interview.getRound());
        roundInterviews.add(interview);
        this.interviews.put(interview.getRound(), roundInterviews);
    }

    void addInterviews(ArrayList<Interview> interviews) {
        for (Interview interview : interviews) {
            List<Interview> roundInterviews = this.interviews.get(interview.getRound());
            roundInterviews.add(interview);
            this.interviews.put(interview.getRound(), roundInterviews);
        }
    }

    private String getNextRound() {
        int roundIndex = this.interviewRounds.indexOf(this.currentRound);
        if (roundIndex < this.interviewRounds.size() - 1) {
            return this.interviewRounds.get(roundIndex + 1);
        }
        return null;
    }

    void setRound(List<Application> applications) {
        for (Application application : applications) {
            application.setStatus(this.currentRound);
        }
    }

    List<Interview> getNextRoundInterviews() {
        if (this.getNextRound() != null) {
            return this.interviews.get(this.getNextRound());
        }
        return null;
    }

    List<Application> getAllApplications() {
        return allApplications;
    }

    List<Application> getRemainingApplications() {
        return remainingApplications;
    }

    String getCurrentRound() {
        return currentRound;
    }

}


