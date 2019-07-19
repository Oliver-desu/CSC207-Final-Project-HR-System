package domain.user;

import domain.applying.Interview;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Interviewer extends User {

    private String companyId;
    private ArrayList<Interview> pastInterviews;
    private ArrayList<Interview> upcomingInterviews;


    public Interviewer(String username, String password, String companyId, LocalDate dateCreated) {
        super(username, password, dateCreated);
        this.companyId = companyId;
    }

    public ArrayList<Interview> getUpcomingInterviews() {
        return this.upcomingInterviews;
    }

    public ArrayList<Interview> getPastInterviews() {
        return this.pastInterviews;
    }

    public ArrayList<Interview> getAllInterviews() {
        ArrayList<Interview> allInterviews = new ArrayList<>();
        allInterviews.addAll(this.pastInterviews);
        allInterviews.addAll(this.upcomingInterviews);
        return allInterviews;
    }

    public void addInterview(Interview interview) {
//        add to upcoming interviews?

    }

    public void updateInterviews() {

    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }
}
