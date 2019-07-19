package domain.applying;

import java.util.ArrayList;
import java.util.HashMap;

public class Application {

    public enum ApplicationStatus {
        DRAFT,
        PENDING,
        HIRE,
        REJECTED
    }

    private HashMap<String, Interview> interviews;
    private String applicationId;
    private String jobPostingId;
    private DocumentManager documentManager;
    private ApplicationStatus status;


    public Application() {

    }

    public HashMap<String, Interview> getInterviewMap() {
        return null;
    }

    public ArrayList<Interview> getInterviews() {
        return null;
    }

    public Interview getInterviewByRound(String round) {
        return null;
    }

    public String getApplicationId() {
        return null;
    }

    public String getJobPostingId() {
        return null;
    }

    public DocumentManager getDocumentManager() {
        return null;
    }

    public ApplicationStatus getStatus() {
        return null;
    }

    public void addInterview(String round, Interview interview) {

    }

    public void apply() {

    }

    public void hired() {

    }

    public void rejected() {

    }

    public void cancel() {

    }

}
