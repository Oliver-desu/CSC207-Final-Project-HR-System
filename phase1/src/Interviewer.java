import java.util.ArrayList;

public class Interviewer extends User {

    private static ArrayList<Interviewer> allInterviewers = new ArrayList<Interviewer>();
    private Company company;
    private ArrayList<Interview> interviews = new ArrayList<Interview>();

    public Interviewer(String username, String password, Company company) {
        super(username, password);
        this.company = company;
        allInterviewers.add(this);
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

    //    Unfinished methods:

    @Override
    public String toString() {
        return null;
    }



}
