package domain.JobPostingStates;

import domain.Application;

public class Filled implements JobPostingState {
    @Override
    public String getStatus() {
        return null;
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
