package domain.JobPostingStates;


import domain.*;

import java.time.LocalDate;


public class Pending implements JobPostingState {

    private JobPosting jobPosting;


    public Pending(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    @Override
    public String getStatus() {
        return "pending";
    }


    @Override
    public String hire(Application application) {
        application.setCurrentState("hired");
        this.jobPosting.removeApplication(application);
        this.jobPosting.addNumOfHired();

        if (this.jobPosting.getNumOfHired() == this.jobPosting.getNumOfPositions()) {
            this.jobPosting.setCurrentState(new Filled(this.jobPosting));
            this.jobPosting.getRemainingApplications().clear();
            return "Successfully hired. This job posting is now filled.";

        } else if (this.jobPosting.getNumOfHired() < this.jobPosting.getNumOfPositions() &&
                this.jobPosting.getRemainingApplications().isEmpty()) {
            this.jobPosting.setCurrentState(new Unfilled(this.jobPosting));
            return "Successfully hired. This job posting is unfilled due to lack of applicants.";
        }

        return "Successfully hired.";
    }


    @Override
    public String matchInterview(String interviewer, Application application, LocalDate date) {
        return "You can not match Interview at this state.";
    }

}

