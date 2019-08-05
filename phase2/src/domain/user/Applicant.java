package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;
import domain.applying.Application;
import domain.applying.DocumentManager;
import domain.applying.Interview;
import domain.show.ShowAble;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Applicant extends User implements Serializable, ShowAble {

    private HashMap<String, Application> applications;
    private DocumentManager documentManager;

    public Applicant(HashMap<String, String> map) {
        super(map, UserType.APPLICANT);
        this.applications = new HashMap<>();
        this.documentManager = new DocumentManager(true);
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(applications.values());
    }

    public DocumentManager getDocumentManager() {
        return this.documentManager;
    }

    public boolean addApplication(String jobId, Application application) {
        if (!this.applications.containsKey(jobId)) {
            this.applications.put(jobId, application);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteApplication(Application application) {
        String jobPostingId = application.getJobPostingId();
        for (String jobId : applications.keySet()) {
            if (jobId.equals(jobPostingId)) {
                applications.remove(jobPostingId);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Interview> getInterviews() {
//        application->interview->status
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application : this.applications.values()) {
            interviews.addAll(application.getInterviews());
        }
        return interviews;
    }

    @Override
    public String getCompanyId() throws NotCompanyWorkerException {
        throw new NotCompanyWorkerException();
    }

    @Override
    public String toString() {
        return getInfoString("Username", getUsername()) +
                getInfoString("Name", getRealName()) +
                getInfoString("Email", getUserDetail().get("Email:")) +
                getInfoString("Employment status", getUserDetail().get("Employment status:")) +
                getInfoString("Work experiences", getUserDetail().get("Work experiences:")) +
                getInfoString("Education background", getUserDetail().get("Education background:")) +
                getInfoString("Major in", getUserDetail().get("Major in:"));
    }
}
