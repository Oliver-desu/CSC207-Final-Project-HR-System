package domain.storage;

import domain.user.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UserPool {

    public enum UserType {
        APPLICANT,
        INTERVIEWER,
        HRGENERALIST,
        HRCOORDINATOR
    }

    private static HashMap<String, Applicant> applicants = new HashMap<>();
    private static HashMap<String, Interviewer> interviewers = new HashMap<>();
    private static HashMap<String, HRGeneralist> generalists = new HashMap<>();
    private static HashMap<String, HRCoordinator> coordinators = new HashMap<>();


    public static void register(Applicant applicant) {
        applicants.put(applicant.getUsername(), applicant);
    }

    public static void register(Interviewer interviewer) {
        interviewers.put(interviewer.getUsername(), interviewer);
    }

    public static void register(HRCoordinator coordinator) {
        coordinators.put(coordinator.getUsername(), coordinator);
    }

    public static void register(HRGeneralist generalist) {
        generalists.put(generalist.getUsername(), generalist);
    }

    public static Applicant getApplicant(String id) {
        return applicants.get(id);
    }

    public static Interviewer getInterviewer(String id) {
        return interviewers.get(id);
    }

    public static HRGeneralist getHRGeneralist(String id) {
        return generalists.get(id);
    }

    public static HRCoordinator getHRCoordinator(String id) {
        return coordinators.get(id);
    }

    public static ArrayList<Applicant> getApplicants(ArrayList<String> ids) {
        ArrayList<Applicant> tempApplicants = new ArrayList<>();
        for (String id: ids) {
            tempApplicants.add(UserPool.getApplicant(id));
        }
        return tempApplicants;
    }

    public static ArrayList<Interviewer> getInterviewers(ArrayList<String> ids) {
        ArrayList<Interviewer> tempInterviewers = new ArrayList<>();
        for (String id: ids) {
            tempInterviewers.add(UserPool.getInterviewer(id));
        }
        return tempInterviewers;
    }

    public static ArrayList<HRCoordinator> getHRCoordinators(ArrayList<String> ids) {
        ArrayList<HRCoordinator> tempCoordinators = new ArrayList<>();
        for (String id: ids) {
            tempCoordinators.add(UserPool.getHRCoordinator(id));
        }
        return tempCoordinators;
    }

    public static User getUser(UserType type, String userName) {
        if (type.equals(UserType.APPLICANT) && applicants.containsKey(userName)) {
            return applicants.get(userName);
        } else if (type.equals(UserType.INTERVIEWER) && interviewers.containsKey(userName)) {
            return interviewers.get(userName);
        } else if (type.equals(UserType.HRGENERALIST) && generalists.containsKey(userName)) {
            return generalists.get(userName);
        } else if (type.equals(UserType.HRCOORDINATOR) && coordinators.containsKey(userName)) {
            return coordinators.get(userName);
        } else {
            // this will change to an empty new User later
            return null;
        }
    }

}
