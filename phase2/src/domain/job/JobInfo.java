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
        return null;
    }

    public String getPositionName() {
        return null;
    }

    public int getNumPositions() {
        return 0;
    }

    public LocalDate getPostDate() {
        return null;
    }

    public LocalDate getCloseDate() {
        return null;
    }

    public String getRequirement() {
        return null;
    }

    public ArrayList<Application> getFinalApplications() {
        return null;
    }

    public void addToFinalApplications(Application application) {
    }

    public boolean isValidInfo() {
        return false;
    }
}

