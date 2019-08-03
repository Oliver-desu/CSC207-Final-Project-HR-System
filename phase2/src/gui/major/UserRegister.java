package gui.major;

import domain.storage.UserFactory;
import domain.user.User;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

public class UserRegister extends Scenario {

    private User.UserType registerType;

    public UserRegister(UserMenu userMenu, User.UserType registerType) {
        super(userMenu, LayoutMode.REGISTER);
        this.registerType = registerType;
    }

    protected InputInfoPanel initInput() {
        InputInfoPanel infoPanel = new InputInfoPanel();
        infoPanel.setup(REGISTER_INPUT_SIZE, true);
        initUserInput(infoPanel);
        if (registerType.equals(User.UserType.APPLICANT)) initApplicantInput(infoPanel);
        else initStaffInput(infoPanel);
        return infoPanel;
    }

    private void initUserInput(InputInfoPanel infoPanel) {
        infoPanel.addTextField("Username:");
        infoPanel.addPasswordField("Password:");
        infoPanel.addPasswordField("Confirm Password:");
        infoPanel.addTextField("First name:");
        infoPanel.addTextField("Last/Family name:");
        infoPanel.addTextField("Email Address:");
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

    private User createUserAndRegister() {
        HashMap<String, String> infoMap = getInputInfoMap();
        infoMap.put("Password:", Arrays.toString(getInputInfoPanel().getPassword()));
        return new UserFactory(getMain().getInfoCenter()).createUser(infoMap, registerType);
    }

    class CreateUserListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            User user = createUserAndRegister();
            UserMenu userMenu = getUserMenu();
            if (user.isNull()) {
                JOptionPane.showMessageDialog(userMenu, "Incorrect input or username already used by others!");
            } else {
                JOptionPane.showMessageDialog(userMenu, "Successfully registered!");
            }
        }
    }
}
