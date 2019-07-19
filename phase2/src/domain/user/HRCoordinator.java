package domain.user;

import domain.job.JobPosting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HRCoordinator extends User {
    private String companyId;
    private ArrayList<JobPosting> jobPostings;

    public HRCoordinator(HashMap<String, String> map, String companyId) {
        super(map);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return this.jobPostings;
    }

    public void addJobPosting(JobPosting jobPosting) {
        this.jobPostings.add(jobPosting);
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }


}
