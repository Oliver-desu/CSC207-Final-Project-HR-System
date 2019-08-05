package domain.job;

import domain.Enums.ApplicationStatus;
import domain.Enums.JobPostingStatus;
import domain.applying.Application;
import domain.filter.Filterable;
import domain.show.ShowAble;
import domain.storage.Storage;
import domain.user.Company;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code JobPosting} contains open position (with their descriptions and requirements)
 * so that applicants who wish to work in this position may apply.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see InterviewRoundManager
 * @see JobPostingStatus
 * @since 2019-08-04
 */
public class JobPosting implements Filterable, Serializable, ShowAble {

    /**
     * A hash map that contains all the details for this job posting.
     * Keys are fixed and can be found in {@code JobPostingRegister}.
     *
     * @see gui.scenarios.hiringManager.JobPostingRegister
     * @see #toString()
     */
    private HashMap<String, String> jobDetails;

    /**
     * The {@code InterviewRoundManager} for this job posting that deals
     * with events related to interview rounds.
     * It should be {@code null} when the job posting is opened.
     *
     * @see InterviewRoundManager
     * @see InterviewRound
     * @see JobPostingStatus
     * @see #startProcessing()
     */
    private InterviewRoundManager interviewRoundManager;

    /**
     * An array list that contains all the applications applied for
     * this job posting initially.
     *
     * @see #applicationSubmit(Application, Storage)
     * @see #applicationCancel(Application, Storage)
     * @see #getApplications()
     */
    private ArrayList<Application> applications;

    /**
     * Keep track of the status of this {@code JobPosting}.
     *
     * @see JobPostingStatus
     * @see #getStatus()
     */
    private JobPostingStatus status;


    public JobPosting(HashMap<String, String> jobDetails) {
        this.jobDetails = jobDetails;
        this.applications = new ArrayList<>();
        this.status = JobPostingStatus.OPEN;
    }

    public InterviewRoundManager getInterviewRoundManager() {
        return interviewRoundManager;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public JobPostingStatus getStatus() {
        return status;
    }

    public String getJobId() {
        return jobDetails.get("Job id:");
    }

    int getNumOfPositions() {
        return Integer.parseInt(jobDetails.get("Num of positions:"));
    }

    private boolean shouldClose() {
        LocalDate closeDate;
        try {
            closeDate = LocalDate.parse(jobDetails.get("Close date:"));
        } catch (DateTimeParseException e) {
            return false;
        }
        return !closeDate.isBefore(LocalDate.now());
    }

    public void startProcessing() {
        if (status.equals(JobPostingStatus.OPEN) && shouldClose()) {
            this.interviewRoundManager = new InterviewRoundManager(this, applications);
        }
    }

    public void endJobPosting() {
        this.status = JobPostingStatus.FINISHED;
        for (Application application : interviewRoundManager.getRemainingApplications()) {
            application.setStatus(ApplicationStatus.REJECTED);
        }
        interviewRoundManager.updateRemainingApplications();
    }

    private boolean hasApplication(Application application) {
        for (Application app : applications) {
            if (app.getApplicantId().equals(application.getApplicantId())) {
                return true;
            }
        }
        return false;
    }

    public boolean applicationSubmit(Application application, Storage Storage) {
        if (!hasApplication(application) && status.equals(JobPostingStatus.OPEN)) {
            Company company = Storage.getCompany(jobDetails.get("Company id:"));
            company.receiveApplication(application);
            this.applications.add(application);
            return true;
        } else {
            return false;
        }
    }

    public void applicationCancel(Application application, Storage Storage) {
        applications.remove(application);
        Company company = Storage.getCompany(jobDetails.get("Company id:"));
        company.cancelApplication(application);
        interviewRoundManager.applicationCancel(application);
    }

    @Override
    public String toString() {
        return getInfoString("Company", jobDetails.get("Company id:")) +
                getInfoString("Position name", jobDetails.get("Position name:")) +
                getInfoString("Num of positions", jobDetails.get("Num of positions:")) +
                getInfoString("Post date", jobDetails.get("Post date:")) +
                getInfoString("Close date", jobDetails.get("Close date:")) +
                getInfoString("CV", jobDetails.get("CV:")) +
                getInfoString("Cover letter", jobDetails.get("Cover letter:")) +
                getInfoString("Reference", jobDetails.get("Reference:")) +
                getInfoString("Extra document", jobDetails.get("Extra document:")) +
                getInfoString("Status", status.toString());
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("company", jobDetails.get("Company id:"));
        map.put("position (no.)", jobDetails.get("Position name:") + "(" + jobDetails.get("Num of positions:") + ")");
        map.put("close date", jobDetails.get("Close date:"));
        return map;
    }

    // The following methods are only for testing!
    public void close() {
        status = JobPostingStatus.PROCESSING;
    }

    public void setInterviewRoundManager() {
        this.interviewRoundManager = new InterviewRoundManager(this, applications);
    }
}
