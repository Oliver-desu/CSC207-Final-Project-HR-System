package domain.user;

import domain.Enums.UserType;
import domain.applying.Interview;
import domain.filter.Filter;
import domain.filter.Filterable;
import domain.job.JobPosting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class {@code Employee} is a type of {@code User} that works for a company.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserType
 * @see User
 * @see Company
 * @since 2019-08-04
 */
public class Employee extends User implements Filterable, Serializable {

    /**
     * The name of {@code Company} this {@code Employee} works for.
     *
     * @see Company
     * @see #getCompanyId()
     */
    private String companyId;

    /**
     * All the files this employee are responsible for. Different type
     * of employee may have different type of files to handle.
     * For {@code INTERVIEWER}, the files are interviews.
     * For {@code RECRUITER} and {@code HIRING_MANAGER}, the files are
     * job postings.
     *
     * @see UserType
     * @see User
     * @see Interview
     * @see JobPosting
     * @see #addFile(Object)
     * @see #removeFile(Object)
     * @see #getInterviews()
     * @see #getJobPostings()
     */
    private ArrayList<Object> files = new ArrayList<>();

    /**
     * creat a new employee
     *
     * @param values    the username and password
     * @param companyId the string represent the company of this employee
     * @param userType  represent the type of user{@code UserType}
     */
    public Employee(HashMap<String, String> values, String companyId, UserType userType) {
        super(values, userType);
        this.companyId = companyId;
    }

    /**
     * return the company ID of this employee
     *
     * @return a string represent the ID of this company
     * @see null
     */
    @Override
    public String getCompanyId() {
        return companyId;
    }

    /**
     * return a ArrayList of JobPostings posted by employee's company
     * if this User's UserType is HiringManager{@code UserTpe.HIRING_MANAGER} and Recruiter{@code UserType.RECRUITER}
     * @return a list represent the ID of th
     * @see  null
     */
    public ArrayList<JobPosting> getJobPostings() {
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        if (this.getUserType().equals(UserType.HIRING_MANAGER) || this.getUserType().equals(UserType.RECRUITER)) {
            for (Object file : files) {
                jobPostings.add((JobPosting) file);
            }
        }
        return jobPostings;
    }

    /**
     * return all interviews of this company
     * @return a ArrayList of company of this company
     * @see null
     */
    public ArrayList<Interview> getInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        if (this.getUserType().equals(UserType.INTERVIEWER)) {
            for (Object file : files) {
                interviews.add((Interview) file);
            }
        }
        return interviews;
    }

    /**
     * add the given file to this company
     * @see domain.applying.Interview#match(Employee)
     */
    public void addFile(Object file) {
        this.files.add(file);
    }

    /**
     * remove given file from this Employee
     * @see   null
     */
    public void removeFile(Object file) {
        this.files.remove(file);
    }

    /**
     * return  a hashmap contain username , realname , and companyID
     * @return a hashmap contain username , realname , and companyID
     * @see  Filter#getHeadings()
     * @see Filter#getSearchValues(Filterable, String[])
     */
    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", getUsername());
        map.put("real name", getRealName());
        map.put("company", getCompanyId());
        return map;
    }
}
