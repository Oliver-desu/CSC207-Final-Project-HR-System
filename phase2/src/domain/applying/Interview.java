package domain.applying;

import domain.Enums.InterviewStatus;
import domain.filter.Filterable;
import domain.show.ShowAble;
import domain.storage.Storage;
import domain.user.Employee;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class {@code Interview} stores information about an interview an
 * {@code Applicant} has with an {@code Interviewer}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see domain.user.Applicant
 * @see Employee
 * @since 2019-08-04
 */
public class Interview implements Filterable, Serializable, ShowAble {

    /**
     * The {@code Employee} that will do the interview.
     *
     * @see Employee
     * @see domain.Enums.UserType
     * @see #getInterviewer()
     * @see #match(Employee)
     */
    private Employee interviewer;

    /**
     * The {@code Application} which contains information about this
     * interviewee.
     *
     * @see Application
     * @see #getApplication()
     * @see #Interview(Application)
     */
    private Application application;

    /**
     * The recommendation interviewer has for the interviewee after the
     * interview has been finished.
     *
     * @see #setRecommendation(String)
     */
    private String recommendation;

    /**
     * The current status for this interview.
     *
     * @see InterviewStatus
     * @see #setStatus(InterviewStatus)
     * @see #getStatus()
     */
    private InterviewStatus status = InterviewStatus.UNMATCHED;

    /**
     * Constructor for {@code Interview}.
     *
     * @param application the {@code Application} whose holder is the interviewee
     * @see Application
     */
    public Interview(Application application) {
        this.application = application;
    }

    public Employee getInterviewer() {
        return interviewer;
    }

    public Application getApplication() {
        return application;
    }

    /**
     * Arrange the interview with the given interviewer, and return true if succeeded.
     * An interview can not be matched if its {@code status} is not {@code UNMATCHED}.
     * @param interviewer   the interviewer that will do this interview
     * @return true if and only if the interview is successfully matched
     * @see InterviewStatus
     * @see Employee#addFile(Object)
     */
    public boolean match(Employee interviewer) {
        if (status.equals(InterviewStatus.UNMATCHED)) {
            this.interviewer = interviewer;
            setStatus(InterviewStatus.PENDING);
            interviewer.addFile(this);
            return true;
        } else {
            return false;
        }
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public InterviewStatus getStatus() {
        return this.status;
    }

    /**
     * Update the {@code status} of this interview and notify all the holders.
     *
     * @param status the new {@code status} of this interview
     * @see #notifyHolders()
     * @see InterviewStatus
     */
    public void setStatus(InterviewStatus status) {
        this.status = status;
        notifyHolders();
    }

    /**
     * Cancel the interview if it has not ended yet. Remove from interviewer's list
     * if it has been arranged. This method is only used when the applicant wants
     * to cancel the application, and will not be used during the normal procedure.
     * @see InterviewStatus
     * @see domain.job.InterviewRound
     */
    public void cancel() {
        if (status.equals(InterviewStatus.PENDING)) {
            interviewer.removeFile(this);
            setStatus(InterviewStatus.FAIL);
        } else if (status.equals(InterviewStatus.UNMATCHED)) {
            setStatus(InterviewStatus.FAIL);
        }
    }

    /**
     * Notify all the holders and remove this from interviewer's list if interview is finished.
     *
     * @see Application#update(Interview)
     * @see InterviewStatus
     * @see Employee#removeFile(Object)
     */
    private void notifyHolders() {
        application.update(this);
        if (status.equals(InterviewStatus.FAIL) || status.equals(InterviewStatus.PASS)) {
            interviewer.removeFile(this);
        }
    }

    /**
     * Return basic information about this interview and detailed information about applicant.
     * @param Storage   the {@code Storage} that contains all users
     * @return basic information about this interview and detailed information about applicant
     */
    public String detailedToStringForCompanyWorker(Storage Storage) {
        return "JobPosting id: " + application.getJobPostingId() + "\n" +
                "\n" +
                "Applicant information:\n" + Storage.getApplicant(application.getApplicantId()).toString();
    }

    /**
     * Overrides the method in interface {@code ShowAble}.
     * @return a string that contains basic information about this application
     * @see ShowAble
     */
    @Override
    public String toString() {
        return getInfoString("JobPosting", application.getJobPostingId()) +
                getInfoString("Applicant", application.getApplicantId()) +
                getInfoString("Interviewer",
                        status.equals(InterviewStatus.UNMATCHED) ? "N/A" : interviewer.getUsername()) +
                getInfoString("Recommendation", recommendation) +
                getInfoString("Status", status.toString());
    }

    /**
     * Return a hash map of headings and corresponding values about this interview.
     * @return a hash map of headings and corresponding values about this interview
     * @see Filterable
     */
    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("applicant", getApplication().getApplicantId());
        map.put("interviewer", getInterviewer() == null ? "N/A" : interviewer.getUsername());
        map.put("status", status.toString());
        return map;
    }

}
