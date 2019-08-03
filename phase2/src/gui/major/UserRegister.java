package gui.major;

import domain.storage.UserFactory;
import domain.user.User;
import gui.panels.ComponentFactory;
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
        InputInfoPanel infoPanel = new InputInfoPanel(REGISTER_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        initUserInput(factory);
        if (registerType.equals(User.UserType.APPLICANT)) initApplicantInput(factory);
        else initStaffInput(factory);
        return infoPanel;
    }

    private void initUserInput(ComponentFactory factory) {
        factory.addTextField("Username:");
        factory.addPasswordField("Password:");
        factory.addPasswordField("Confirm Password:");
        factory.addTextField("First name:");
        factory.addTextField("Last/Family name:");
        factory.addTextField("Email:");
    }

    private void initApplicantInput(ComponentFactory factory) {
        String[] occupations = new String[]{"Student", "Worker", "Other"};
        factory.addComboBox("Occupation:", occupations);
        String[] experiences = new String[]{"0-3 years", "3-5 years", "5-8 years", "8+ years"};
        factory.addComboBox("Work experiences:", experiences);
        String[] diplomas = new String[]{"High School", "Bachelor", "Master", "Doctor", "Other"};
        factory.addComboBox("Education background:", diplomas);
        String[] major = new String[]{
                "Math", "CompScience", "Economics", "Finance", "Education", "Art", "Engineer", "Statistics", "Other"
        };
        factory.addComboBox("Major in:", major);
    }

    private void initStaffInput(ComponentFactory factory) {
        factory.addTextField("Company id:");
        factory.addTextField("Work id:");
        factory.addTextArea("Verify message:");
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
