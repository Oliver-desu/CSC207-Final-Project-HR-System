package domain.applying;

import domain.filter.Filterable;
import domain.user.Interviewer;

import java.util.ArrayList;
import java.util.List;

public class Interview implements Filterable, InfoHolder {

    public enum InterviewStatus {
        UNMATCHED,
        PENDING,
        PASS,
        FAIL
    }

    private Interviewer interviewer;
    private Application application;
    private InterviewStatus status = InterviewStatus.UNMATCHED;
    private Info interviewInfo;

    public Interview(Application application) {
        this.application = application;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public Application getApplication() {
        return application;
    }

    public boolean match(Interviewer interviewer, Info info) {
        if (status.equals(InterviewStatus.UNMATCHED)) {
            this.interviewer = interviewer;
            setInfo(info);
            setStatus(InterviewStatus.PENDING);
            interviewer.addInterview(this);
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
        interviewer.update(this);
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
        values.add(getInfo().getSpecificInfo("InterviewId"));
        values.add(getStatus().toString());
        return values.toArray(new String[3]);
    }
}
