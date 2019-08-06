package domain.user;

import domain.Enums.InterviewStatus;
import domain.Enums.UserType;
import domain.Exceptions.NotEmployeeException;
import domain.applying.Application;
import domain.applying.DocumentManager;
import domain.applying.Interview;
import domain.show.ShowAble;

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
     * Constructor for {@code Applicant}.
     * @param map the hash map that stores the information about this {@code Applicant}
     * @see User#User(HashMap, UserType)
     */
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

    /**
     * Add the new {@code Application} to this {@code Applicant} and return true if succeeded.
     * Each {@code Applicant} can only have one {@code Application} for each {@code JobPosting}.
     * @param jobId the id for the {@code JobPosting}
     * @param application   the new {@code Application} needed to be added
     * @return true if and only if this {@code Applicant} does not already have an {@code Application}
     * for the same {@code JobPosting}
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
     * Delete {@code Application} from this {@code Applicant}.
     * @param application the {@code Application} that should be deleted from this {@code Applicant}
     * @return true if and only if deleted successfully
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
     * Return a list of interviews this {@code Application} has finished.
     * @return a list of interviews this {@code Application} has finished
     */
    public ArrayList<Interview> getPastInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application : this.applications.values()) {
            for (Interview interview : application.getInterviews()) {
                if (interview.getStatus().equals(InterviewStatus.PASS) ||
                        interview.getStatus().equals(InterviewStatus.FAIL)) {
                    interviews.add(interview);
                }
            }
        }
        return interviews;
    }

    /**
     * Return a list of interviews this {@code Application} currently has.
     *
     * @return a list of interviews this {@code Application} currently has
     */
    public ArrayList<Interview> getOngoingInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        for (Application application : this.applications.values()) {
            for (Interview interview : application.getInterviews()) {
                if (interview.getStatus().equals(InterviewStatus.UNMATCHED) ||
                        interview.getStatus().equals(InterviewStatus.PENDING)) {
                    interviews.add(interview);
                }
            }
        }
        return interviews;
    }

    /**
     * Override the method in {@code User}. {@code Applicant} does not work for a
     * company so if this method is called an exception will be thrown.
     * @return nothing since {@code Applicant} does not have company
     * @throws NotEmployeeException {@code Applicant} does not work for a company
     * @see User#getCompanyId()
     * @see Employee#getCompanyId()
     */
    @Override
    public String getCompanyId() throws NotEmployeeException {
        throw new NotEmployeeException();
    }

    /**
     * Overrides the method in interface {@code ShowAble}.
     * @return a string that contains basic information about this applicant
     * @see ShowAble
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
