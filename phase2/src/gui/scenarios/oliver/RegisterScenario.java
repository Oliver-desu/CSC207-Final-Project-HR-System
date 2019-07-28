package gui.scenarios.oliver;

import domain.storage.UserPool;
import domain.user.*;
import gui.major.Scenario;
import gui.major.UserMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class RegisterScenario extends Scenario {

    private RegisterType registerType;

    public RegisterScenario(UserMenu userMenu, RegisterType registerType) {
        super(userMenu, LayoutMode.REGISTER);
        this.registerType = registerType;
    }


    private User createUser() {
        HashMap<String, String> infoMap = getInputInfoMap();
        String companyId = infoMap.get("CompanyId:");
        if (registerType.equals(RegisterType.APPLICANT)) return new Applicant(infoMap);
        else if (registerType.equals(RegisterType.HR_COORDINATOR)) return new HRCoordinator(infoMap, companyId);
        else if (registerType.equals(RegisterType.HR_GENERALIST)) return new HRGeneralist(infoMap, companyId);
        else if (registerType.equals(RegisterType.INTERVIEWER)) return new Interviewer(infoMap, companyId);
        return new NullUser();
    }

    private enum RegisterType {
        APPLICANT, HR_COORDINATOR, HR_GENERALIST, INTERVIEWER
    }

    class CreateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserPool userPool = getMain().getUserPool();
            User user = createUser();
            if (user.isNull()) JOptionPane.showMessageDialog(getUserMenu(), "Incorrect input!");
            else {
//                userPool.register(user);
            }
        }
    }
}
