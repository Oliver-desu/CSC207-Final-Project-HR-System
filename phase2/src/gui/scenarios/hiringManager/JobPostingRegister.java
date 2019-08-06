package gui.scenarios.hiringManager;

import domain.Enums.UserType;
import domain.Test;
import domain.job.JobPosting;
import domain.user.Company;
import domain.user.Employee;
import gui.major.MenuPanel;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ButtonPanel;
import gui.panels.ComponentFactory;
import gui.panels.InputInfoPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

/**
 * Class {@code JobPostingRegister} handles the situation where the hiring manager want to create a new job posting.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see gui.major.MenuPanel
 * @since 2019-08-05
 */
public class JobPostingRegister extends Scenario {

    /**
     * An {@code InputInfoPanel} that sets up gui in this scenario.
     *
     * @see #update()
     * @see #initInput()
     * @see #createJobInfoMap()
     * @see CreateJobPostingListener
     */
    private InputInfoPanel infoPanel;

    /**
     * Create a new {@code JobPostingRegister} that is a {@code Scenario} with title "Create Job Posting".
     *
     * @param userMenu the {@code userMenu} that sets up the gui framework
     * @see MenuPanel
     */
    public JobPostingRegister(UserMenu userMenu) {
        super(userMenu, "Create Job Posting");
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        test.addRecruitersForCompany(9, company);
        Employee hiringManager = test.getStorage().getEmployee(
                company.getHiringManagerId(), UserType.HIRING_MANAGER);

        UserMenu userMenu = new UserMenu(test.getMain(), hiringManager);
        new JobPostingRegister(userMenu).exampleView();
    }

    /**
     * Override {@code initComponents()} in abstract class {@code Scenario}.
     */
    @Override
    protected void initComponents() {
        initInput();
        initButton();
    }

    /**
     * Override {@code update()} in abstract class {@code Scenario}.
     */
    @Override
    protected void update() {
        infoPanel.clear();
    }

    /**
     * A helper method for {@code initComponents()} that initializes the {@code infoPanel}.
     *
     * @see #initComponents()
     */
    private void initInput() {
        infoPanel = new InputInfoPanel(REGISTER_INPUT_SIZE, true);
        ComponentFactory factory = infoPanel.getComponentFactory();
        String[] recruiters = getUserMenu().getCompany().getRecruiterIds().toArray(new String[0]);
        factory.addComboBox("Recruiter:", recruiters);
        factory.addTextField("Position name:");
        factory.addTextField("Num of positions:");
        factory.addTextField("Close date:");
        String[] documentsOption = new String[]{"Required", "Optional"};
        String[] extraDocumentsOption = new String[]{"Not allowed", "Allowed 1", "Allowed up to 3", "No restriction"};
        factory.addComboBox("CV:", documentsOption);
        factory.addComboBox("Cover letter:", documentsOption);
        factory.addComboBox("Reference:", documentsOption);
        factory.addComboBox("Extra document:", extraDocumentsOption);
        add(infoPanel);
    }

    /**
     * A helper method for {@code initComponents()} that initializes and add the new {@code ButtonPanel}.
     *
     * @see #initComponents()
     */
    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Post job", new CreateJobPostingListener());
        add(buttonPanel);
    }

    /**
     * Create a map that contains basic job information obtained from gui.
     * A helper function for {@code actionPerformed(ActionEvent)} in {@code CreateJobPostingListener}.
     *
     * @return a map that contains the basic information obtained from user interface
     * @see JobPostingRegister
     */
    private HashMap<String, String> createJobInfoMap() {
        HashMap<String, String> infoMap = infoPanel.getInfoMap();
        Company company = getUserMenu().getCompany();
        infoMap.put("Post date:", LocalDate.now().toString());
        infoMap.put("Company id:", company.getId());
        infoMap.put("Job id:", company.getId() + "--" + infoMap.get("Position name:") + "--" +
                LocalDateTime.now());
        return infoMap;
    }

    /**
     * Check and return whether an integer is valid, that is, whether it starts from a non-zero digit.
     *
     * @param integer the integer to be checked
     * @return true if and only if the integer does not begin with zero
     * @see #isValidJobInfoMap(HashMap)
     */
    private boolean isValidInt(String integer) {
        return integer.matches("[1-9][0-9]*");
    }

    /**
     * Check and return whether a date is valid, that is return whether the date is today or after today.
     *
     * @param date the date to be checked
     * @return true only when the date passed in is no earlier than now
     * @see #isValidJobInfoMap(HashMap)
     */
    private boolean isValidDate(String date) {
        try {
            return !LocalDate.parse(date).isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * A helper function for {@code actionPerformed(ActionEvent)} that checks validity and returns a message that will
     * show on gui indicating that corresponding information.
     *
     * @param map a hash map containing the information entered into gui
     * @return a message about which part of the information is missing; "Good" when the input is valid
     * @see CreateJobPostingListener
     */
    private String isValidJobInfoMap(HashMap<String, String> map) {
        if (map.containsValue("")) {
            return "Please fill all information";
        } else if (!isValidInt(map.get("Num of positions:"))) {
            return "Please type in right form of Number of positions";
        } else if (!isValidDate(map.get("Close date:"))) {
            return "Please type in right form of Close date";
        }
        return "Good";
    }

    /**
     * Class{@code CreateJobPostingListener} implements ActionListener. It deals with the situation in which the button
     * "Post job" is clicked.
     *
     * @author group 0120 of CSC207 summer 2019
     * @see #initButton()
     * @since 2019-08-05
     */
    class CreateJobPostingListener implements ActionListener {

        /**
         * Override {@code actionPerformed} in interface {@code ActionListener}.
         *
         * @param e the action event of clicking a button
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!confirmAction()) return;
            HashMap<String, String> values = createJobInfoMap();
            if (isValidJobInfoMap(values).equals("Good")) {
                JobPosting jobPosting = new JobPosting(values);
                getUserMenu().getCompany().addJobPostingId(jobPosting.getJobId());
                getMain().getStorage().getEmployee(
                        values.get("Recruiter:"), UserType.RECRUITER).addFile(jobPosting);
                getMain().getStorage().addJobPosting(jobPosting);
                showMessage("Successfully post job!");
                infoPanel.clear();
            } else {
                showMessage(isValidJobInfoMap(values));
            }
        }
    }
}
