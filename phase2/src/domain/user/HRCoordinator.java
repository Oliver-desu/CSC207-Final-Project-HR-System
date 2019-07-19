package domain.user;

import domain.job.JobPosting;

import java.util.ArrayList;
import java.util.HashMap;

public class HRCoordinator extends User {
    private String companyId;
    private ArrayList<JobPosting> jobPostings;

    public HRCoordinator() {
    }

    public String getCompanyId() {
        return null;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return null;
    }

    public void addJobPosting(JobPosting jobPosting) {
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }


}
