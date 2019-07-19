package domain.user;

import domain.applying.Interview;

import java.util.ArrayList;
import java.util.HashMap;

public class Interviewer extends User {

    private String companyId;
    private ArrayList<Interview> pastInterviews;
    private ArrayList<Interview> upcomingInterviews;


    public Interviewer() {

    }

    public ArrayList<Interview> getUpcomingInterviews() {
        return null;
    }

    public ArrayList<Interview> getPastInterviews() {
        return null;
    }

    public ArrayList<Interview> getAllInterviews() {
        return null;
    }

    public void addInterview(Interview interview) {

    }

    public void updateInterviews() {

    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }
}
