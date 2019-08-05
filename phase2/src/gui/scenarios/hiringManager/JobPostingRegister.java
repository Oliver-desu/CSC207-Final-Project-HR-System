package gui.scenarios.hiringManager;

import domain.Enums.UserType;
import domain.Test;
import domain.job.JobPosting;
import domain.user.Company;
import domain.user.Employee;
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

public class JobPostingRegister extends Scenario {

    private InputInfoPanel infoPanel;

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

    @Override
    protected void initComponents() {
        initInput();
        initButton();
    }

    @Override
    protected void update() {
        infoPanel.clear();
    }

    protected void initInput() {
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

    protected void initButton() {
        ButtonPanel buttonPanel = new ButtonPanel(BUTTON_PANEL_SIZE);
        buttonPanel.addButton("Post job", new CreateJobPostingListener());
        add(buttonPanel);
    }

    private HashMap<String, String> createJobInfoMap() {
        HashMap<String, String> infoMap = infoPanel.getInfoMap();
        Company company = getUserMenu().getCompany();
        infoMap.put("Post date:", LocalDate.now().toString());
        infoMap.put("Company id:", company.getId());
        infoMap.put("Job id:", company.getId() + "--" + infoMap.get("Position name:") + "--" +
                LocalDateTime.now());
        return infoMap;
    }

    private boolean isValidInt(String integer) {
        return integer.matches("[1-9][0-9]*");
    }

    private boolean isValidDate(String date) {
        try {
            return !LocalDate.parse(date).isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

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

    class CreateJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!confirmAction()) return;
            HashMap<String, String> values = createJobInfoMap();
            if (isValidJobInfoMap(values).equals("Good")) {
                JobPosting jobPosting = new JobPosting(values);
                getUserMenu().getCompany().addJobPostingId(jobPosting.getJobId());
                getMain().getStorage().getEmployee(
                        values.get("Recruiter:"), UserType.RECRUITER).addFile(jobPosting);
                getMain().getStorage().addJobPosting(jobPosting.getJobId(), jobPosting);
                showMessage("Successfully post job!");
                infoPanel.clear();
            } else {
                showMessage(isValidJobInfoMap(values));
            }
        }
    }
}