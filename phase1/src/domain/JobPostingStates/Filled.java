package domain.JobPostingStates;

import domain.*;

import java.time.LocalDate;

public class Filled implements JobPostingState {

    private JobPosting jobPosting;

    public Filled(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    @Override
    public String getStatus() {
        return "filled";
    }


    @Override
    public String hire(Application ap) {
        return "You can not hire an applicant at this state.";
    }


    @Override
    public String matchInterview(String interviewer, Application application, LocalDate date) {
        return "You can not match interview at this state.";
    }
}
