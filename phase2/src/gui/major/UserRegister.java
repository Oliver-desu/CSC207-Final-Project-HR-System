package gui.major;

import domain.storage.UserPool;
import domain.user.*;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class UserRegister extends Scenario {

    private RegisterType registerType;

    public UserRegister(UserMenu userMenu, RegisterType registerType) {
        super(userMenu, LayoutMode.REGISTER);
        this.registerType = registerType;
    }

    protected void initInput() {
        InputInfoPanel infoPanel = getInputInfoPanel();
        initUserInput(infoPanel);
        if (registerType.equals(RegisterType.APPLICANT)) initApplicantInput(infoPanel);
        else initStaffInput(infoPanel);
    }

    private void initUserInput(InputInfoPanel infoPanel) {
        infoPanel.addTextField("Username:");
        infoPanel.addPasswordField("Password");
        infoPanel.addPasswordField("Confirm Password:");
        infoPanel.addTextField("First name:");
        infoPanel.addTextField("Last/Family name:");
        infoPanel.addPasswordField("Age:");
        infoPanel.addPasswordField("Email Address:");
    }

    private void initApplicantInput(InputInfoPanel infoPanel) {
        String[] occupations = new String[]{"Student", "Worker", "Other"};
        infoPanel.addComboBox("Occupation:", occupations);
        String[] experiences = new String[]{"0-3 years", "3-5 years", "5-8 years", "8+ years"};
        infoPanel.addComboBox("Work experiences:", experiences);
        String[] diplomas = new String[]{"High School", "Bachelor", "Master", "Doctor", "Other"};
        infoPanel.addComboBox("Education background:", diplomas);
        String[] major = new String[]{
                "Math", "CompScience", "Economics", "Finance", "Education", "Art", "Engineer", "Statistics", "Other"
        };
        infoPanel.addComboBox("Major in:", major);
    }

    private void initStaffInput(InputInfoPanel infoPanel) {
        infoPanel.addTextField("Company id:");
        infoPanel.addTextField("Work id:");
        infoPanel.addTextArea("Verify message:");
    }

    protected void initButton() {
        addButton("Create User", new CreateUserListener());
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

    public enum RegisterType {
        APPLICANT, HR_COORDINATOR, HR_GENERALIST, INTERVIEWER
    }

    class CreateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserPool userPool = getMain().getUserPool();
            User user = createUser();
            UserMenu userMenu = getUserMenu();
            if (user.isNull()) JOptionPane.showMessageDialog(userMenu, "Incorrect input!");
            else {
                userPool.register(user);
                JOptionPane.showMessageDialog(userMenu, "Successfully registered!");
            }
        }
    }
}
