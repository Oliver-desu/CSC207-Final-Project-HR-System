package domain.user;

import domain.applying.Interview;
import domain.filter.Filterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Interviewer extends User implements Filterable {

    private String companyId;
    private ArrayList<Interview> pastInterviews;
    private ArrayList<Interview> upcomingInterviews;


    public Interviewer(HashMap<String, String> map, String companyId) {
        super(map);
        this.companyId = companyId;
        this.pastInterviews = new ArrayList<>();
        this.upcomingInterviews = new ArrayList<>();
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
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
        if (interview.getStatus().equals(Interview.InterviewStatus.FAIL) || interview.getStatus().equals(Interview.InterviewStatus.PASS)) {
            this.pastInterviews.add(interview);
        } else if (interview.getStatus().equals(Interview.InterviewStatus.PENDING)) {
            this.upcomingInterviews.add(interview);
        }
    }

    public void updateInterviews() {

    }

    public void update(Interview interview) {
        this.upcomingInterviews.remove(interview);
        this.pastInterviews.add(interview);
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }

    @Override
    public String[] getHeadings() {
        List<String> headings = new ArrayList<>();
        headings.add("userName");
        return headings.toArray(new String[0]);
    }

    @Override
    public String[] getSearchValues() {
        List<String> values = new ArrayList<>();
        values.add(this.getUsername());
        return values.toArray(new String[0]);
    }
}
