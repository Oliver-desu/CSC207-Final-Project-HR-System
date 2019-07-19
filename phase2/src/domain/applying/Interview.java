package domain.applying;

public class Interview {

    public enum InterviewStatus {
        UNMATCHED,
        PENDING,
        PASS,
        FAIL
    }

    private Application application;
    private String interviewerId;
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
        return this.interviewerId;
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

    public void match(String interviewerId, InterviewInfo interviewInfo) {
        this.interviewerId = interviewerId;
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

}
