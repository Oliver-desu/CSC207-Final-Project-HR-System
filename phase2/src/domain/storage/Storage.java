package domain.storage;

import domain.Enums.JobPostingStatus;
import domain.Enums.UserType;
import domain.job.JobPosting;
import domain.user.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Storage implements Serializable {

    private HashMap<UserType, ArrayList<User>> users = new HashMap<>();
    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public Storage() {
        users.put(UserType.APPLICANT, new ArrayList<>());
        users.put(UserType.RECRUITER, new ArrayList<>());
        users.put(UserType.HIRING_MANAGER, new ArrayList<>());
        users.put(UserType.INTERVIEWER, new ArrayList<>());
    }

    public void register(User user, UserType userType) {
        this.users.get(userType).add(user);
    }

    void registerCompany(Company company) {
        this.companies.put(company.getId(), company);
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

    public Employee getEmployee(String username, UserType userType) {
        try {
            return (Employee) getUser(username, userType);
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

    public ArrayList<Employee> getInterviewers(ArrayList<String> usernameList) {
        ArrayList<Employee> interviewers = new ArrayList<>();
        for (String username : usernameList) {
            interviewers.add(getEmployee(username, UserType.INTERVIEWER));
        }
        return interviewers;
    }

    public ArrayList<JobPosting> getOpenJobPostings() {
        ArrayList<JobPosting> openJobPostings = new ArrayList<>();
        for (JobPosting jobPosting : this.jobPostings.values()) {
            if (jobPosting.getStatus().equals(JobPostingStatus.OPEN)) {
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
