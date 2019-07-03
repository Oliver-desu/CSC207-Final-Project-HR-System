package domain;

import java.util.ArrayList;
import java.util.HashMap;

class JobDecidingProcess {
    private HashMap<Applicant, Application> allApplications = new HashMap<>();
    private ArrayList<String> interviewRounds;
    private HashMap<String, ArrayList<Interview>> interviews = new HashMap<>();
    private int currentRound;

    JobDecidingProcess(ArrayList<String> interviewRounds) {
        this.interviewRounds = interviewRounds;
        for (String round : interviewRounds) {
            interviews.put(round, new ArrayList<>());
        }
    }

    boolean isFinalRound() {
        return currentRound == interviewRounds.size();
    }

    void addApplication(Application application) {
//        this.allApplications.put(application.getApplicant(), application);
    }

    void withdrawApplication(Application application) {
        allApplications.remove(application);
    }

    private void newInterview(Application application) {
        interviews.get(getCurrentRound()).add(new Interview(application));
    }

    void nextRound() {
        currentRound += 1;
        for (Application application : getPassApplications()) {
            newInterview(application);
        }
    }

    private ArrayList<Application> getPassApplications() {
        ArrayList<Application> applications = new ArrayList<>();
        for (Interview interview : getRoundInterviews(getCurrentRound())) {
            if (interview.isPass()) applications.add(interview.getApplication());
        }
        return applications;
    }

    ArrayList<String> getInterviewRounds() {
        return this.interviewRounds;
    }

    private String getNextRound() {
        return interviewRounds.get(currentRound + 1);
    }

    ArrayList<Interview> getRoundInterviews(String round) {
        return this.interviews.get(round);
    }

    ArrayList<Application> getAllApplications() {
        return new ArrayList<>(allApplications.values());
    }

    String getCurrentRound() {
        return interviewRounds.get(currentRound);
    }

    boolean hasApplicant(Applicant applicant) {
        return allApplications.containsKey(applicant);
    }


}


