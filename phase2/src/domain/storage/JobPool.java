package domain.storage;

import domain.job.JobPosting;

import java.util.ArrayList;
import java.util.Collection;
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
        ArrayList<JobPosting> listJobPostings = new ArrayList<JobPosting>();
        Collection<String> keys = jobPostings.keySet();
        for (String key:keys
             ) {listJobPostings.add(jobPostings.get(key));

        }
        return  listJobPostings;
    }

    public static JobPosting getJobPosting(String id) {
        return jobPostings.get(id);
    }

    public static void addJobPosting(String id, JobPosting jobPosting) {
        jobPostings.put(id, jobPosting);
    }
}
