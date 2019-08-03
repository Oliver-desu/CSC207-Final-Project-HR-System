package gui.scenarios.generalist;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.user.CompanyWorker;
import domain.user.User;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.ComponentFactory;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class JobPostingRegister extends Scenario {

    public JobPostingRegister(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGISTER);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        test.addCoordinatorsForCompany(9, company);
        CompanyWorker generalist = test.getInfoCenter().getCompanyWorker(
                company.getHRGeneralistId(), User.UserType.HR_GENERALIST);

        UserMenu userMenu = new UserMenu(test.getMain(), generalist);
        new JobPostingRegister(userMenu).exampleView();
    }

    protected InputInfoPanel initInput() {
        InputInfoPanel infoPanel = new InputInfoPanel(REGULAR_INPUT_SIZE);
        ComponentFactory factory = infoPanel.getComponentFactory();
        String[] coordinators = getUserMenu().getCompany().getHRCoordinatorIds().toArray(new String[0]);
        factory.addComboBox("Coordinator:", coordinators);
        factory.addTextField("Position name:");
        factory.addTextField("Num of positions:");
        factory.addTextField("Close date:");
        String[] documentsOption = new String[]{"Required", "Optional"};
        String[] extraDocumentsOption = new String[]{"Not allowed", "Allowed 1", "Allowed up to 3", "No restriction"};
        factory.addComboBox("CV:", documentsOption);
        factory.addComboBox("Cover letter:", documentsOption);
        factory.addComboBox("Reference:", documentsOption);
        factory.addComboBox("Extra document:", extraDocumentsOption);
        return infoPanel;
    }

    protected void initButton() {
        addButton("Post job", new CreateJobPostingListener());
    }

    private HashMap<String, String> createJobInfoMap() {
        HashMap<String, String> infoMap = getInputInfoMap();
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
            UserMenu userMenu = getUserMenu();
            int confirm = JOptionPane.showConfirmDialog(userMenu, "Are you sure to post job?");
            HashMap<String, String> values = createJobInfoMap();
            if (confirm == 0 && isValidJobInfoMap(values).equals("Good")) {
                JobPosting jobPosting = new JobPosting(values);
                getUserMenu().getCompany().addJobPostingId(jobPosting.getJobId());
                getMain().getInfoCenter().getCompanyWorker(
                        values.get("Coordinator:"), User.UserType.HR_COORDINATOR).addFile(jobPosting);
                getMain().getInfoCenter().addJobPosting(jobPosting.getJobId(), jobPosting);
                JOptionPane.showMessageDialog(userMenu, "Successfully post job!");
                getInputInfoPanel().clear();
            } else if (confirm == 0) {
                JOptionPane.showMessageDialog(userMenu, isValidJobInfoMap(values));
            }
        }
    }
}
