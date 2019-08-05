package domain.storage;

import domain.Enums.JobPostingStatus;
import domain.Enums.UserType;
import domain.job.JobPosting;
import domain.user.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code Storage} stores all the {@code User}, {@code Company} and
 * {@code JobPosting} for classes who need them.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see User
 * @see Company
 * @see JobPosting
 * @since 2019-08-04
 */
public class Storage implements Serializable {

    /**
     * A hash map whose key is {@code UserType} and value is an array list
     * of the corresponding users.
     *
     * @see     UserType
     * @see     Applicant
     * @see     Employee
     * @see     #getUser(String, UserType)
     * @see     #register(User, UserType)
     */
    private HashMap<UserType, ArrayList<User>> users = new HashMap<>();

    /**
     * An array list containing all the companies registered.
     *
     * @see     Company
     * @see     #getCompany(String)
     * @see     #registerCompany(Company)
     */
    private ArrayList<Company> companies = new ArrayList<>();

    /**
     * An array list containing all the job postings created.
     *
     * @see JobPosting
     * @see #getJobPosting(String)
     * @see #getJobPostings()
     * @see #getJobPostingsByIds(ArrayList)
     * @see #getOpenJobPostings()
     * @see #addJobPosting(JobPosting)
     */
    private ArrayList<JobPosting> jobPostings = new ArrayList<>();


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
        this.companies.add(company);
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
        for (Company company : companies) {
            if (company.getId().equals(companyId)) {
                return company;
            }
        }
        return null;
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
        for (JobPosting jobPosting : this.jobPostings) {
            if (jobPosting.getStatus().equals(JobPostingStatus.OPEN)) {
                openJobPostings.add(jobPosting);
            }
        }
        return openJobPostings;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return new ArrayList<>(this.jobPostings);
    }

    public ArrayList<JobPosting> getJobPostingsByIds(ArrayList<String> ids) {
        ArrayList<JobPosting> listJobPostings = new ArrayList<>();
        for (String id : ids) {
            listJobPostings.add(getJobPosting(id));
        }
        return listJobPostings;
    }

    public JobPosting getJobPosting(String id) {
        for (JobPosting jobPosting : jobPostings) {
            if (jobPosting.getJobId().equals(id)) {
                return jobPosting;
            }
        }
        return null;
    }

    public void addJobPosting(JobPosting jobPosting) {
        this.jobPostings.add(jobPosting);
    }

    public void updateOpenJobPostings() {
        ArrayList<JobPosting> jobPostings = this.getOpenJobPostings();
        for (JobPosting jobPosting : jobPostings) {
            jobPosting.startProcessing();
        }
    }

}
