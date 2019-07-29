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


    public JobInfo() {
        this.id = "dev1234";
        this.companyId = "Google";
        this.positionName = "Developer";
        this.numPositions = 4;
        this.postDate = LocalDate.now().minusDays(3);
        this.closeDate = this.postDate.plusDays(15);
        this.requirement = "CV";
        this.finalApplications = new ArrayList<>();
    }

    public JobInfo(HashMap<String, String> map) {
        this.id = map.get("id:");
        this.companyId = map.get("Company id:");
        this.positionName = map.get("Position name:");
        this.numPositions = Integer.parseInt(map.get("Num of positions:"));
        this.postDate = LocalDate.now();
        this.closeDate = LocalDate.parse(map.get("Close date:"), formatter);
//        this.requirement = map.get("requirement");
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

    @Override
    public String toString() {
        return "Company: " + this.companyId + "\n" +
                "Position name: " + this.positionName + "\n" +
                "Number of position: " + this.numPositions + "\n" +
                "Post date: " + this.postDate + "\n" +
                "Close date: " + this.closeDate;
    }
}

