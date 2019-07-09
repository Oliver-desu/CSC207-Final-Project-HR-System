package domain.JobPostingStates;

import domain.Application;
import domain.Interviewer;

public interface JobPostingState {

    String getStatus();

    String hire(Application ap);

    String reject(Application ap);

    String matchInterview(Application application, Interviewer interviewer);
}
