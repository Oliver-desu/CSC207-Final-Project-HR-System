package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class JobPostingManager {
    //    HashMap<Company, HashMap<String, JobPosting>> jobPostings;  //(String) position -> (JobPosting) jobPosting
    private HashMap<String, ArrayList<JobPosting>> openJobPostings;
    private HashMap<String, ArrayList<JobPosting>> jobPostingsByCompany;
    private HashMap<LocalDate, ArrayList<JobPosting>> jobPostingsByCloseDate;
    private static JobPostingManager jm = null;

    private JobPostingManager() {
        this.openJobPostings = new HashMap<>();
        this.jobPostingsByCompany = new HashMap<>();
        this.jobPostingsByCloseDate = new HashMap<>();
    }

    public static JobPostingManager getInstance() {
        if (jm == null) {
            jm = new JobPostingManager();
        }
        return jm;
    }

    private void addToOpenJobPostings(JobPosting jobPosting) {
        if (jobPosting.getCurrentState().equals(null)) { //todo: change to open state(need to check open  in other two?)
            String position;
            ArrayList<JobPosting> lst = openJobPostings.get(position = jobPosting.getPosition());
            lst.add(jobPosting);
            openJobPostings.put(position, lst);
        }
    }

    private void addToJobPostingsByCompany(JobPosting jobPosting) {
        String company = jobPosting.getCompany().getCompanyName();
        ArrayList<JobPosting> lst = new ArrayList<>();
        if (jobPostingsByCompany.containsKey(company)) {
            lst = jobPostingsByCompany.get(company);
        }
        lst.add(jobPosting);
        this.jobPostingsByCompany.put(company, lst);
    }

    //    todo:handle duplicate
    private void addToJobPostingsByCloseDate(JobPosting jobPosting) {
        LocalDate closeDate = jobPosting.getCloseDate();
        ArrayList<JobPosting> lst = new ArrayList<>();
        if (jobPostingsByCloseDate.containsKey(closeDate)) {
            lst = jobPostingsByCloseDate.get(closeDate);
        }
        lst.add(jobPosting);
        this.jobPostingsByCloseDate.put(closeDate, lst);
    }

    void addJobPostings(JobPosting jobPosting) {
        addToOpenJobPostings(jobPosting);
        addToJobPostingsByCompany(jobPosting);
        addToJobPostingsByCloseDate(jobPosting);
        //if one position in this company already exist, does it need be able to add numPosition? (set a method changeJobPosting)
    }

    ArrayList<JobPosting> searchByCompany(String company) {
        if (jobPostingsByCompany.containsKey(company)) {
            return jobPostingsByCompany.get(company);
        }
        return null; //return null if no such key exist
    }

    ArrayList<JobPosting> searchByPosition(String position) {
        if (openJobPostings.containsKey(position)) {
            return openJobPostings.get(position);
        }
        return null; // return null if no such key
    }

}