package domain.JobPostingStates;

import domain.*;

import java.time.LocalDate;

public interface JobPostingState {

    String getStatus();

    String hire(Application ap);

    String matchInterview(String interviewer, Application application, LocalDate date);
}
