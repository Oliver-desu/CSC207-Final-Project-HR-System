package domain.JobPostingStates;

import domain.Application;
import domain.JobPosting;

import java.time.LocalDate;

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
    public void receiveApplication(Application ap) {


    }

    @Override
    public String hire(Application ap) {
        return null;
    }

    @Override
    public String reject(Application ap) {
        return null;
    }
}
//
    void fromOpen() {
        if (this.closeDate.isAfter(LocalDate.now())) {
            this.checkRemainingApplicants();
        }
    }