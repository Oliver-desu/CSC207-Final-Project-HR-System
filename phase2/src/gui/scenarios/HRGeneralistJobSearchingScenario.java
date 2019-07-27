package gui.scenarios;

import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.JobPool;
import domain.storage.CompanyPool;
import domain.storage.UserPool;
import domain.user.HRGeneralist;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


public class HRGeneralistJobSearchingScenario extends Scenario {

    private JobPool jobPool;
    private CompanyPool companyPool;
    private HRGeneralist hrGeneralist;

    public HRGeneralistJobSearchingScenario(UserMenu userMenu, HRGeneralist hrGeneralist, JobPool jobPool, CompanyPool companyPool){
        super(userMenu, Scenario.LayoutMode.REGULAR);
        this.hrGeneralist = hrGeneralist;
        this.jobPool = jobPool;
        this.companyPool = companyPool;
    }

    public void init(){
        super.init();
        String id = this.hrGeneralist.getCompanyId();
        Company company = this.companyPool.getCompany(id);
        ArrayList<String> jobPostingIds = company.getJobPostingIds();
        ArrayList<JobPosting> jobPostings = this.jobPool.getJobPostingsByIds(jobPostingIds);
        FilterPanel<Object> leftFilterPanel = getFilterPanel(true);
        ArrayList<Object> jobs = new ArrayList<>();
        jobs.addAll(jobPostings);
        leftFilterPanel.setFilterContent(jobs);
        setListener(leftFilterPanel);
        leftFilterPanel.updateUI();

    }

    public void setListener(FilterPanel panel){
        panel.addSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e){
                try {
                    JobPosting object = (JobPosting) panel.getSelectObject();
                    setOutputText(object.toString());
                }
                catch (ClassCastException a ){
                    System.out.println("What you given is not JobPosting!!");
                }

            }
        });
    }

    public static void main(String[] args){
        UserPool userPool = new UserPool();
        JobPool jobPool = new JobPool();
        CompanyPool companyPool = new CompanyPool();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "sharon");
        HRGeneralist hrGeneralist = new HRGeneralist(hashMap, "111");
        userPool.register(hrGeneralist);

        HashMap<String, String> compHashMap = new HashMap<>();
        compHashMap.put("id", "111");
        compHashMap.put("generalistId", "sharon");
        companyPool.addCompany("111", new Company(compHashMap));

        HRGeneralistJobSearchingScenario hrGeneralistJobSearchingScenario = new HRGeneralistJobSearchingScenario(
                new UserMenu(), hrGeneralist, jobPool, companyPool
        );
        hrGeneralistJobSearchingScenario.exampleView();

    }

}
