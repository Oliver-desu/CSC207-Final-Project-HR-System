package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;
import domain.applying.Application;
import domain.applying.DocumentManager;
import domain.applying.Interview;
import domain.show.ShowAble;
import domain.storage.Storage;
import gui.major.UserMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code Applicant} is a type of {@code User} that can apply to jobs.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserType
 * @see User
 * @since 2019-08-04
 */
public class Applicant extends User implements Serializable, ShowAble {

    /**
     * A hash map where the key is applicant's username and value is
     * the application submitted.
     *
     * @see Application
     * @see #getApplications()
     * @see #addApplication(String, Application)
     * @see #deleteApplication(Application)
     */
    private HashMap<String, Application> applications;

    /**
     * The document manager that manages all the documents.
     *
     * @see domain.applying.DocumentManager
     * @see #getDocumentManager()
     */
    private DocumentManager documentManager;

    /**
     * Create a new applicant
     *
     * @param map the map store the the username and password
     */
    public Applicant(HashMap<String, String> map) {
        super(map, UserType.APPLICANT);
        this.applications = new HashMap<>();
        this.documentManager = new DocumentManager(true);
    }

    /**
     * @return All applications stored in the applications.
     */
    public ArrayList<Application> getApplications() {
        return new ArrayList<>(applications.values());
    }

    /**
     * @return the documentManager of this applicant
     */
    public DocumentManager getDocumentManager() {
        return this.documentManager;
    }

    /**
     * @param jobId       a string represent  the job
     * @param application the application need to be added
     * @return true if  application is not exist in  this job  and to be added
     * successfully , false otherwise
     */
    public boolean addApplication(String jobId, Application application) {
        if (!this.applications.containsKey(jobId)) {
            this.applications.put(jobId, application);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param application the application needed to be deleted from this applicant
     * @return true if deleted successfully
     * @see null
     */
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

    /**
     *return a list of interviews of this application
     * @return a list of interviews of this
     * @see null
     */
    public ArrayList<Interview> getInterviews() {
        //        application->interview->status
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application : this.applications.values()) {
            interviews.addAll(application.getInterviews());
        }
        return interviews;
    }

    /**
     * return a string if  # Todo
     * @return a
     * @see Employee#getFilterMap()
     * @see  UserMenu#getCompany()
     */
    @Override
    public String getCompanyId() throws NotCompanyWorkerException {
        throw new NotCompanyWorkerException();
    }

    /**
     * return  a string contains username, realname, email,Employment status,Work experiences
     * Education background,Major of applicant.
     * @return a string contains username, realname, email,Employment status,Work experiences
     * Education background,Major of applicant.
     * @see   domain.applying.Application#detailedToStringForCompanyWorker(Storage)
     */
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
