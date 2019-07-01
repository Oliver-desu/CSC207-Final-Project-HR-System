package domain;

import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Applicant extends User implements SearchObject {
    private String realName;
    private ArrayList<Document> documents =new ArrayList<>();
    private ArrayList<Application> current_Applications = new ArrayList<>();
    private ArrayList<Application> past_Applications =new ArrayList<>();
    private ArrayList<Application> all_Applications =new ArrayList<>();
    private  ArrayList<Interview> interviews =new ArrayList<>();
    private static List<Applicant>allApplicants =new ArrayList<>();




    public Applicant(String username, String password, LocalDate DateCreated) {
        super(username, password, DateCreated);
    }

    public  Applicant(HashMap<String, String> account){
        super(account);
    }

    //getter and setters begin-------------------------------------------
        //---------------------------------------------------------


    public void setRealName(String realName) {
        this.realName = realName;
    }

    public  String getRealName(){return  this.realName;}

    public ArrayList<Document> get_documents(){return  this.documents;}

    public ArrayList<Application> getCurrent_Applications(){return  this.current_Applications;}

    public ArrayList<Application> getPast_Applications(){return  this.past_Applications;}

    public  ArrayList<Application> getApplications(){return  this.all_Applications;}

    public ArrayList<Interview> getInterviews(){return  interviews;}



    //getter and setters end -------------------------------------------
    //---------------------------------------------------------


    public void addInterviews(Interview a){interviews.add(a);}



    public List<JobPosting> viewAllJobs(ArrayList<JobPosting> allJobPosting){
        return allJobPosting;}

    public Application createApplication(JobPosting jobPosting){
        Application application = new Application(jobPosting,this);
        this.all_Applications.add(application);
        return  application;
        //creat application with jobposing and return it

    }
    public  void withdrawApplication(Application application){
        for (Application application1:all_Applications
             ) {if(application1 == application){all_Applications.remove(application1);}

        }
        application.getJobPosting().withdrawApplication(application);


        // set domain.Applicant and Jobposting i order to withdraw
    }

    public  void submitDocument(Document document){
        this.documents.add(document);
    }

    public  boolean applicant_already_created(String username){
        for (Applicant applicant:allApplicants
        ) {if(applicant.getUsername().equals(username)){return true;}

        }
        return false;
    }//to determine whether there is a user already

    public ArrayList<Application> get_applications(String username){
        for (Applicant applicant:allApplicants
             ) {if(applicant.getUsername().equals(username)){return applicant.getApplications();}

        }

        return  new ArrayList<>();
        //return a List with all applications in it   , and if username dose not exits return a empty list
    }

    public ArrayList<Document> getDocuments(){return  this.documents;}
    public Document getCV(){
        for (Document doc:documents
        ) {if(doc.get_CV_or_not()){return doc;}

        }
        return new Document("");
        //return CV   , and if CV dose not exits return a empty domain.Document.
    }

    public Document getCoverLetter(){
        for (Document cv:documents
        ) {if(!cv.get_CV_or_not()){return cv;}
        }
        return new Document("");
        //return CV   , and if Coverletter dose not exits return a empty domain.Document.
    }

    public Document getDocument(String name){
        for (Document doc:documents
             ) {if(doc.getNameOfDocument().equals(name)){return doc;}

        }
        return  null;
    }

    public HashMap<String, String> getAccount() {

        return  super.getAccount();
    }

    // Below methods are collabarate with GUI. Please implement them as soon as possible.
    // Do not change heading.





    public Application getApplication(String id){
        for (Application application: all_Applications
             ) {if(application.getId().equals(id)){return  application;}

        }
        return   null;
    }


    public void addApplication(Application application) {
        all_Applications.add(application);
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




