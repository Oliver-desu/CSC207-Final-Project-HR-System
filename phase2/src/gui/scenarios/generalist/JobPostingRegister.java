package gui.scenarios.generalist;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.Info;
import domain.user.HRGeneralist;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.InputInfoPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;

public class JobPostingRegister extends Scenario {

    public JobPostingRegister(UserMenu userMenu) {
        super(userMenu, LayoutMode.REGISTER);
    }

    public static void main(String[] args) {
        Test test = new Test();
        Company company = test.addCompany();
        test.addCoordinatorsForCompany(9, company);
        HRGeneralist generalist = test.getUserPool().getHRGeneralist(company.getHRGeneralistId());

        UserMenu userMenu = new UserMenu(test.getMain(), generalist);
        new JobPostingRegister(userMenu).exampleView();
    }

    protected void initInput() {
        InputInfoPanel infoPanel = getInputInfoPanel();
        String[] coordinators = getUserMenu().getCompany().getHRCoordinatorIds().toArray(new String[0]);
        infoPanel.addComboBox("Coordinator:", coordinators);
        infoPanel.addTextField("Position name:");
        infoPanel.addTextField("Num of positions:");
        infoPanel.addTextField("Close date:");
        String[] documentsOption = new String[]{"Required", "Optional"};
        String[] extraDocumentsOption = new String[]{"Not allowed", "Allowed 1", "Allowed up to 3", "No restriction"};
        infoPanel.addComboBox("CV:", documentsOption);
        infoPanel.addComboBox("Cover letter:", documentsOption);
        infoPanel.addComboBox("Reference:", documentsOption);
        infoPanel.addComboBox("Extra document:", extraDocumentsOption);
    }

    protected void initButton() {
        addButton("Post job", new CreateJobPostingListener());
    }

    private HashMap<String, String> createJobInfoMap() {
        HashMap<String, String> infoMap = getInputInfoMap();
        Company company = getUserMenu().getCompany();
        infoMap.put("Post date", LocalDate.now().toString());
        infoMap.put("Company id", company.getId());
        infoMap.put("Job id", company.getId() + "--" + infoMap.get("Position name:") + "--" +
                company.getJobPostingIds().size());
        return infoMap;
    }

    class CreateJobPostingListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserMenu userMenu = getUserMenu();
            int confirm = JOptionPane.showConfirmDialog(userMenu, "Are you sure to post job?");
            if (confirm == 0) {
                HashMap<String, String> values = createJobInfoMap();
                JobPosting jobPosting = new JobPosting();
                new Info(jobPosting, values);
                getUserMenu().getCompany().addJobPostingId(jobPosting.getJobId());
                getMain().getUserPool().getHRCoordinator(values.get("Coordinator:")).addJobPosting(jobPosting);
                getMain().getJobPool().addJobPosting(jobPosting.getJobId(), jobPosting);
                JOptionPane.showMessageDialog(userMenu, "Successfully post job!");
                getInputInfoPanel().clear();
            }
        }
    }
}
