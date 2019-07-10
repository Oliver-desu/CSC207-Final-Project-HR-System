package domain;

import login.SearchObject;

import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;


public class Applicant implements Observer {
    private HashMap<String, ArrayList<Application>> applicationsByState;
    private HashMap<String, Application> applications;

    public Applicant{
        this.applicationsByState = new HashMap<String, ArrayList<Application>>();
        this.applications = new HashMap<String, Application>();
        this.applicationsByState.put("incomplete", new ArrayList<>());
        this.applicationsByState.put("waiting_for_next_round", new ArrayList<>());
        this.applicationsByState.put("interviewing", new ArrayList<>());
        this.applicationsByState.put("pending", new ArrayList<>());
        this.applicationsByState.put("rejected", new ArrayList<>());
        this.applicationsByState.put("hired", new ArrayList<>());

    }

    public ArrayList<Application> getApplications(String key) {
        for (String app: this.applicationsByState.keySet()){
            return this.applicationsByState.get(app);
        }
    }


    public Application getApplication(String key){
        for (String app: this.applications.keySet()){
            return this.applications.get(key);
        }
    } //this returns a application

    public void addApplication(Application application){
        this.applications.put(application.getHeading(), application);
        this.applicationsByState.get("incomplete").add(application);
    }

    public void moveApplication(Application app, String state){
        if (state.equals("rejected")||state.equals("hired")){
            this.checkActive();
        }
    }

    public void checkActive(){
        int r = 0;
        r += this.applicationsByState.get("interviewing").size();
        r += this.applicationsByState.get("pending").size();
        if(r>0){
            DocumentManager.removeFromDeleteAfterThirtyDays(this);
        } else if(r==0){
            
        }

    }

    //public void removeApplication(Application application){}

    public void applyJob(JobPosting jp, Applicant applicant){
        Application app = new Application(jp, applicant);
        this.applicationsByState.get("incomplete").add(app);
        this.applications.put(app.getHeading(), app);
    }
    //this create new application and add to applicant's List



    public void removeApplication(Application app){
        for (ArrayList<Application> apps: this.applicationsByState.values()){
            this.applicationsByState.remove(app);
        }
        for (Application apps: this.applications.values()){
            this.applications.remove(app);
        }

    }



    //I'll implement this later
    @Override
    public void update(Observable o, Object arg) {

    }
}



//extends User implements SearchObject

//private String realName;
//private ArrayList<Document> documents =new ArrayList<>();
//private ArrayList<Application> currentApplications = new ArrayList<>();
//private ArrayList<Application> pastApplications =new ArrayList<>();
//private ArrayList<Application> applications =new ArrayList<>();
//private  ArrayList<Interview> interviews =new ArrayList<>();
//private static List<Applicant> allApplicants =new ArrayList<>();
//private MyDocuments myDocs;
//private boolean applying;


//public Applicant(String username, String password, LocalDate DateCreated) {
//super(username, password, DateCreated);
//}

//public Applicant (String username, String password){}












/*

    public Applicant(String username, String password, LocalDate DateCreated) {
        super(username, password, DateCreated);
    }

    public  Applicant(HashMap<String, String> account){
        super(account);
    }

    //getter and setters begin-------------------------------------------
        //---------------------------------------------------------

    public  String getRealName(){
        return  this.realName;
    }

    public ArrayList<Document> getDocuments(){
        return  this.documents;
    }

    public ArrayList<Application> getCurrentApplications(){
        return  this.currentApplications;
    }

    public ArrayList<Application> getPastApplications(){
        return  this.pastApplications;
    }

    public  ArrayList<Application> getApplications(){
        return  this.applications;
    }

    public ArrayList<Interview> getInterviews(){
        return  interviews;
    }

    public ArrayList<Application> getApplications(String username){
        for (Applicant applicant:allApplicants) {
            if(applicant.getUsername().equals(username)){
                return applicant.getApplications();
            }
        }
        return  new ArrayList<>();
        //return a List with all applications in it   , and if username dose not exits return a empty list
    }

    public Application getApplication(String id){
        for (Application application: applications) {
            if(application.getId().equals(id)){
                return  application;
            }
        }
        return   null;
    }

    public Document getCoverLetter(){
        for (Document cv:documents) {
            if(!cv.get_CV_or_not()){
                return cv;
            }
        }
        return new Document("");
        //return CV   , and if Coverletter dose not exits return a empty domain.Document.
    }

    public Document getDocument(String name){
        for (Document doc:documents) {
            if(doc.getNameOfDocument().equals(name)){
                return doc;
            }
        }
        return  null;
    }

    public HashMap<String, String> getAccount() {
        return  super.getAccount();
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


    //getter and setters end -------------------------------------------
    //---------------------------------------------------------


    public void addInterviews(Interview a){
        interviews.add(a);
    }

    public List<JobPosting> viewAllJobs(ArrayList<JobPosting> allJobPosting){
        return allJobPosting;
    }

    public Application createApplication(JobPosting jobPosting){
        Application application = new Application(jobPosting,this);
        this.applications.add(application);
        return  application;
        //creat application with jobposing and return it
    }

    public  void withdrawApplication(Application application){
        for (Application application1: applications) {
            if(application1 == application){
                applications.remove(application1);
            }
        }
        application.getJobPosting().withdrawApplication(application);
        // set domain.Applicant and JobPosting in order to withdraw
    }

    public  void submitDocument(Document document){
        this.documents.add(document);
    }

    public  boolean applicantAlreadyCreated(String username){
        for (Applicant applicant:allApplicants) {
            if(applicant.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }//to determine whether there is a user already

    public Document getCV(){
        for (Document doc:documents) {
            if(doc.get_CV_or_not()){
                return doc;
            }
        }
        return new Document("");
        //return CV, and if CV dose not exits return a empty domain.Document.
    }


    // Below methods are collaborate with GUI. Please implement them as soon as possible.
    // Do not change heading.


    public void addApplication(Application application) {
        applications.add(application);
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

*/


