package domain;

import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Interviewer extends User implements SearchObject {

    private String realName;
    private Company company;
    private ArrayList<Interview> interviews = new ArrayList<Interview>();

    public Interviewer(String username, String password, String realName, Company company) {
        super(username, password);
        this.realName = realName;
        company.addInterviewers(this);
        this.company = company;

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


    public void addInterview(Interview interview) {
        this.interviews.add(interview);
    }

    public boolean removeInterview(Interview interview) {
        return this.interviews.remove(interview);
    }

    //   TODO: Unfinished methods:

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = ((User) this).getAccount();
        return result;
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue1() {
        return null;
    }
}
