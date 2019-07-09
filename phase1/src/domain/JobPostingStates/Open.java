package domain.JobPostingStates;

import domain.Application;
import domain.Interviewer;
import domain.JobPosting;


public class Open implements JobPostingState {

    private JobPosting jobPosting;

    public Open(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }


    @Override
    public String getStatus() {
        return "open";
    }


    @Override
    public String hire(Application application) {
        return "You can not hire an applicant at this state";
    }

    @Override
    public String reject(Application application) {
        jobPosting.removeApplication(application);
        return "successfully reject" + application.getApplicantName();
    }

    @Override
    public String matchInterview(Application application, Interviewer interviewer) {
        return "Can't match Interview at this state";
    }

}

/*
    void fromOpen() {
        if (this.closeDate.isAfter(LocalDate.now())) {
            this.checkRemainingApplicants();
        }
    }
*/