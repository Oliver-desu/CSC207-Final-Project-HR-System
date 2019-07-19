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

    private HashMap<String, Applicant> applicants;
    private HashMap<String, Interviewer> interviewers;
    private HashMap<String, HRGeneralist> generalists;
    private HashMap<String, HRCoordinator> coordinators;


    public UserPool() {

    }

    public void register(Applicant applicant) {

    }

    public void register(Interviewer interviewer) {

    }

    public void register(HRCoordinator coordinator) {

    }

    public void register(HRGeneralist generalist) {

    }

    public Applicant getApplicant(String id) {
        return null;
    }

    public Interviewer getInterviewer(String id) {
        return null;
    }

    public HRGeneralist getHRGeneralist(String id) {
        return null;
    }

    public HRCoordinator getHRCoordinator(String id) {
        return null;
    }

    public ArrayList<Applicant> getApplicants(ArrayList<String> ids) {
        return null;
    }

    public ArrayList<Interviewer> getInterviewers(ArrayList<String> ids) {
        return null;
    }

    public ArrayList<HRCoordinator> getHRCoordinators(ArrayList<String> ids) {
        return null;
    }

    public User getUser(UserType type, String userName) {
        return null;
    }

}
