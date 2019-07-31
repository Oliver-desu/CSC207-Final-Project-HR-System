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

    private HashMap<UserType, ArrayList<User>> users = new HashMap<>();
    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public InfoCenter() {
        users.put(UserType.APPLICANT, new ArrayList<>());
        users.put(UserType.HR_COORDINATOR, new ArrayList<>());
        users.put(UserType.HR_GENERALIST, new ArrayList<>());
        users.put(UserType.INTERVIEWER, new ArrayList<>());
    }

    public void register(User user, UserType userType) {
        this.users.get(userType).add(user);
        if (userType.equals(UserType.HR_GENERALIST)) {
            HRGeneralist generalist = (HRGeneralist) user;
            HashMap<String, String> values = new HashMap<>();
            values.put("id", generalist.getCompanyId());
            values.put("generalistId", generalist.getUsername());
            companies.put(generalist.getCompanyId(), new Company(values));
        }
    }

    public User getUser(String userName, UserType userType) {
        for (User user : users.get(userType)) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return new NullUser();
    }

    public Applicant getApplicant(String username) {
        try {
            return (Applicant) getUser(username, UserType.APPLICANT);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Company getCompany(String companyId) {
        return companies.get(companyId);
    }

    public Interviewer getInterviewer(String username) {
        try {
            return (Interviewer) getUser(username, UserType.INTERVIEWER);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public HRGeneralist getHRGeneralist(String username) {
        try {
            return (HRGeneralist) getUser(username, UserType.HR_GENERALIST);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public HRCoordinator getHRCoordinator(String username) {
        try {
            return (HRCoordinator) getUser(username, UserType.HR_COORDINATOR);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public ArrayList<Applicant> getAllApplicants() {
        ArrayList<Applicant> applicants = new ArrayList<>();
        for (User user : users.get(UserType.APPLICANT)) {
            applicants.add((Applicant) user);
        }
        return applicants;
    }

    public ArrayList<Interviewer> getInterviewers(ArrayList<String> usernameList) {
        ArrayList<Interviewer> tempInterviewers = new ArrayList<>();
        for (String username : usernameList) {
            tempInterviewers.add(getInterviewer(username));
        }
        return tempInterviewers;
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
