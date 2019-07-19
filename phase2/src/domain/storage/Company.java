package domain.storage;

import domain.applying.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class Company {

    private String id;
    private String generalistId;
    private ArrayList<String> coordinatorIds;
    private ArrayList<String> interviewerIds;
    private ArrayList<String> jobPostingIds;
    private HashMap<String, ArrayList<Application>> applications;


    public Company() {

    }

    public String getId() {
        return null;
    }

    public String getHRGeneralistId() {
        return null;
    }

    public ArrayList<String> getHRCoordinatorIds() {
        return null;
    }

    public ArrayList<String> getInterviewerIds() {
        return null;
    }

    public ArrayList<String> getJobPostingIds() {
        return null;
    }

    public ArrayList<String> getApplicantsId() {
        return null;
    }

    public ArrayList<Application> getApplicationForApplicant(String applicantId) {
        return null;
    }

    public ArrayList<Application> getAllApplications() {
        return null;
    }

    public void addHRCoordinatorId(String id) {

    }

    public void addInterviewerId(String id) {

    }

    public void addJobPostingId(String id) {

    }

    public void addApplication(Application application) {

    }

    public void receiveApplication(Application application) {

    }

    public void cancelApplication(Application application) {

    }
}
