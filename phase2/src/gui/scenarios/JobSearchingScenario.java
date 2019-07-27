package gui.scenarios;

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
        addButton("CreatApplication",new JumptoApplicationManageScenarioButton());
       // ArrayList<String >AbbreviationOfJobPosting = AbbreviationOfJobPosting(jobPostings);

    }
    class JumptoApplicationManageScenarioButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String buttonName = e.getActionCommand();
            ApplicationManageScenario appm=  new ApplicationManageScenario(getUserMenu(),applicant);
            NullScenario aa = new NullScenario(getUserMenu());
            switchScenario(appm);

            // Todo:
            System.out.println("botton"); //used for test if successful hitted botton

        }
    }

    public  void setListner(FilterPanel panel){
        panel.addSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try {JobPosting object = (JobPosting) panel.getSelectObject();
                    setOutputText(object.toString());
                }
                catch (ClassCastException a){
                    System.out.println("what you given is not a Jobostingg ing ing ");
                }

            }
        });

    }

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("username", "oliver");
        Applicant applicant = new Applicant(hashMap);
        JobSearchingScenario jobSearchingScenario = new JobSearchingScenario(new UserMenu(), applicant, new JobPool());
        jobSearchingScenario.exampleView();
    }

}

