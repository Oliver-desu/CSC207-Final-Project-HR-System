package domain.storage;

import domain.job.JobPosting;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPool {

    private static HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public static ArrayList<JobPosting> getOpenJobPostings() {
        return null;
    }

    public static ArrayList<JobPosting> getJobPostings() {
        return null;
    }

    public static JobPosting getJobPosting(String id) {
        return null;
    }

    public static void addJobPosting(String id, JobPosting jobPosting) {

    }
}
