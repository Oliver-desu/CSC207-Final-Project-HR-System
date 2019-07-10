package domain.JobPostingStates;

import domain.*;

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
    public String hire(Application application) {
        return "You can not hire an applicant at this state.";
    }


    @Override
    public String matchInterview(String interviewer, Application application, LocalDate date) {
        return "You can not match Interview at this state.";
    }

}

/*
    void fromOpen() {
        if (this.closeDate.isAfter(LocalDate.now())) {
            this.checkRemainingApplicants();
        }
    }
*/