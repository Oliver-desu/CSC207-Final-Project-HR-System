package gui.major;

import domain.storage.Company;
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
        infoPanel.addPasswordField("Password:");
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
        if (registerType.equals(RegisterType.APPLICANT)) return createApplicant(infoMap);
        else if (registerType.equals(RegisterType.HR_COORDINATOR)) return createCoordinator(infoMap);
        else if (registerType.equals(RegisterType.INTERVIEWER)) return createInterviewer(infoMap);
        else if (registerType.equals(RegisterType.HR_GENERALIST)) return createGeneralist(infoMap);
        return new NullUser();
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        if (!infoMap.get("Password:").isEmpty() &&
                getMain().getUserPool().getApplicant(infoMap.get("Username:")) == null) {
            return new Applicant(infoMap);
        } else {
            return new NullUser();
        }
    }

    private User createCoordinator(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                getMain().getUserPool().getHRCoordinator(infoMap.get("Username:")) == null) {
            Company company = getMain().getCompanyPool().getCompany(companyId);
            company.addHRCoordinatorId(infoMap.get("Username:"));
            return new HRCoordinator(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                getMain().getUserPool().getInterviewer(infoMap.get("Username:")) == null) {
            Company company = getMain().getCompanyPool().getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new Interviewer(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private User createGeneralist(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && !companyExists(companyId) &&
                getMain().getUserPool().getHRGeneralist(infoMap.get("Username:")) == null) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id", companyId);
            values.put("generalistId", infoMap.get("Username:"));
            getMain().getCompanyPool().addCompany(companyId, new Company(values));
            return new HRGeneralist(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return getMain().getCompanyPool().getCompany(companyId) != null;
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
            if (user.isNull()) JOptionPane.showMessageDialog(
                    userMenu, "Incorrect input or username already used by others!"
            );
            else {
                userPool.register(user);
                JOptionPane.showMessageDialog(userMenu, "Successfully registered!");
            }
        }
    }
}
