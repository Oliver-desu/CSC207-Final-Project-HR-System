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

    List<Interview> getNextRoundInterviews() {
        int roundIndex = this.interviewRounds.indexOf(this.currentRound);
        if (roundIndex < this.interviewRounds.size() - 1) {
            String nextRound = this.interviewRounds.get(roundIndex + 1);
            return this.interviews.get(nextRound);
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


