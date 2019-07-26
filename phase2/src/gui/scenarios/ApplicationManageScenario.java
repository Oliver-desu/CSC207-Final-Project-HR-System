package gui.scenarios;

import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;

public class ApplicationManageScenario  extends Scenario {
    private  Applicant applicant;
    public ApplicationManageScenario(UserMenu userMenu, Applicant applicant) {

        super(userMenu, LayoutMode.REGULAR);
        this.applicant = applicant;
        System.out.println("credted ApplicationManageScenario");
    }
    public void init(){
        super.init();
        // # Todo : not finishted
    }
}
