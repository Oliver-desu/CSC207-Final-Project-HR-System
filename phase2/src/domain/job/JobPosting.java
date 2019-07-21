package domain.job;

import domain.applying.Application;
import domain.filter.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobPosting implements Filterable {

    public enum JobPostingStatus {
        OPEN,
        PROCESSING,
        FINISHED
    }

    private HashMap<String, Application> applications; //applicantId->submitted application
    private HashMap<Integer, InterviewRound> interviewRounds;
    private int currRound;
    private JobPostingStatus status;
    private JobInfo jobInfo;

    public JobPosting(JobInfo jobInfo) {
        this.jobInfo = jobInfo;
        this.currRound = 0;
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
        for (Application application : this.getCurrentRoundApplications()) {
            if (application.getStatus().equals(Application.ApplicationStatus.PENDING)) {
                remainingApplications.add(application);
            }
        }
        return remainingApplications;
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

    public JobInfo getJobInfo() {
        return this.jobInfo;
    }

    public String getJobId() {
        return this.jobInfo.getId();
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

    public void next() {
//        set to next round
        this.currRound += 1;
        this.interviewRounds.get(currRound).start();
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

    public void applicationSubmit(Application application) {
        if(!this.applications.containsValue(application)){
//            company.receiveApplication(application);
            this.applications.put(application.getApplicantId(), application);
        }
    }

    public void applicationCancel(Application application) {
        for (String applicantId : this.applications.keySet()) {
            if (this.applications.get(applicantId).equals(application)) {
//                company.cancelApplication(application);
                this.applications.remove(applicantId, application);
                break;
            }
        }
    }

    public String toString(Object object) {
        return null;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("id");
        headings.add("positionName");
        headings.add("closeDate");
        return (String[]) headings.toArray();
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.jobInfo.getId());
        values.add(this.jobInfo.getPositionName());
        values.add(this.jobInfo.getCloseDate().toString());
        return (String[]) values.toArray();
    }
}
