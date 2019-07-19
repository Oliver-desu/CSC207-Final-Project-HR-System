package domain.applying;

public class Interview {

    public enum InterviewStatus {
        UNMATCHED,
        PENDING,
        PASS,
        FAIL
    }

    private Application application;
    private String interviewId;
    private InterviewStatus status;
    private String recommendation;


    public Interview() {

    }

    public Application getApplication() {
        return null;
    }

    public String getInterviewId() {
        return null;
    }

    public InterviewStatus getStatus() {
        return null;
    }

    public String getRecommendation() {
        return null;
    }

    public void setRecommendation(String recommendation) {

    }

    public void match(InterviewInfo interviewInfo) {

    }

    public void setPass() {

    }

    public void setFail() {

    }

    public String toString(Object object) {
        return null;
    }

    private void updateApplication() {

    }

}
