package domain.job;

import domain.applying.Application;
import domain.filter.Filterable;
import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.storage.Info;
import domain.storage.InfoHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobPosting implements Filterable, InfoHolder {

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


    public JobPosting() {
        this.currRound = -1;
        this.interviewRounds = new HashMap<>();
        this.applications = new HashMap<>();
    }

    public HashMap<String, Application> getApplicationMap() {
        return this.applications;
    }

    public ArrayList<Application> getApplications() {
        return new ArrayList<>(this.applications.values());
    }

    public ArrayList<Application> getCurrentRoundApplications() {
        InterviewRound round = this.interviewRounds.get(currRound);
        return round.getCurrentRoundApplications();
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
        return this.jobInfo.getSpecificInfo("Job id");
    }

    public Application getApplication(String applicationId) {
        return this.applications.get(applicationId);
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

    public boolean isOpen() {
        return this.status.equals(JobPostingStatus.OPEN);
    }

    public void start() {
        this.status = JobPostingStatus.OPEN;
    }

    public boolean nextRound() {
//        set to next round
        InterviewRound currentRound = interviewRounds.get(currRound);
        if (interviewRounds.containsKey(currRound + 1) &&
                (currentRound == null || currentRound.getStatus().equals(InterviewRound.InterviewRoundStatus.FINISHED))) {
            currRound += 1;
            interviewRounds.get(currRound).start(getRemainingApplications());
            return true;
        } else {
            return false;
        }
    }

//    public boolean isLastRound() {
//        return false;
//    }

    public void hire(Application application) {
        application.hired();
    }

    public void endJobPosting() {
        this.status = JobPostingStatus.FINISHED;
    }

    public boolean applicationSubmit(Application application, CompanyPool companyPool) {
        if (!this.applications.containsValue(application)) {
            Company company = companyPool.getCompany(this.jobInfo.getSpecificInfo("Company id"));
            company.receiveApplication(application);
            this.applications.put(application.getApplicantId(), application);
            return true;
        } else {
            return false;
        }
    }

    public boolean applicationCancel(Application application, CompanyPool companyPool) {
        for (String applicantId : this.applications.keySet()) {
            if (this.applications.get(applicantId).equals(application)) {
                Company company = companyPool.getCompany(this.jobInfo.getSpecificInfo("Company id"));
                company.cancelApplication(application);
                this.applications.remove(applicantId, application);
                return true;
            }
        }
        return false;
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
        return "Job id: " + this.jobInfo.getSpecificInfo("Job id") + "\n" +
                "Company id: " + this.jobInfo.getSpecificInfo("Company id") + "\n" +
                "Position name: " + this.jobInfo.getSpecificInfo("Position name:") + "\n" +
                "Num of positions:" + this.jobInfo.getSpecificInfo("Num of positions:") + "\n" +
                "Post date:" + this.jobInfo.getSpecificInfo("Post date") + "\n" +
                "Close date:" + this.jobInfo.getSpecificInfo("Close date:") + "\n" +
                "Status: " + this.status;
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
        values.add(this.jobInfo.getSpecificInfo("Job id"));
        values.add(this.jobInfo.getSpecificInfo("Position name:"));
        values.add(this.jobInfo.getSpecificInfo("Close date:"));
        return values.toArray(new String[0]);
    }
}
