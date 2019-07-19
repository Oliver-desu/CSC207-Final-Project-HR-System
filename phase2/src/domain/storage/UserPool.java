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

    private static HashMap<String, Applicant> applicants;
    private static HashMap<String, Interviewer> interviewers;
    private static HashMap<String, HRGeneralist> generalists;
    private static HashMap<String, HRCoordinator> coordinators;


    public static void register(Applicant applicant) {

    }

    public static void register(Interviewer interviewer) {

    }

    public static void register(HRCoordinator coordinator) {

    }

    public static void register(HRGeneralist generalist) {

    }

    public static Applicant getApplicant(String id) {
        return null;
    }

    public static Interviewer getInterviewer(String id) {
        return null;
    }

    public static HRGeneralist getHRGeneralist(String id) {
        return null;
    }

    public static HRCoordinator getHRCoordinator(String id) {
        return null;
    }

    public static ArrayList<Applicant> getApplicants(ArrayList<String> ids) {
        return null;
    }

    public static ArrayList<Interviewer> getInterviewers(ArrayList<String> ids) {
        return null;
    }

    public static ArrayList<HRCoordinator> getHRCoordinators(ArrayList<String> ids) {
        return null;
    }

    public static User getUser(UserType type, String userName) {
        return null;
    }

}
