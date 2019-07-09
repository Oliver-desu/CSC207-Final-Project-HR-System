package domain.JobPostingStates;

import domain.Application;

public interface JobPostingState {

    String getStatus();

    void receiveApplication(Application ap);

    String hire(Application ap);

    String reject(Application ap);
}
