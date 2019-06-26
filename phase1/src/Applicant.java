import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Applicant extends  User implements SearchObject {
    private String realName;
    private List<Document> documents =new ArrayList<>();
    private List<Application> current_Applications = new ArrayList<>();
    private List<Application> past_Applications =new ArrayList<>();
    private List<Application> all_Applications =new ArrayList<>();
    private  List<Interview> interviews =new ArrayList<>();
    private static List<Applicant>allApplicants =new ArrayList<>();
    private LocalDate dateCreated;
    private LocalDate lastAppliedDate;


    public Applicant(String username, String password, LocalDate DateCreated) {
        super(username, password, DateCreated);

    //getter and setters begin-------------------------------------------
        //---------------------------------------------------------

    }
    public String getUsername() { return super.getUsername();
    }
    public String getPassword() { return super.getPassword();
    }
    public void setPassword(String password) {  super.setPassword(password);
    }
    public void setUsername(String username) { super.setUsername(username);
    }
    public boolean matchPassword(String password) {return super.getPassword().equals(password);
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getRealName(){
        return this.realName;
    }
    public List<Document> get_documents(){return  this.documents;}

    public List<Application> getCurrent_Applications(){return  this.current_Applications;}

    public List<Application> getPast_Applications(){return  this.past_Applications;}

    public  List<Application> getAll_Applications(){return  this.all_Applications;}

    //getter and setters end -------------------------------------------
    //---------------------------------------------------------


    public void addInterviews(Interview a){interviews.add(a);}

    public List<Interview> getInterviews(){return  interviews;}

    public List<JobPosting> viewJobs(ArrayList<JobPosting> a){return a;}

    public  Application createApplication(JobPosting a){
        Application b = new Application(a,this);
        this.all_Applications.add(b);
        return  b;
        //creat application with jobposing and return it

    }
    public  void withdrawApplication(Application a){
        a.setApplicant(null);
        a.setJobposting(null);
        // set Applicant and Jobposting i order to withdraw
    }

    public  void submitDocument(Document d){
        this.documents.add(d);
    }

    public  boolean applicant_already_created(String username){
        for (Applicant a:allApplicants
        ) {if(a.getUsername().equals(username)){return true;}

        }
        return false;
    }//to determine whether there is a user already

    public Object get_application(String username){
        for (Applicant a:allApplicants
             ) {if(a.getUsername().equals(username)){return a.getAll_Applications();}

        }
        return "No such User";

    }

    public List<Document> getDocument(){return  this.documents;}
    public  Object getCV(){
        for (Document a:documents
        ) {if(a.get_CV_or_not()){return a;}

        }
        Document d = new Document();
        return d;
    }

    public Document  getCoverLetter(){
        for (Document a:documents
        ) {if(!a.get_CV_or_not()){return a;}

        }
        Document d = new Document();
        return d;
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




