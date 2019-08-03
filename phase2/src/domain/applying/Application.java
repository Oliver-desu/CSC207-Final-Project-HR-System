package domain.applying;

import domain.filter.Filterable;
import domain.job.JobPosting;
import domain.storage.InfoCenter;
import domain.user.Applicant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application implements Filterable, Serializable {

    public enum ApplicationStatus {
        DRAFT,
        PENDING,
        HIRE,
        REJECTED
    }

    private HashMap<String, Interview> interviews = new HashMap<>();
    private String applicantId;
    private String jobPostingId;
    private DocumentManager documentManager;
    private ApplicationStatus status;


    public Application(Applicant applicant, JobPosting jobPosting) {
        this.applicantId = applicant.getUsername();
        this.jobPostingId = jobPosting.getJobId();
        this.documentManager = new DocumentManager(true);
        this.status = ApplicationStatus.DRAFT;
    }

    public ArrayList<Interview> getInterviews() {
        return new ArrayList<>(this.interviews.values());
    }

    public Interview getInterviewByRound(String round) {
        return this.interviews.get(round);
    }

    public String getApplicantId() {
        return this.applicantId;
    }

    public Applicant getApplicant(InfoCenter infoCenter) {
        return infoCenter.getApplicant(this.applicantId);
    }

    public String getJobPostingId() {
        return this.jobPostingId;
    }

    public JobPosting getJobPosting(InfoCenter infoCenter) {
        return infoCenter.getJobPosting(this.jobPostingId);
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

    public void addInterview(String round, Interview interview) {
        this.interviews.put(round, interview);
    }

    public boolean apply(InfoCenter infoCenter) {
        // apply will ask JobPosting whether it is allowed to apply or not, and modify things if permitted, then return
        // whether succeeded or not
        boolean succeed = this.getJobPosting(infoCenter).applicationSubmit(this, infoCenter);
        if (succeed) {
            this.documentManager.setEditable(false);
            this.setStatus(ApplicationStatus.PENDING);
        }
        return succeed;
    }

    public boolean cancel(InfoCenter infoCenter) {
        // cancel will ask JobPosting whether it is allowed to cancel or not, and modify things if permitted, then
        // return whether succeeded or not
        if (!this.status.equals(ApplicationStatus.HIRE) && !this.status.equals(ApplicationStatus.REJECTED)) {
            boolean succeed = this.getJobPosting(infoCenter).applicationCancel(this, infoCenter);
            if (succeed) {
                this.documentManager.setEditable(true);
                this.setStatus(ApplicationStatus.DRAFT);
            }
            return succeed;
        } else {
            return false;
        }
    }

    public void hire() {
        this.setStatus(ApplicationStatus.HIRE);
    }

    public void reject() {
        this.setStatus(ApplicationStatus.REJECTED);
    }

    public void update(Interview interview) {
        if (interview.getStatus().equals(Interview.InterviewStatus.FAIL)) {
            this.setStatus(ApplicationStatus.REJECTED);
        }
    }

    public String detailedToString(InfoCenter infoCenter) {
        Applicant applicant = infoCenter.getApplicant(applicantId);
        return "JobPosting id:" + jobPostingId + "\n" +
                "Status: " + status + "\n" +
                "\n" +
                "Applicant information:\n" + applicant.toString();
    }

    @Override
    public String toString() {
        return "Applicant: " + this.applicantId + "\n" +
                "JobPosting id:" + this.jobPostingId + "\n" +
                "Status: " + this.status;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("Applicant id");
        headings.add("JobPosting id");
        headings.add("Status");
        return headings.toArray(new String[3]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.applicantId);
        values.add(this.jobPostingId);
        values.add(this.status.toString());
        return values.toArray(new String[3]);
    }
}
