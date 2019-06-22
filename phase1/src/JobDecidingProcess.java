package src;
import java.util.*;

public class JobDecidingProcess {
    private int round;
    private List<Application> allApplications;
    private List<Application> remainingApplications;
    private List<String> interviewRounds;
    private HashMap<String, List<Interview>> interviews;
    private List<String> requirement;

    public JobDecidingProcess(){
        this.allApplications = new ArrayList<Application>();
        this.remainingApplications = new ArrayList<Application>();
        this.interviewRounds = new ArrayList<String>();
        this.interviews = new HashMap<String, List<Interview>>();
        this.requirement = new ArrayList<String>();
    }

    List<Application> getAllApplications() {
        return allApplications;
    }

    List<Application> getRemainingApplications() {
        return remainingApplications;
    }


}


