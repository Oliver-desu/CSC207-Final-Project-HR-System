package domain.storage;

import domain.job.JobPosting;

import java.util.ArrayList;
import java.util.HashMap;

public class JobPool {

    private HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public ArrayList<JobPosting> getOpenJobPostings() {
        ArrayList<JobPosting> openJobPostings = new ArrayList<>();
        for (JobPosting jobPosting: this.jobPostings.values()) {
            if (jobPosting.getStatus().equals(JobPosting.JobPostingStatus.OPEN)) {
                openJobPostings.add(jobPosting);
            }
        }
        return openJobPostings;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return new ArrayList<>(this.jobPostings.values());
    }

    public ArrayList<JobPosting> getJobPostingsByIds(ArrayList<String> ids) {
        ArrayList<JobPosting> listJobPostings = new ArrayList<>();
        for (String id: ids) {
            listJobPostings.add(this.jobPostings.get(id));
        }
        return listJobPostings;
    }

    public JobPosting getJobPosting(String id) {
        return this.jobPostings.get(id);
    }

    public void addJobPosting(String id, JobPosting jobPosting) {
        this.jobPostings.put(id, jobPosting);
    }

    public void updateOpenJobPostings() {
        ArrayList<JobPosting> jobPostings = this.getOpenJobPostings();
        for (JobPosting jobPosting : jobPostings) {
            jobPosting.startProcessing();
        }
    }

}
