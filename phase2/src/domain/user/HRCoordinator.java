package domain.user;

import domain.filter.Filterable;
import domain.job.JobPosting;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HRCoordinator extends User implements Filterable {

    private String companyId;
    private ArrayList<JobPosting> jobPostings;


    public HRCoordinator() {
        super();
        this.setUsername("HRCoordinator");
        this.companyId = "Google";
    }

    public HRCoordinator(HashMap<String, String> map, String companyId) {
        super(map);
        this.companyId = companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public ArrayList<JobPosting> getJobPostings() {
        return this.jobPostings;
    }

    public void addJobPosting(JobPosting jobPosting) {
        this.jobPostings.add(jobPosting);
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("companyId");
        headings.add("userName");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.getCompanyId());
        values.add(this.getUsername());
        return values.toArray(new String[0]);
    }
}
