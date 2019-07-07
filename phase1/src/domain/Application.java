package domain;

//import login.SearchObject;

import java.util.ArrayList;

public class Application {
    //implements SearchObject

    private JobPosting jobPosting;
    private ArrayList<Interviewer> interviews;
    private Applicant applicant;
    //private String id;
    private String status; //"incomplete"(default), "submitted", rejected", "hired"
    private ArrayList<Document> documents = new ArrayList<>();



    public Application(JobPosting jobPosting, Applicant applicant) {
        this.jobPosting = jobPosting;
        this.applicant = applicant;
        //initialize other attributes
    }

    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public ArrayList<Interviewer> getInterviews(String status){
        //status: "pass", upcoming"(default), "pending", "fail"
        //e.g. iterate through interviews and return the "pass" interviews

        //delete theses after implementing
        ArrayList<Interviewer> a = new ArrayList<>();
        return a;
    }



    public Applicant getApplicant() {
        return applicant;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    //status change

    public void setToSubmitted(){}

    public void setToRejected(){}

    public void setToHired(){}
}








/*
    public Application(JobPosting jobposting, Applicant applicant, String id) {
        this.jobPosting = jobposting;
        this.applicant = applicant;
        this.id = id;
    }
    public Application(JobPosting jobposting, Applicant applicant){
        this.jobPosting = jobposting;
        this.applicant = applicant;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public Applicant getApplicant(){
        return this.applicant;
    }


    public  void setApplicant(Applicant a){this.applicant = a;}

    public  void setJobPosting(JobPosting a){this.jobPosting= a;}

    public void addDocument(Document document) {
        this.documents.add(document);
    }

    public JobPosting getJobPosting() {
        return this.jobPosting;
    }

    public ArrayList<Document> getDocument(){
        return this.documents;
    }

    public String shortForm() {
        return null;
    }

    public String detailForm() {
        return null;
    }

    // Oliver: Class Interview use below new methods. To resolve error. I added them.
    // Please contact to Kerwin.
    public String getApplicantRealName(){
        return this.applicant.getRealName();
    }

    public String getId(){
        return this.id;
    }


    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public ArrayList<String> getSearchValues() {
        return null;
    }
}

