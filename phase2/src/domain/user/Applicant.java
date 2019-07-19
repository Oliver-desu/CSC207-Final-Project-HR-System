package domain.user;

import domain.applying.Application;
import domain.applying.DocumentManager;
import domain.applying.Interview;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Applicant extends User {
    private HashMap<String, Application> applications;
    private DocumentManager documentManager;

    public Applicant(String username, String password, LocalDate dateCreated) {
        super(username, password, dateCreated);
    }

    public HashMap<String, Application> getApplicationMap() {
        return this.applications;
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(applications.values());
    }

    public DocumentManager getDocumentManager() {
        return this.documentManager;
    }

    public void addApplication(String jobId, Application application) {
        if (!this.applications.containsKey(jobId)) {
            this.applications.put(jobId, application);
        } else {
            throw new java.lang.Error("this.applications already contains JobId");
        }
    }

    public boolean deleteApplication(String jobId) {
        boolean isDeleted = false;
        if (this.applications.containsKey(jobId)) {
            this.applications.get(jobId).cancel(); //what application.cancel() does?
            this.applications.remove(jobId);
            isDeleted = true;
        }
        return isDeleted;
    }

    public boolean deleteApplication(Application application) {
        if (this.applications.containsValue(application)) {
            for (Map.Entry<String, Application> entry : this.applications.entrySet()) {
                if (application.equals(entry.getValue())) {
                    entry.getValue().cancel(); //cancel?
                    this.applications.remove(entry.getKey(), application);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Interview> getOngoingInterviews() {
//        application->interview->status
        return null;
    }

    public void update(LocalDate currentDate) {
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }


}
