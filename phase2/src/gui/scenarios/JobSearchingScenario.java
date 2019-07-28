package gui.scenarios;

import domain.applying.Application;
import domain.job.JobPosting;
import domain.storage.JobPool;
import domain.user.Applicant;
import gui.major.Scenario;
import gui.major.UserMenu;
import gui.panels.FilterPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class JobSearchingScenario extends  Scenario{
    private JobPool jobPool;
    private Applicant applicant;

    public   JobSearchingScenario(UserMenu userMenu, Applicant applicant, JobPool jobPool) {
        super(userMenu , Scenario.LayoutMode.REGULAR);
        this.applicant = applicant;
        this.jobPool = jobPool;
    }

    public  void  init(){
        super.init();
        FilterPanel<Object>  leftFilterPanel = getFilterPanel(true);
        ArrayList<Object> lst = new ArrayList<>();
        lst.addAll(this.jobPool.getJobPostings());
        leftFilterPanel.setFilterContent(lst);
        setListner(leftFilterPanel);
        leftFilterPanel.updateUI();
    }



    public  void setListner(FilterPanel panel){
        panel.addSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {
                    JobPosting object = (JobPosting) panel.getSelectObject(); //selected JobPosting
                    setOutputText(object.toString());
                    setButtonListener(object); //then pass this jobPosting to listener

                }
                catch (ClassCastException a){
                    System.out.println("what you given is not a JobPosting ");
                }
            }
        });

    }

    public void setButtonListener(JobPosting jobPosting) {
        addButton("CreateApplication", new JumptoApplicationManageScenarioButton(jobPosting));

    }

    class JumptoApplicationManageScenarioButton implements ActionListener {
        public JobPosting jobPosting;

        public JumptoApplicationManageScenarioButton(JobPosting jobPosting) {
            this.jobPosting = jobPosting;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Application application = new Application();
            applicant.addApplication(jobPosting.getJobId(), application);
            ApplicationManageScenario applicationManageScenario = new ApplicationManageScenario(getUserMenu(), applicant);
            switchScenario(applicationManageScenario);


        }
    }
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "oliver");
        Applicant applicant = new Applicant(hashMap);
        JobSearchingScenario jobSearchingScenario = new JobSearchingScenario(new UserMenu(), applicant, new JobPool());
        jobSearchingScenario.exampleView();
    }

}

