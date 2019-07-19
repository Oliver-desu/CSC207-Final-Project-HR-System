package domain.job;

import domain.applying.Application;

import java.time.LocalDate;
import java.util.ArrayList;

public class JobInfo {
    private String id;
    private String positionName;
    private int numPositions;
    private LocalDate postDate;
    private LocalDate closeDate;
    private String requirement;
    private ArrayList<Application> finalApplications;

    public JobInfo() {
    }

    public String getId() {
        return this.id;
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

    public boolean isValidInfo() {
//        todo
        return false;
    }
}

