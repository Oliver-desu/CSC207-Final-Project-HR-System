package gui.major;

import domain.Enums.UserType;
import domain.Exceptions.*;
import domain.storage.UserFactory;
import domain.user.User;
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.InputInfoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class {@code UserRegister} setup a Scenario that deal with user register
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserType
 * @see InputInfoPanel
 * @see ButtonPanel
 * @see Scenario
 * @since 2019-08-05
 */
public class UserRegister extends Scenario {

    /**
     * The type of user that going to create
     *
     * @see UserType
     * @see #createUserAndRegister()
     * @see #initInput()
     */
    private UserType registerType;

    /**
     * The panel deal with all user input
     *
     * @see InputInfoPanel
     * @see #createUserAndRegister()
     * @see #initInput()
     */
    private InputInfoPanel infoPanel;

    /**
     * Constructor for new {@code UserRegister} ,
     *
     * @param userMenu     given usermenu
     * @param registerType given {@code UserType}
     */
    public UserRegister(UserMenu userMenu, UserType registerType) {
        super(userMenu, "User Register");
        this.registerType = registerType;
    }

    /**
     * Constructor for new {@code UserRegister}
     *
     * @param userMenu given usermenu
     */
    public UserRegister(UserMenu userMenu) {
        super(userMenu, "User Register");
    }

    /**
     * overrides the method in parent class{@code Scenario}, initial the inputInfoPanel and ButtonPanel
     */
    @Override
    protected void initComponents() {
        initInput();
        initButton();
    }

    /**
     * overrides the method in parent class{@code Scenario}, clear the text in the text area.
     * @see  Scenario#init()
     *
     */
    @Override
    protected void update() {
        infoPanel.clear();
    }

    /**
     * initial InputInfoPanel with given size , then initial all the components in the page ,
     * then if registerType is null , initial the register page for {@code Employee} , otherwise
     * initial the register page for {@code Applicant} , lastly add the page just initialed to
     * this frame
     */
    protected void initInput() {
        infoPanel = new InputInfoPanel(REGISTER_INPUT_SIZE, true);
        ComponentFactory factory = infoPanel.getComponentFactory();
        initUserInput(factory);
        if (registerType == null) initEmployeeInput(factory);
        else initApplicantInput(factory);
        add(infoPanel);
    }

    /**
     * Initial the information that need  {@code User} to be filled , include username , password,
     * first name , last name and email.
     * @param factory the factory that construct the page
     */
    private void initUserInput(ComponentFactory factory) {
        factory.addTextField("Username:");
        factory.addPasswordField("Password:");
        factory.addPasswordField("Confirm Password:");
        factory.addTextField("First name:");
        factory.addTextField("Last/Family name:");
        factory.addTextField("Email:");
    }

    /**
     * Initial the information that need  {@code Applicant} to be filled, include employmentStatus ,
     * working experiences,diplomas amd major
     * @param factory the factory that construct the page
     */
    private void initApplicantInput(ComponentFactory factory) {
        String[] employmentStatus = new String[]{"Student", "Employee", "Other"};
        factory.addComboBox("Employment status:", employmentStatus);
        String[] experiences = new String[]{"0-3 years", "3-5 years", "5-8 years", "8+ years"};
        factory.addComboBox("Work experiences:", experiences);
        String[] diplomas = new String[]{"High School", "Bachelor", "Master", "Doctor", "Other"};
        factory.addComboBox("Education background:", diplomas);
        String[] major = new String[]{
                "Math", "CompScience", "Economics", "Finance", "Education", "Art", "Engineer", "Statistics", "Other"
        };
        factory.addComboBox("Major in:", major);
    }

    /**
     * Initial the information that need  {@code Employee} to be filled , include the position and companyID.
     * @param factory the factory that construct the page
     */
    private void initEmployeeInput(ComponentFactory factory) {
        String[] positions = new String[]{"Interviewer", "Recruiter", "Hiring_Manager"};
        factory.addComboBox("Position:", positions);
        factory.addTextField("Company id:");
    }

    /**
     * initial the buttonPanel and create a new button called "create User" with a new {@code CreateUserListener}
     * @see  #initComponents()
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Create User", new CreateUserListener());
        add(buttonPanel);
    }

    private User createUserAndRegister()
            throws UnmatchedPasswordException, WrongEmailFormatException, UserAlreadyExistsException,
            CompanyAlreadyExistsException, CompanyDoesNotExistException {
        HashMap<String, String> infoMap = infoPanel.getInfoMap();
        infoMap.put("Password:", Arrays.toString(infoPanel.getPassword()));
        if (registerType == null) registerType = UserType.valueOf(infoMap.get("Position:").toUpperCase());
        return new UserFactory(getMain().getStorage()).createUser(infoMap, registerType);
    }

    /**
     * Class {@code CreateUserListener} Listener to register a new user
     *
     * @see UserRegister
     * @since 2019-08-05
     */
    class CreateUserListener implements ActionListener {
        /**
         * override the method in interface {ActionListener} ,
         * create a new user, if successfully created show a dialog "Successfully registered!" ,
         * if not  show a dialog "Incorrect input or username already used by others!"
         * @param e ActionEvent
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                createUserAndRegister();
                showMessage("Successfully registered!");
            } catch (UnmatchedPasswordException | WrongEmailFormatException | UserAlreadyExistsException |
                    CompanyAlreadyExistsException | CompanyDoesNotExistException e1) {
                showMessage(e1.getMessage());
            }
        }
    }
}
