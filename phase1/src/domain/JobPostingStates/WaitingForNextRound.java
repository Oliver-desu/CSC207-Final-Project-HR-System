package domain.JobPostingStates;

import domain.*;

import java.time.LocalDate;

public class WaitingForNextRound implements JobPostingState {

    private JobPosting jobPosting;

    public WaitingForNextRound(JobPosting jobPosting) {
        jobPosting.setUnmatchedApplicants(jobPosting.getRemainingApplications().size());
        this.jobPosting = jobPosting;
    }

    @Override
    public String getStatus() {
        return "waiting for next round";
    }


    @Override
    public String hire(Application ap) {
        return "You can not hire an applicant at this state.";
    }


    @Override
    public String matchInterview(String interviewer, Application application, LocalDate date) {
        this.jobPosting.decreaseUnmatchedApplicants();
        this.jobPosting.getCompany().assignInterview(interviewer, application, date);
        if (this.jobPosting.getUnmatchedApplicants() == 0) {
            this.jobPosting.setCurrentState(new Interviewing(this.jobPosting));
        }
        return "Successfully created interview.";
    }

}
