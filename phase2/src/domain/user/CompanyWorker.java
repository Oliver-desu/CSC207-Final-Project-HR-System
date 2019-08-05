package domain.user;

import domain.Enums.UserType;
import domain.applying.Interview;
import domain.filter.Filterable;
import domain.job.JobPosting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CompanyWorker extends User implements Filterable, Serializable {

    private String companyId;
    private ArrayList<Object> files = new ArrayList<>();


    public CompanyWorker(HashMap<String, String> values, String companyId, UserType userType) {
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
