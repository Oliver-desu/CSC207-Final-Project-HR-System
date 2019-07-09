package domain.JobPostingStates;

import domain.Application;
import domain.JobPostingStates.JobPostingState;

public class WaitingForNextRound implements JobPostingState {
    @Override
    public String getStatus() {
        return null;
    }

    void fromWaitingForNextRound() {
        this.unmatchedApplicants --;
        if (this.unmatchedApplicants == 0) {
            this.currentState = JobPostingState.INTERVIEWING;
        }
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
