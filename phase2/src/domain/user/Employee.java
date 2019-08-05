package domain.user;

import domain.Enums.UserType;
import domain.applying.Interview;
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


    public Employee(HashMap<String, String> values, String companyId, UserType userType) {
        super(values, userType);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public ArrayList<JobPosting> getJobPostings() {
        ArrayList<JobPosting> jobPostings = new ArrayList<>();
        if (this.getUserType().equals(UserType.HIRING_MANAGER) || this.getUserType().equals(UserType.RECRUITER)) {
            for (Object file : files) {
                jobPostings.add((JobPosting) file);
            }
        }
        return jobPostings;
    }

    public ArrayList<Interview> getInterviews() {
        ArrayList<Interview> interviews = new ArrayList<>();
        if (this.getUserType().equals(UserType.INTERVIEWER)) {
            for (Object file : files) {
                interviews.add((Interview) file);
            }
        }
        return interviews;
    }

    public void addFile(Object file) {
        this.files.add(file);
    }

    public void removeFile(Object file) {
        this.files.remove(file);
    }

    @Override
    public HashMap<String, String> getFilterMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", getUsername());
        map.put("real name", getRealName());
        map.put("company", getCompanyId());
        return map;
    }
}
