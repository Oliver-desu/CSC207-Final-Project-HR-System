package domain.user;

import domain.applying.Application;
import domain.applying.DocumentManager;
import domain.applying.Interview;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Applicant extends User {
    private HashMap<String, Application> applications;
    private DocumentManager documentManager;

    public Applicant() {
    }

    public HashMap<String, Application> getApplicationMap() {
        return null;
    }

    public ArrayList<Application> getApplications() {
        return null;
    }

    public DocumentManager getDocumentManager() {
        return null;
    }

    public void addApplication(String jobId, Application application) {
    }

    public boolean deleteApplication(String jobId) {
        return false;
    }

    public boolean deleteApplication(Application application) {
        return false;
    }

    public ArrayList<Interview> getOngoingInterviews() {
        return null;
    }

    public void update(LocalDate currentDate) {
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }


}
