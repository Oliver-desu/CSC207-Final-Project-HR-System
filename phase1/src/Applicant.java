import java.time.LocalDate;

import java.util.Date;
import java.util.List;

public class Applicant extends  User{
    private String username;
    private String password;
    private List<Document> Documents;
    private List<Application> current_Applications;
    private List<Application> past_Applications;
    private List<Application> all_Applications;
    private static List<Interview> interviews;
    private LocalDate dateCreated;
    private LocalDate lastAppliedDate;


    public Applicant(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public List<Document> get_documents(){return  this.Documents}

    public List<Application> getCurrent_Applications(){return  this.current_Applications;}

    public List<Application> getPast_Applications(){return  this.past_Applications;}

    public  List<Application> getAll_Applications(){return  this.all_Applications;}

    public void addInterviews(Interview a){interviews.add(a);}


}




