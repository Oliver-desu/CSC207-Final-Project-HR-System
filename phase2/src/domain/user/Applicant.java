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

    public Applicant(HashMap<String, String> map) {
        super(map);
        this.applications = new HashMap<>();
        this.documentManager = new DocumentManager(true);
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

    /**
     * Delete an application before submitting it. **/
    public boolean deleteApplication(String jobId) {
        boolean isDeleted = false;
        if (this.applications.containsKey(jobId)) {
            this.applications.remove(jobId);
            isDeleted = true;
        }
        return isDeleted;
    }

    public boolean deleteApplication(Application application) {
        if (this.applications.containsValue(application)) {
            for (Map.Entry<String, Application> entry : this.applications.entrySet()) {
                if (application.equals(entry.getValue())) {
                    this.applications.remove(entry.getKey(), application);
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Interview> getOngoingInterviews() {
//        application->interview->status
        ArrayList<Interview> ongoingInterviews = new ArrayList<>();
        for(Application application: this.applications.values()){
            for(Interview interview: application.getInterviews()){
                if(interview.getStatus().equals(Interview.InterviewStatus.PENDING)){
                    ongoingInterviews.add(interview);
                }
            }
        }
      return ongoingInterviews;
    }

    public void update(LocalDate currentDate) {
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }


}
