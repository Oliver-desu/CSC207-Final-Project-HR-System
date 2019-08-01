package domain.storage;

import domain.job.JobPosting;
import domain.user.Applicant;
import domain.user.CompanyWorker;
import domain.user.NullUser;
import domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;

public class InfoCenter {

    private HashMap<User.UserType, ArrayList<User>> users = new HashMap<>();
    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, JobPosting> jobPostings = new HashMap<>();


    public InfoCenter() {
        users.put(User.UserType.APPLICANT, new ArrayList<>());
        users.put(User.UserType.HR_COORDINATOR, new ArrayList<>());
        users.put(User.UserType.HR_GENERALIST, new ArrayList<>());
        users.put(User.UserType.INTERVIEWER, new ArrayList<>());
    }

    public void register(User user, User.UserType userType) {
        this.users.get(userType).add(user);
    }

    public void registerCompany(Company company) {
        this.companies.put(company.getId(), company);
    }

    public User getUser(String userName, User.UserType userType) {
        for (User user : users.get(userType)) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return new NullUser();
    }

    public Applicant getApplicant(String username) {
        try {
            return (Applicant) getUser(username, User.UserType.APPLICANT);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public Company getCompany(String companyId) {
        return companies.get(companyId);
    }

    public CompanyWorker getCompanyWorker(String username, User.UserType userType) {
        try {
            return (CompanyWorker) getUser(username, userType);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public ArrayList<Applicant> getAllApplicants() {
        ArrayList<Applicant> applicants = new ArrayList<>();
        for (User user : users.get(User.UserType.APPLICANT)) {
            applicants.add((Applicant) user);
        }
        return applicants;
    }

    public ArrayList<CompanyWorker> getInterviewers(ArrayList<String> usernameList) {
        ArrayList<CompanyWorker> interviewers = new ArrayList<>();
        for (String username : usernameList) {
            interviewers.add(getCompanyWorker(username, User.UserType.INTERVIEWER));
        }
        return interviewers;
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
