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
        this.workerIds.put(UserType.HIRING_MANAGER, new ArrayList<>());
        this.workerIds.put(UserType.RECRUITER, new ArrayList<>());
        this.workerIds.put(UserType.INTERVIEWER, new ArrayList<>());
        this.workerIds.get(UserType.HIRING_MANAGER).add(values.get("hiringManagerId"));
        this.jobPostingIds = new ArrayList<>();
        this.applications = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public String getHiringManagerId() {
        return this.workerIds.get(UserType.HIRING_MANAGER).get(0);
    }

    public ArrayList<String> getRecruiterIds() {
        return this.workerIds.get(UserType.RECRUITER);
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

    public void addRecruiterId(String id) {
        this.workerIds.get(UserType.RECRUITER).add(id);
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
