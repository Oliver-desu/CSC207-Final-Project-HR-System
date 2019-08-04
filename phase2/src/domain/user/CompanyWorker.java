package domain.user;

import domain.Enums.UserType;
import domain.applying.Interview;
import domain.filter.Filterable;
import domain.job.JobPosting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        if (this.getUserType().equals(UserType.HR_GENERALIST) || this.getUserType().equals(UserType.HR_COORDINATOR)) {
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
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("Username");
        headings.add("Real name");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.getUsername());
        values.add(this.getRealName());
        return values.toArray(new String[0]);
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
