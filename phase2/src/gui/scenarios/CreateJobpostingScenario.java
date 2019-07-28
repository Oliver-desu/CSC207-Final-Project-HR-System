package gui.scenarios;

import domain.job.JobInfo;
import domain.storage.CompanyPool;
import domain.user.HRGeneralist;
import domain.storage.Company;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateJobpostingScenario extends Scenario {

    private HRGeneralist hrGeneralist;
    private CompanyPool companyPool;

    public CreateJobpostingScenario(UserMenu userMenu, HRGeneralist hrGeneralist) {
        super(userMenu, LayoutMode.REGULAR);
        this.hrGeneralist = hrGeneralist;
    }

    public static void main(String[] args){
        HRGeneralist hrGeneralist = new HRGeneralist();
        CreateJobpostingScenario createJobpostingScenario = new CreateJobpostingScenario(new UserMenu(), hrGeneralist);
        createJobpostingScenario.exampleView();


    }

    public void init(){
        super.init();
        FilterPanel<Object> filterPanel = getFilterPanel(true);
        String id = this.hrGeneralist.getCompanyId();
        Company company = companyPool.getCompany(id);
        ArrayList<String>  coordinators = company.getHRCoordinatorIds();
        ArrayList<Object> filterContent = new ArrayList<>(coordinators);
        filterPanel.setFilterContent(filterContent);
        setListener(filterPanel);
        super.getInputInfoPanel();

    }


    public void setListener(FilterPanel filterPanel) {
        filterPanel.addSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                    String object = (String) filterPanel.getSelectObject();
                    setButtonListener(object);
                }
                catch (ClassCastException a){
                    System.out.println("Something went wrong");
                }
            }
        });

    }

    public void setButtonListener(String coordinators){
        addButton("Create", new createJob(coordinators));
    }

    class createJob implements ActionListener{
        public String coordinators;

        public createJob(String coordinators){this.coordinators = coordinators;}

        public void actionPerformed(ActionEvent e){
            //TODO

        }


    }



}
