package domain;

import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Interviewer extends User implements SearchObject {

    private static ArrayList<Interviewer> allInterviewers = new ArrayList<Interviewer>();
    private String realName;
    private Company company;
    private ArrayList<Interview> interviews = new ArrayList<Interview>();

    public Interviewer(String username, String password, LocalDate dateCreated, String realName, Company company) {
        super(username, password, dateCreated);
        this.realName = realName;
        this.company = company;
        allInterviewers.add(this);
    }

    public String getRealName() {
        return this.realName;
    }

    public Company getCompany() {
        return this.company;
    }

    public ArrayList<Interview> getInterviews() {
        return this.interviews;
    }

    public static ArrayList<Interviewer> getAllInterviewers() {
        return allInterviewers;
    }

    public void addInterview(Interview interview) {
        this.interviews.add(interview);
    }

    public boolean removeInterview(Interview interview) {
        return this.interviews.remove(interview);
    }

    //   TODO: Unfinished methods:

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = ((User) this).getAccount();
        result.put("Real name", this.realName);
        result.put("Company name", this.company.getCompanyName());
        return result;
    }

    @Override
    public ArrayList<String> getSearchValues() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }
}
