package domain.applying;

import domain.Enums.ApplicationStatus;
import domain.Enums.InterviewStatus;
import domain.filter.Filterable;
import domain.job.JobPosting;
import domain.show.ShowAble;
import domain.storage.Storage;
import domain.user.Applicant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code Application} stores the information about a particular
 * application and deals with the whole applying procedure.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see     Interview
 * @see     DocumentManager
 * @see     ApplicationStatus
 * @since 2019-08-04
 */
public class Application implements Filterable, Serializable, ShowAble {
    /**
     * A hash map where the key is the name of interview round and
     * the value is the interview this applicant has during that round.
     *
     * @see     #getInterviews()
     * @see     #getInterviewByRound(String)
     * @see     #addInterview(String, Interview)
     */
    private HashMap<String, Interview> interviews = new HashMap<>();

    /**
     * The username of the applicant.
     *
     * @see     #getApplicantId()
     * @see     #getApplicant(Storage)
     */
    private String applicantId;

    /**
     * The id of the job posting.
     *
     * @see     JobPosting
     * @see     #getJobPostingId()
     */
    private String jobPostingId;

    /**
     * The document manager of this application.
     *
     * @see     DocumentManager
     * @see     #getDocumentManager()
     */
    private DocumentManager documentManager;

    /**
     * The status of this application.
     *
     * @see     ApplicationStatus
     * @see     #getStatus()
     * @see     #setStatus(ApplicationStatus)
     */
    private ApplicationStatus status;


    /**
     * Create a new application.
     * @param applicant     the applicant who applied
     * @param jobPosting    the job posting that is applied for
     */
    public Application(Applicant applicant, JobPosting jobPosting) {
        this.applicantId = applicant.getUsername();
        this.jobPostingId = jobPosting.getJobId();
        this.documentManager = new DocumentManager(true);
        this.status = ApplicationStatus.DRAFT;
    }

    /**
     * Return all the interviews this application holds.
     * @return All the interviews this application holds.
     */
    public ArrayList<Interview> getInterviews() {
        return new ArrayList<>(this.interviews.values());
    }

    /**
     * Return the interview corresponds to the round name.
     * @param round     the name of current round
     * @return the interview corresponds to the round name
     */
    public Interview getInterviewByRound(String round) {
        return this.interviews.get(round);
    }

    public String getApplicantId() {
        return this.applicantId;
    }

    /**
     * Return the {@code Applicant} who holds this application.
     * @param Storage    the {@code Storage} that contains all users
     * @return the {@code Applicant} who holds this application
     * @see     Applicant
     * @see     Storage
     */
    public Applicant getApplicant(Storage Storage) {
        return Storage.getApplicant(this.applicantId);
    }

    public String getJobPostingId() {
        return this.jobPostingId;
    }

    public DocumentManager getDocumentManager() {
        return this.documentManager;
    }

    public ApplicationStatus getStatus() {
        return this.status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    /**
     * Add the round, interview pair to {@code this.interviews}.
     * @param round     the round name
     * @param interview     the interview corresponds to round name
     */
    public void addInterview(String round, Interview interview) {
        this.interviews.put(round, interview);
    }

    /**
     * Ask the job posting whether it is allowed to apply or not. If allowed, set the document manager
     * to be uneditable and status to be {@code ApplicationStatus.PENDING}. Then return true
     * if and only if the application is submitted successfully.
     * @param Storage    the {@code Storage} that contains information about job postings
     * @return true if and only if the application is submitted successfully
     * @see     JobPosting#applicationSubmit(Application, Storage)
     * @see     DocumentManager#setEditable(boolean)
     */
    public boolean apply(Storage Storage) {
        boolean succeed = Storage.getJobPosting(jobPostingId).applicationSubmit(this, Storage);
        if (succeed) {
            this.documentManager.setEditable(false);
            this.setStatus(ApplicationStatus.PENDING);
        }
        return succeed;
    }

    /**
     * Cancel the application if and only if the status is {@code ApplicationStatus.PENDING}, then notify
     * the corresponding job posting and document manager. Return true if and only if application is
     * successfully cancelled.
     * @param Storage    the {@code Storage} that contains information about job postings
     * @return true if and only if application is successfully cancelled
     * @see     JobPosting#applicationCancel(Application, Storage)
     */
    public boolean cancel(Storage Storage) {
        if (this.status.equals(ApplicationStatus.PENDING)) {
            Storage.getJobPosting(jobPostingId).applicationCancel(this, Storage);
            this.documentManager.setEditable(true);
            this.setStatus(ApplicationStatus.DRAFT);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update the status of this application according to the result of interview. If interview failed,
     * then set status to {@code ApplicationStatus.REJECTED}, else do nothing and wait for future
     * interviews.
     * @param interview     the interview that will updates the status of this application
     */
    public void update(Interview interview) {
        if (interview.getStatus().equals(InterviewStatus.FAIL)) {
            this.setStatus(ApplicationStatus.REJECTED);
        }
    }

    /**
     * Return basic information about this application and detailed information about applicant.
     * @param Storage    the {@code Storage} that contains all users
     * @return basic information about this application and detailed information about applicant
     */
    public String detailedToStringForCompanyWorker(Storage Storage) {
        Applicant applicant = Storage.getApplicant(applicantId);
        return "JobPosting id:" + jobPostingId + "\n" +
                "Status: " + status + "\n" +
                "\n" +
                "Applicant information:\n" + applicant.toString();
    }

    /**
     * Overrides the method in interface {@code ShowAble}.
     * @return a string that contains basic information about this application
     * @see     ShowAble
     */
    @Override
    public String toString() {
        return getInfoString("Applicant", applicantId) +
                getInfoString("JobPosting", jobPostingId) +
                getInfoString("Status", status.toString());
    }

    /**
     * Return a hash map of headings and corresponding values about this application.
     * @return a hash map of headings and corresponding values about this application
     * @see     Filterable
     */
    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("applicant", getApplicantId());
        map.put("job posting", jobPostingId);
        map.put("status", status.toString());
        return map;
    }

}
