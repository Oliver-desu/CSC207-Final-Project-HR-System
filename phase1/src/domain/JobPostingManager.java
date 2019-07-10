package domain;

import domain.JobPostingStates.WaitingForNextRound;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class JobPostingManager{

    private static HashMap<String, JobPosting> jobPostings = new HashMap<>();
    private static HashMap<String, ArrayList<JobPosting>> jobPostingsByPosition = new HashMap<>();
    private static HashMap<String, ArrayList<JobPosting>> jobPostingsByCompany = new HashMap<>();
    private static HashMap<LocalDate, ArrayList<JobPosting>> jobPostingsByCloseDate = new HashMap<>();


    public static void addToJobPostingsByPosition(JobPosting jobPosting) {
        if (!jobPostingsByPosition.containsKey(jobPosting.getPosition())) {
            jobPostingsByPosition.put(jobPosting.getPosition(), new ArrayList<>());
        }
        jobPostingsByPosition.get(jobPosting.getPosition()).add(jobPosting);    }

    public static void addToJobPostingsByCompany(JobPosting jobPosting) {
        if (!jobPostingsByCompany.containsKey(jobPosting.getCompany().getCompanyName())) {
            jobPostingsByCompany.put(jobPosting.getCompany().getCompanyName(), new ArrayList<>());
        }
        jobPostingsByCompany.get(jobPosting.getCompany().getCompanyName()).add(jobPosting);
    }

    public static void addToJobPostingsByCloseDate(JobPosting jobPosting) {
        if (!jobPostingsByCloseDate.containsKey(jobPosting.getCloseDate())) {
            jobPostingsByCloseDate.put(jobPosting.getCloseDate(), new ArrayList<>());
        }
        jobPostingsByCloseDate.get(jobPosting.getCloseDate()).add(jobPosting);
    }

    public static void addJobPosting(JobPosting jobPosting) {
        jobPostings.put(jobPosting.getId(), jobPosting);
        addToJobPostingsByPosition(jobPosting);
        addToJobPostingsByCompany(jobPosting);
        addToJobPostingsByCloseDate(jobPosting);
    }

    public static void removeFromJobPostingsByPosition(JobPosting jobPosting) {
        jobPostingsByPosition.get(jobPosting.getPosition()).remove(jobPosting);
        if (jobPostingsByPosition.get(jobPosting.getPosition()).isEmpty()) {
            jobPostingsByPosition.remove(jobPosting.getPosition());
        }
    }

    public static void removeFromJobPostingsByCompany(JobPosting jobPosting) {
        jobPostingsByCompany.get(jobPosting.getCompany().getCompanyName()).remove(jobPosting);
        if (jobPostingsByCompany.get(jobPosting.getCompany().getCompanyName()).isEmpty()) {
            jobPostingsByCompany.remove(jobPosting.getCompany().getCompanyName());
        }
    }

    public JobPosting findJobPosting(String id) {
        return jobPostings.get(id);
    }

    public ArrayList<JobPosting> searchByPosition(String position) {
        return jobPostingsByPosition.get(position);
    }

    public ArrayList<JobPosting> searchByCompany(String company) {
        return jobPostingsByCompany.get(company);
    }

    public void closeJobPostings() {
        for (LocalDate date: jobPostingsByCloseDate.keySet()) {
            if (date.isBefore(LocalDate.now())) {
                ArrayList<JobPosting> jobPostings = jobPostingsByCloseDate.remove(date);
                for (JobPosting jobPosting: jobPostings) {
                    jobPosting.setCurrentState(new WaitingForNextRound(jobPosting));
                    removeFromJobPostingsByPosition(jobPosting);
                    removeFromJobPostingsByCompany(jobPosting);
                }
            }
        }
    }












//    HashMap<Company, HashMap<String, JobPosting>> jobPostings;  //(String) position -> (JobPosting) jobPosting
//
//    void addJobPostings(JobPosting jobPosting){
//        //todo:implement
//        Company company = jobPosting.getCompany();
//        //if one position in this company already exist, does it need be able to add numPosition?
//    }
//
//    ArrayList<JobPosting> searchByCompany(Company company){
//        return new ArrayList<>(jobPostings.get(company).values());
//    }
//
//    ArrayList<JobPosting> searchByPosition(String position){
//        ArrayList<JobPosting> lst = new ArrayList<>();
//        for(Company company: jobPostings.keySet()){
//            lst.add(jobPostings.get(company).get(position));
//        }
//        return lst;
//    }

}