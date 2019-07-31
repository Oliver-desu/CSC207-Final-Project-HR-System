package domain.storage;

import domain.user.*;

import java.util.ArrayList;
import java.util.HashMap;

public class UserPool {

    private HashMap<String, Applicant> applicants = new HashMap<>();
    private HashMap<String, Interviewer> interviewers = new HashMap<>();
    private HashMap<String, HRGeneralist> generalists = new HashMap<>();
    private HashMap<String, HRCoordinator> coordinators = new HashMap<>();

    public void register(User user) {
        if (user instanceof Applicant) register((Applicant) user);
        else if (user instanceof HRCoordinator) register((HRCoordinator) user);
        else if (user instanceof HRGeneralist) register((HRGeneralist) user);
        else if (user instanceof Interviewer) register((Interviewer) user);
    }

    public void register(Applicant applicant) {
        applicants.put(applicant.getUsername(), applicant);
    }

    public void register(Interviewer interviewer) {
        interviewers.put(interviewer.getUsername(), interviewer);
    }

    public void register(HRCoordinator coordinator) {
        coordinators.put(coordinator.getUsername(), coordinator);
    }

    public void register(HRGeneralist generalist) {
        generalists.put(generalist.getUsername(), generalist);
    }

    public Applicant getApplicant(String username) {
        return applicants.get(username);
    }

    public Interviewer getInterviewer(String username) {
        return interviewers.get(username);
    }

    public HRGeneralist getHRGeneralist(String username) {
        return generalists.get(username);
    }

    public HRCoordinator getHRCoordinator(String username) {
        return coordinators.get(username);
    }

    public ArrayList<Applicant> getAllApplicants() {
        return new ArrayList<>(this.applicants.values());
    }

    public ArrayList<Interviewer> getInterviewers(ArrayList<String> usernameList) {
        ArrayList<Interviewer> tempInterviewers = new ArrayList<>();
        for (String username : usernameList) {
            tempInterviewers.add(getInterviewer(username));
        }
        return tempInterviewers;
    }

    public User getUser(UserType type, String username) {
        if (type.equals(UserType.APPLICANT) && applicants.containsKey(username)) {
            return getApplicant(username);
        } else if (type.equals(UserType.INTERVIEWER) && interviewers.containsKey(username)) {
            return getInterviewer(username);
        } else if (type.equals(UserType.HR_GENERALIST) && generalists.containsKey(username)) {
            return getHRGeneralist(username);
        } else if (type.equals(UserType.HR_COORDINATOR) && coordinators.containsKey(username)) {
            return getHRCoordinator(username);
        } else {
            return new NullUser();
        }
    }

    public enum UserType {
        APPLICANT,
        INTERVIEWER,
        HR_GENERALIST,
        HR_COORDINATOR
    }

}
