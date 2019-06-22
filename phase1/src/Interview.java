import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;

public class Interview implements SearchObject {

    private enum Status {
        PASS,
        REJECT,
        PENDING
    }

    private LocalDate date;
    private JobPosting jobPosting;
    private Interviewer interviewer;
    private Application application;
    private String round;
    private Status status = Status.PENDING;
    private String recommandation;

    public Interview(LocalDate date, JobPosting jobPosting, Interviewer interviewer, Application application,
                     String round) {
        this.date = date;
        this.jobPosting = jobPosting;
        this.interviewer = interviewer;
        this.application = application;
        this.round = round;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public JobPosting getJobPosting() {
        return this.jobPosting;
    }

    public Interviewer getInterviewer() {
        return this.interviewer;
    }

    public Application getApplication() {
        return this.application;
    }

    public String getRound() {
        return this.round;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getRecommandation() {
        return this.recommandation;
    }

    public void setRecommandation(String recommandation) {
        this.recommandation = recommandation;
    }

//    Unfinished methods:

    public String toStringForApplicant() {
        return null;
    }

    public String toStringForStaff() {
        return null;
    }

    public void passInterview() {

    }

    public void failInterview() {

    }

    @Override
    public String getSearchValue1() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }
}
