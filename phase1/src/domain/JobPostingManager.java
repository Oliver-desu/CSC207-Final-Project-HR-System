package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPostingManager{
    HashMap<Company, HashMap<String, JobPosting>> jobPostings;  //(String) position -> (JobPosting) jobPosting

    void addJobPostings(JobPosting jobPosting){
        //todo:implement
        Company company = jobPosting.getCompany();
        //if one position in this company already exist, does it need be able to add numPosition?
    }

    ArrayList<JobPosting> searchByCompany(Company company){
        return new ArrayList<>(jobPostings.get(company).values());
    }

    ArrayList<JobPosting> searchByPosition(String position){
        ArrayList<JobPosting> lst = new ArrayList<>();
        for(Company company: jobPostings.keySet()){
            lst.add(jobPostings.get(company).get(position));
        }
        return lst;
    }

}