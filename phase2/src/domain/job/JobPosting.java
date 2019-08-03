package domain.job;

import domain.applying.Application;
import domain.filter.Filterable;
import domain.storage.Company;
import domain.storage.InfoCenter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobPosting implements Filterable, Serializable {

    public enum JobPostingStatus {
        OPEN,
        PROCESSING,
        FINISHED
    }

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

    public int getNumOfPositions() {
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

    public boolean isOpen() {
        return status.equals(JobPostingStatus.OPEN);
    }

    public boolean isProcessing() {
        return status.equals(JobPostingStatus.PROCESSING);
    }

    public boolean startProcessing() {
        if (isOpen() && shouldClose()) {
            this.interviewRoundManager = new InterviewRoundManager(this, applications);
            return true;
        } else {
            return false;
        }
    }

    public void endJobPosting() {
        this.status = JobPostingStatus.FINISHED;
        for (Application application : interviewRoundManager.getRemainingApplications()) {
            application.setStatus(Application.ApplicationStatus.REJECTED);
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

    public boolean applicationCancel(Application application, InfoCenter infoCenter) {
        if (!status.equals(JobPostingStatus.FINISHED)) {
            boolean contain = applications.remove(application);
            if (contain) {
                Company company = infoCenter.getCompany(jobDetails.get("Company id:"));
                company.cancelApplication(application);
            }
            return contain;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String[] titles = {"Company id:", "Position name:", "Num of positions:", "Post date:", "Close date:",
                "CV:", "Cover letter:", "Reference:", "Extra document:"};
        StringBuilder sb = new StringBuilder();
        for (String title : titles) {
            sb.append(title);
            sb.append(" ");
            sb.append(jobDetails.get(title));
            sb.append("\n");
        }
        return sb.toString() + "Status: " + status;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("companyId");
        headings.add("positionName");
        headings.add("closeDate");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(jobDetails.get("Company id:"));
        values.add(jobDetails.get("Position name:"));
        values.add(jobDetails.get("Close date:"));
        return values.toArray(new String[0]);
    }


    // The following methods are only for testing!
    public void close() {
        status = JobPostingStatus.PROCESSING;
    }

    public void setInterviewRoundManager() {
        this.interviewRoundManager = new InterviewRoundManager(this, applications);
    }
}
