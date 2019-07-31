package domain.storage;

import domain.job.JobPosting;
import domain.user.*;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoCenter {

    public enum UserType {
        APPLICANT,
        INTERVIEWER,
        HR_GENERALIST,
        HR_COORDINATOR
    }

    private HashMap<String, Applicant> applicants = new HashMap<>();
    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, Interviewer> interviewers = new HashMap<>();
    private HashMap<String, HRGeneralist> generalists = new HashMap<>();
    private HashMap<String, HRCoordinator> coordinators = new HashMap<>();
    private HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public void register(Applicant applicant) {
        applicants.put(applicant.getUsername(), applicant);
    }

    public void register(Interviewer interviewer) {
        interviewers.put(interviewer.getUsername(), interviewer);
    }

    public void register(HRCoordinator coordinator) {
        coordinators.put(coordinator.getUsername(), coordinator);
    }

    public void register(HRGeneralist generalist) {
        generalists.put(generalist.getUsername(), generalist);
        HashMap<String, String> values = new HashMap<>();
        values.put("id", generalist.getCompanyId());
        values.put("generalistId", generalist.getUsername());
        companies.put(generalist.getCompanyId(), new Company(values));
    }

    public Applicant getApplicant(String username) {
        return applicants.get(username);
    }

    public Company getCompany(String companyId) {
        return companies.get(companyId);
    }

    public Interviewer getInterviewer(String username) {
        return interviewers.get(username);
    }

    public HRGeneralist getHRGeneralist(String username) {
        return generalists.get(username);
    }

    public HRCoordinator getHRCoordinator(String username) {
        return coordinators.get(username);
    }

    public ArrayList<Applicant> getAllApplicants() {
        return new ArrayList<>(this.applicants.values());
    }

    public ArrayList<Interviewer> getInterviewers(ArrayList<String> usernameList) {
        ArrayList<Interviewer> tempInterviewers = new ArrayList<>();
        for (String username : usernameList) {
            tempInterviewers.add(getInterviewer(username));
        }
        return tempInterviewers;
    }

    public User getUser(UserType type, String username) {
        if (type.equals(UserType.APPLICANT) && applicants.containsKey(username)) {
            return getApplicant(username);
        } else if (type.equals(UserType.INTERVIEWER) && interviewers.containsKey(username)) {
            return getInterviewer(username);
        } else if (type.equals(UserType.HR_GENERALIST) && generalists.containsKey(username)) {
            return getHRGeneralist(username);
        } else if (type.equals(UserType.HR_COORDINATOR) && coordinators.containsKey(username)) {
            return getHRCoordinator(username);
        } else {
            return new NullUser();
        }
    }

    public ArrayList<JobPosting> getOpenJobPostings() {
        ArrayList<JobPosting> openJobPostings = new ArrayList<>();
        for (JobPosting jobPosting : this.jobPostings.values()) {
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
        for (String id : ids) {
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
