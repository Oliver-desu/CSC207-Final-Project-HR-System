package domain.job;

import domain.Enums.ApplicationStatus;
import domain.Enums.JobPostingStatus;
import domain.applying.Application;
import domain.filter.Filterable;
import domain.show.ShowAble;
import domain.storage.Company;
import domain.storage.InfoCenter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class JobPosting implements Filterable, Serializable, ShowAble {

    private HashMap<String, String> jobDetails;
    private InterviewRoundManager interviewRoundManager;
    private ArrayList<Application> applications;
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

    public boolean applicationSubmit(Application application, InfoCenter infoCenter) {
        if (!hasApplication(application) && status.equals(JobPostingStatus.OPEN)) {
            Company company = infoCenter.getCompany(jobDetails.get("Company id:"));
            company.receiveApplication(application);
            this.applications.add(application);
            return true;
        } else {
            return false;
        }
    }

    public void applicationCancel(Application application, InfoCenter infoCenter) {
        applications.remove(application);
        Company company = infoCenter.getCompany(jobDetails.get("Company id:"));
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
