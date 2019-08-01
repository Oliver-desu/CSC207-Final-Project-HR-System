package domain.job;

import domain.applying.Application;
import domain.filter.Filterable;
import domain.storage.Company;
import domain.storage.Info;
import domain.storage.InfoCenter;
import domain.storage.InfoHolder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobPosting implements Filterable, InfoHolder, Serializable {

    public enum JobPostingStatus {
        OPEN,
        PROCESSING,
        FINISHED
    }

    private HashMap<String, Application> applications; //applicantId->submitted application
    private HashMap<Integer, InterviewRound> interviewRounds;
    private int currRound;
    private JobPostingStatus status = JobPostingStatus.OPEN;
    private Info jobInfo;
    private int numHired;


    public JobPosting() {
        this.currRound = -1;
        this.numHired = 0;
        this.interviewRounds = new HashMap<>();
        this.applications = new HashMap<>();
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(this.applications.values());
    }

    public ArrayList<Application> getRemainingApplications() {
        ArrayList<Application> remainingApplications = new ArrayList<>();
        for (Application application : this.applications.values()) {
            if (application.getStatus().equals(Application.ApplicationStatus.PENDING)) {
                remainingApplications.add(application);
            }
        }
        return remainingApplications;
    }

    public String getJobId() {
        return this.jobInfo.getSpecificInfo("Job id:");
    }

    public Application getApplication(String applicationId) {
        return this.applications.get(applicationId);
    }

    public LocalDate getCloseDate() {
        try {
            return LocalDate.parse(jobInfo.getSpecificInfo("Close date:"));
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    public InterviewRound getCurrentInterviewRound() {
        return this.interviewRounds.get(this.currRound);
    }

    public ArrayList<InterviewRound> getAllInterviewRounds() {
        return new ArrayList<>(this.interviewRounds.values());
    }

    public JobPostingStatus getStatus() {
        return this.status;
    }

    public void addInterviewRound(InterviewRound interviewRound) {
        this.interviewRounds.put(this.interviewRounds.size(), interviewRound);
    }

    public boolean startProcessing() {
        if (status.equals(JobPostingStatus.OPEN) && !LocalDate.now().isBefore(getCloseDate())) {
            this.status = JobPostingStatus.PROCESSING;
            return true;
        } else {
            return false;
        }
    }

    // The following method is only for testing!
    public void close() {
        this.status = JobPostingStatus.PROCESSING;
    }

    public boolean nextRound() {
//        set to next round
        InterviewRound currentRound = interviewRounds.get(currRound);
        if (status.equals(JobPostingStatus.PROCESSING) && interviewRounds.containsKey(currRound + 1) &&
                (currentRound == null || currentRound.getStatus().equals(InterviewRound.InterviewRoundStatus.FINISHED))) {
            currRound += 1;
            interviewRounds.get(currRound).start(getRemainingApplications());
            return true;
        } else {
            return false;
        }
    }

    public boolean hire(Application application) {
        if (application.getStatus().equals(Application.ApplicationStatus.PENDING) &&
                status.equals(JobPostingStatus.PROCESSING) && getCurrentInterviewRound().isFinished() &&
                numHired < Integer.parseInt(jobInfo.getSpecificInfo("Num of positions:"))) {
            application.hired();
            numHired++;
            return true;
        } else {
            return false;
        }
    }

    public void endJobPosting() {
        this.status = JobPostingStatus.FINISHED;
        for (Application application : this.getRemainingApplications()) {
            application.setStatus(Application.ApplicationStatus.REJECTED);
        }
    }

    public boolean applicationSubmit(Application application, InfoCenter infoCenter) {
        if (!this.applications.containsValue(application) && status.equals(JobPostingStatus.OPEN)) {
            Company company = infoCenter.getCompany(this.jobInfo.getSpecificInfo("Company id:"));
            company.receiveApplication(application);
            this.applications.put(application.getApplicantId(), application);
            return true;
        } else {
            return false;
        }
    }

    public boolean applicationCancel(Application application, InfoCenter infoCenter) {
        if (!status.equals(JobPostingStatus.FINISHED)) {
            for (String applicantId : this.applications.keySet()) {
                if (this.applications.get(applicantId).equals(application)) {
                    Company company = infoCenter.getCompany(this.jobInfo.getSpecificInfo("Company id:"));
                    company.cancelApplication(application);
                    this.applications.remove(applicantId, application);
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public Info getInfo() {
        return jobInfo;
    }

    @Override
    public void setInfo(Info info) {
        this.jobInfo = info;
    }

    @Override
    public String toString() {
        String[] headings = this.jobInfo.getAllInfoHeadings(this);
        StringBuilder sb = new StringBuilder();
        for (String heading : headings) {
            sb.append(heading);
            sb.append(" ");
            sb.append(this.jobInfo.getSpecificInfo(heading));
            sb.append("\n");
        }
        return sb.toString() + "Status: " + this.status;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("jobId");
        headings.add("positionName");
        headings.add("closeDate");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.jobInfo.getSpecificInfo("Job id:"));
        values.add(this.jobInfo.getSpecificInfo("Position name:"));
        values.add(this.jobInfo.getSpecificInfo("Close date:"));
        return values.toArray(new String[0]);
    }
}
