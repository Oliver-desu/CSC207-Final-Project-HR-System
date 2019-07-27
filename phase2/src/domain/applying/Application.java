package domain.applying;

import domain.storage.CompanyPool;
import domain.user.Applicant;
import domain.filter.Filterable;
import domain.job.JobPosting;
import domain.storage.JobPool;
import domain.storage.UserPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application implements Filterable {

    public enum ApplicationStatus {
        DRAFT,
        PENDING,
        HIRE,
        REJECTED
    }

    private HashMap<String, Interview> interviews;
    private String applicantId;
    private String jobPostingId;
    private DocumentManager documentManager;
    private ApplicationStatus status;


    public Application() {}

    public Application(HashMap<String, String> values) {
        this.interviews = new HashMap<>();
        this.applicantId = values.get("applicantId");
        this.jobPostingId = values.get("jobPostingId");
        this.documentManager = new DocumentManager(true);
        this.status = ApplicationStatus.DRAFT;
    }

    public HashMap<String, Interview> getInterviewMap() {
        return this.interviews;
    }

    public ArrayList<Interview> getInterviews() {
        return (ArrayList<Interview>) this.interviews.values();
    }

    public Interview getInterviewByRound(String round) {
        return this.interviews.get(round);
    }

    public String getApplicantId() {
        return this.applicantId;
    }

    public Applicant getApplicant(UserPool userPool) {
        return userPool.getApplicant(this.applicantId);
    }

    public String getJobPostingId() {
        return this.jobPostingId;
    }

    public JobPosting getJobPosting(JobPool jobPool) {
        return jobPool.getJobPosting(this.jobPostingId);
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

    public boolean apply(JobPool jobPool, CompanyPool companyPool) {
        // apply will ask JobPosting whether it is allowed to apply or not, and modify things if permitted, then return
        // whether succeeded or not
        boolean succeed = this.getJobPosting(jobPool).applicationSubmit(this, companyPool);
        if (succeed) {
            this.documentManager.setEditable(false);
            this.setStatus(ApplicationStatus.PENDING);
        }
        return succeed;
    }

    public boolean cancel(JobPool jobPool, CompanyPool companyPool) {
        // cancel will ask JobPosting whether it is allowed to cancel or not, and modify things if permitted, then
        // return whether succeeded or not
        if (!this.status.equals(ApplicationStatus.HIRE) && !this.status.equals(ApplicationStatus.REJECTED)) {
            boolean succeed = this.getJobPosting(jobPool).applicationCancel(this, companyPool);
            if (succeed) {
                this.documentManager.setEditable(true);
                this.setStatus(ApplicationStatus.DRAFT);
            }
            return succeed;
        } else {
            return false;
        }
    }

    public void hired() {
        this.setStatus(ApplicationStatus.HIRE);
    }

    public void rejected() {
        this.setStatus(ApplicationStatus.REJECTED);
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("applicantId");
        headings.add("jobPostingId");
        headings.add("status");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.applicantId);
        values.add(this.jobPostingId);
        values.add(this.status.toString());
        return values.toArray(new String[0]);
    }
}
