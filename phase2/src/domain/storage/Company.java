package domain.storage;

import domain.applying.Application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Company implements Serializable {

    private String id;
    private String generalistId;
    private ArrayList<String> coordinatorIds;
    private ArrayList<String> interviewerIds;
    private ArrayList<String> jobPostingIds;
    private HashMap<String, ArrayList<Application>> applications;


    public Company(HashMap<String, String> values) {
        this.id = values.get("id");
        this.generalistId = values.get("generalistId");
        this.coordinatorIds = new ArrayList<>();
        this.interviewerIds = new ArrayList<>();
        this.jobPostingIds = new ArrayList<>();
        this.applications = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getHRGeneralistId() {
        return this.generalistId;
    }

    public ArrayList<String> getHRCoordinatorIds() {
        return this.coordinatorIds;
    }

    public ArrayList<String> getInterviewerIds() {
        return this.interviewerIds;
    }

    public ArrayList<String> getJobPostingIds() {
        return this.jobPostingIds;
    }

    public ArrayList<Application> getAllApplications() {
        ArrayList<Application> allApplications = new ArrayList<>();
        for (String applicantId : applications.keySet()) {
            allApplications.addAll(applications.get(applicantId));
        }
        return allApplications;
    }

    public void addHRCoordinatorId(String id) {
        this.coordinatorIds.add(id);
    }

    public void addInterviewerId(String id) {
        this.interviewerIds.add(id);
    }

    public void addJobPostingId(String id) {
        this.jobPostingIds.add(id);
    }

    private void addApplication(Application application) {
        String applicantId = application.getApplicantId();
        if (!this.applications.containsKey(applicantId)) {
            this.applications.put(applicantId, new ArrayList<>());
        }
        this.applications.get(applicantId).add(application);
    }

    public void receiveApplication(Application application) {
        this.addApplication(application);
    }

    public void cancelApplication(Application application) {
        String applicantId = application.getApplicantId();
        if (this.applications.containsKey(applicantId)) {
            this.applications.get(applicantId).remove(application);
        }
        if (this.applications.get(applicantId).isEmpty()) {
            this.applications.remove(applicantId);
        }
    }
}
