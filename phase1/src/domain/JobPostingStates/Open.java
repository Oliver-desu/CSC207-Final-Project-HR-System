package domain.JobPostingStates;

import domain.Application;
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
    public void receiveApplication(Application application) {
       jobPosting.getApplications().get("waiting for next round").add(application);
    }

    @Override
    public String hire(Application application) {
        return "You can not hire an applicant at this state";
    }

    @Override
    public String reject(Application application) {
        jobPosting.removeApplicant(application);
        return "successfully reject" + application.getApplicantName();
    }
}

/*
    void fromOpen() {
        if (this.closeDate.isAfter(LocalDate.now())) {
            this.checkRemainingApplicants();
        }
    }
*/