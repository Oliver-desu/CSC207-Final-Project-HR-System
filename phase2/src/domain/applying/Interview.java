package domain.applying;

import domain.filter.Filterable;

import java.util.ArrayList;
import java.util.List;

public class Interview implements Filterable {

    public enum InterviewStatus {
        UNMATCHED,
        PENDING,
        PASS,
        FAIL
    }

    private Application application;
    private InterviewStatus status;
    private String recommendation;
    private InterviewInfo interviewInfo;


    public Interview(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return this.application;
    }

    public String getInterviewId() {
        return this.interviewInfo.getInterviewerId();
    }

    public InterviewStatus getStatus() {
        return this.status;
    }

    public String getRecommendation() {
        return this.recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public void match(InterviewInfo interviewInfo) {
        this.interviewInfo = interviewInfo;
    }

    public void setPass() {
        this.status = InterviewStatus.PASS;
    }

    public void setFail() {
        this.status = InterviewStatus.FAIL;
        this.application.setStatus(Application.ApplicationStatus.REJECTED);
    }

//    private void updateApplication() {
//
//    }


    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("applicantId");
        headings.add("interviewerId");
        headings.add("status");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.application.getApplicantId());
        values.add(this.interviewInfo.getInterviewerId());
        values.add(this.status.toString());
        return values.toArray(new String[0]);
    }
}
