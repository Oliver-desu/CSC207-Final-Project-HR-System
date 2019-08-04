package domain.applying;

import domain.Enums.InterviewStatus;
import domain.filter.Filterable;
import domain.show.ShowAble;
import domain.storage.InfoCenter;
import domain.user.CompanyWorker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interview implements Filterable, Serializable, ShowAble {

    private CompanyWorker interviewer;
    private Application application;
    private String recommendation;
    private InterviewStatus status = InterviewStatus.UNMATCHED;

    public Interview(Application application) {
        this.application = application;
    }

    public CompanyWorker getInterviewer() {
        return interviewer;
    }

    public Application getApplication() {
        return application;
    }

    public boolean match(CompanyWorker interviewer) {
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

    private void setStatus(InterviewStatus status) {
        this.status = status;
        notifyHolders();
    }

    public void setResult(boolean isPass) {
        if (isPass) setStatus(InterviewStatus.PASS);
        else setStatus(InterviewStatus.FAIL);
    }

    public void cancel() {
        if (status.equals(InterviewStatus.PENDING)) {
            interviewer.removeFile(this);
            setResult(false);
        }
    }

    private void notifyHolders() {
        application.update(this);
        interviewer.removeFile(this);
    }

    public String detailedToStringForCompanyWorker(InfoCenter infoCenter) {
        return "JobPosting id: " + application.getJobPostingId() + "\n" +
                "\n" +
                "Application information:\n" + infoCenter.getApplicant(application.getApplicantId()).toString();
    }

    @Override
    public String toString() {
        return getInfoString("JobPosting", application.getJobPostingId()) +
                getInfoString("Applicant", application.getApplicantId()) +
                getInfoString("Interviewer",
                        status.equals(InterviewStatus.UNMATCHED) ? "N/A" : interviewer.getUsername()) +
                getInfoString("Recommendation", recommendation) +
                getInfoString("Status", status.toString());
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("Applicant id");
        headings.add("Interviewer id");
        headings.add("Status");
        return headings.toArray(new String[3]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(getApplication().getApplicantId());
        if (getInterviewer() == null) {
            values.add("no interviewer");
        } else {
            values.add(getInterviewer().getUsername());
        }
        values.add(getStatus().toString());
        return values.toArray(new String[3]);
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("applicant", getApplication().getApplicantId());
        map.put("interviewer", getInterviewer() == null ? "N/A" : interviewer.getUsername());
        map.put("status", status.toString());
        return map;
    }

}
