//package oldVersion;
//
//import domain.Applicant;
//import domain.Application;
//import domain.Interview;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//class JobDecidingProcess {
//    private HashMap<Applicant, Application> allApplications = new HashMap<>();
//    private ArrayList<String> interviewRounds;
//    private HashMap<String, ArrayList<Interview>> interviews = new HashMap<>();
//    private int currentRound = -1;
//
//    JobDecidingProcess(ArrayList<String> interviewRounds) {
//        this.interviewRounds = interviewRounds;
//        for (String round : interviewRounds) {
//            interviews.put(round, new ArrayList<>());
//        }
//    }
//
//    boolean isFinalRound() {
//        return currentRound == interviewRounds.size()-1;
//    }
//
//    void addApplication(Application application) {
//        this.allApplications.put(application.getApplicant(), application);
//    }
//
//    void withdrawApplication(Application application) {
//        allApplications.remove(application);
//    }
//
//    private void newInterview(Application application) {
//        interviews.get(getCurrentRound()).add(new Interview(application));
//    }
//
//    void nextRound() {
//        ArrayList<Application> passApplications = getPassApplications();
//        currentRound ++;
//        for (Application application : passApplications) {
//            this.newInterview(application);
//        }
//    }
//
//    ArrayList<Application> getPassApplications() {
//        if (currentRound < 0) {
//            return new ArrayList<>(this.allApplications.values());
//        } else {
//            ArrayList<Application> applications = new ArrayList<>();
//            for (Interview interview : getRoundInterviews(getCurrentRound())) {
//                if (!interview.isReject()) {
//                    applications.add(interview.getApplication());
//                }
//            }
//            return applications;
//        }
//    }
//
//    ArrayList<String> getInterviewRounds() {
//        return this.interviewRounds;
//    }
//
//    private String getNextRound() {
//        return interviewRounds.get(currentRound + 1);
//    }
//
//    ArrayList<Interview> getRoundInterviews(String round) {
//        if (round.equals("")) {
//            return new ArrayList<>();
//        } else {
//            return this.interviews.get(round);
//        }
//    }
//
//    ArrayList<Application> getAllApplications() {
//        return new ArrayList<>(allApplications.values());
//    }
//
//    String getCurrentRound() {
//        if (currentRound < 0) {
//            return "";
//        } else {
//            return interviewRounds.get(currentRound);
//        }
//    }
//
//    boolean hasApplicant(Applicant applicant) {
//        return allApplications.containsKey(applicant);
//    }
//
//
//}
//
//
