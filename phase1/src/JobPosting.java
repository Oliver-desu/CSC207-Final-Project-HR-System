import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class JobPosting implements SearchObject {
    private LocalDate postDate;
    private LocalDate closeDate;
    private int numPositions;
    private String status;
    private JobDecidingProcess decidingProcess;
    private Company company;
    private static List<JobPosting> allJobPostings;


    public JobPosting(Company company, LocalDate postDate, LocalDate closeDate, int numPositions){
        this.company = company;
        this.postDate = postDate;
        this.closeDate = closeDate;
        this.numPositions = numPositions;
        this.decidingProcess = new JobDecidingProcess();
//        update allJobPostings
//        Oliver to YiChun: there should be a new method to add new JobPosting to the entire collection. instead of
//        adding it in the constructor.
    }

    public Company getCompany() {
        return company;
    }
    public String getStatus() {
        return status;
    }

    public List<Application> getAllApplications(){
        return this.decidingProcess.getAllApplications();
    }

    public List<Application> getRemainingApplications(){
        return this.decidingProcess.getRemainingApplications();
    }

    void nextRound(List<Application> applications){}

    public List<JobPosting> getAllJobPostings(){
        return null;
    }

    public List<Application> getNextRoundApplications(){
        return null;
    }

    @Override
    public String getSearchValue1() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }
}
