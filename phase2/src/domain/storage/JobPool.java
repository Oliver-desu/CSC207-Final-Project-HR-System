package domain.storage;

import domain.job.JobPosting;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPool {

    private static HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public static ArrayList<JobPosting> getOpenJobPostings() {
        ArrayList<JobPosting> openJobPostings = new ArrayList<>();
        for (JobPosting jobPosting: jobPostings.values()) {
            if (jobPosting.isOpen()) {
                openJobPostings.add(jobPosting);
            }
        }
        return openJobPostings;
    }

    public static ArrayList<JobPosting> getJobPostings() {
        return new ArrayList<>(jobPostings.values());
    }

    public static ArrayList<JobPosting> getJobPostingsByIds(ArrayList<String> ids) {
        ArrayList<JobPosting> listJobPostings = new ArrayList<>();
        for (String id: ids) {
            listJobPostings.add(jobPostings.get(id));
        }
        return listJobPostings;
    }

    public static JobPosting getJobPosting(String id) {
        return jobPostings.get(id);
    }

    public static void addJobPosting(String id, JobPosting jobPosting) {
        jobPostings.put(id, jobPosting);
    }

    public static void main(String[] args) {
        ArrayList<JobPosting> jobs = JobPool.getJobPostings();
        System.out.println(jobs);
    }
}
