package domain.job;

import domain.applying.Application;
import domain.storage.Company;
import domain.storage.CompanyPool;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class JobInfo {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String id;
    private String companyId;
    private String positionName;
    private int numPositions;
    private LocalDate postDate;
    private LocalDate closeDate;
    private String requirement;
    private ArrayList<Application> finalApplications;


    public JobInfo() {}

    public JobInfo(HashMap<String, String> map) {
        this.id = map.get("id");
        this.companyId = map.get("companyId");
        this.positionName = map.get("positionName");
        this.numPositions = Integer.parseInt(map.get("numPositions"));
        this.postDate = LocalDate.parse(map.get("postDate"), formatter);
        this.closeDate = LocalDate.parse(map.get("closeDate"), formatter);
        this.requirement = map.get("requirement");
        this.finalApplications = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public Company getCompany(CompanyPool companyPool) {
        return companyPool.getCompany(this.companyId);
    }

    public String getPositionName() {
        return this.positionName;
    }

    public int getNumPositions() {
        return this.numPositions;
    }

    public LocalDate getPostDate() {
        return this.postDate;
    }

    public LocalDate getCloseDate() {
        return this.closeDate;
    }

    public String getRequirement() {
        return this.requirement;
    }

    public ArrayList<Application> getFinalApplications() {
        return this.finalApplications;
    }

    public void addToFinalApplications(Application application) {
        this.finalApplications.add(application);
    }

    // TODO: 2019-07-27
//    public boolean isValidInfo() {
//        return false;
//    }
}

