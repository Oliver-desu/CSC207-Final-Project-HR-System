package gui.scenarios;

import gui.major.Scenario;
import gui.major.UserMenu;

public class RegisterScenario extends Scenario {

    public RegisterScenario(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGISTER);
    }

    private enum RegisterType {
        APPLICANT, HR_COORDINATOR, HR_GENERALIST, INTERVIEWER, INTERVIEW, JOB_POSTING, APPLICATION
    }

}
