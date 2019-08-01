package domain.applying;

import domain.filter.Filterable;
import domain.storage.Info;
import domain.storage.InfoHolder;
import domain.user.CompanyWorker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Interview implements Filterable, InfoHolder, Serializable {

    public enum InterviewStatus {
        UNMATCHED,
        PENDING,
        PASS,
        FAIL
    }

    private CompanyWorker interviewer;
    private Application application;
    private InterviewStatus status = InterviewStatus.UNMATCHED;
    private Info interviewInfo;


    public Interview(Application application) {
        this.application = application;
    }

    public CompanyWorker getInterviewer() {
        return interviewer;
    }

    public Application getApplication() {
        return application;
    }

    public boolean match(CompanyWorker interviewer, Info info) {
        if (status.equals(InterviewStatus.UNMATCHED)) {
            this.interviewer = interviewer;
            setInfo(info);
            setStatus(InterviewStatus.PENDING);
            interviewer.addFile(this);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Info getInfo() {
        return interviewInfo;
    }

    @Override
    public void setInfo(Info info) {
        interviewInfo = info;
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

    private void notifyHolders() {
        application.update(this);
        interviewer.removeFile(this);
    }

    @Override
    public String toString() {
        if (this.status.equals(InterviewStatus.UNMATCHED)) {
            return "JobPosting id: " + this.application.getJobPostingId() + "\n" +
                    "Applicant: " + this.application.getApplicantId() + "\n" +
                    "Status: " + this.status;
        } else {
            return "JobPosting id: " + this.application.getJobPostingId() + "\n" +
                    "Applicant: " + this.application.getApplicantId() + "\n" +
                    "Interviewer:" + this.interviewer.getUsername() + "\n" +
                    "Time: " + this.interviewInfo.getSpecificInfo("Time") + "\n" +
                    "Duration(min): " + this.interviewInfo.getSpecificInfo("Duration(min)") + "\n" +
                    "Location: " + this.interviewInfo.getSpecificInfo("Location") + "\n" +
                    "Status: " + this.status;
        }
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
}
