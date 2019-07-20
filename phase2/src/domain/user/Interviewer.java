package domain.user;

import domain.applying.Interview;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Interviewer extends User {

    private String companyId;
    private ArrayList<Interview> pastInterviews;
    private ArrayList<Interview> upcomingInterviews;


    public Interviewer(HashMap<String, String> map, String companyId) {
        super(map);
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
        if(interview.getStatus().equals(Interview.InterviewStatus.FAIL) || interview.getStatus().equals(Interview.InterviewStatus.PASS)){
            this.pastInterviews.add(interview);
        }else if(interview.getStatus().equals(Interview.InterviewStatus.PENDING)){
            this.upcomingInterviews.add(interview);
        }
    }

    public void updateInterviews() {

    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }
}
