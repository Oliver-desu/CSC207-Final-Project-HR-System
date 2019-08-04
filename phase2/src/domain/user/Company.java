package domain.user;

import domain.Enums.UserType;
import domain.applying.Application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Company implements Serializable {

    private String id;
    private HashMap<UserType, ArrayList<String>> workerIds;
    private ArrayList<String> jobPostingIds;
    private HashMap<String, ArrayList<Application>> applications;


    public Company(HashMap<String, String> values) {
        this.id = values.get("id");
        this.workerIds = new HashMap<>();
        this.workerIds.put(UserType.HR_GENERALIST, new ArrayList<>());
        this.workerIds.put(UserType.HR_COORDINATOR, new ArrayList<>());
        this.workerIds.put(UserType.INTERVIEWER, new ArrayList<>());
        this.workerIds.get(UserType.HR_GENERALIST).add(values.get("generalistId"));
        this.jobPostingIds = new ArrayList<>();
        this.applications = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getHRGeneralistId() {
        return this.workerIds.get(UserType.HR_GENERALIST).get(0);
    }

    public ArrayList<String> getHRCoordinatorIds() {
        return this.workerIds.get(UserType.HR_COORDINATOR);
    }

    public ArrayList<String> getInterviewerIds() {
        return this.workerIds.get(UserType.INTERVIEWER);
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
        this.workerIds.get(UserType.HR_COORDINATOR).add(id);
    }

    public void addInterviewerId(String id) {
        this.workerIds.get(UserType.INTERVIEWER).add(id);
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
