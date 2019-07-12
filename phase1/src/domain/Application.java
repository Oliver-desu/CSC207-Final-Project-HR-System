package domain;

//import login.SearchObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Application extends Observable implements Observer, Serializable {


    private  String heading;  // contains applicant name, position
    private ArrayList<String> attachedDocuments;
    private JobPosting jobPosting;
    private HashMap<String,ArrayList<Interview>> interviews;
    //Key: "upcoming", "waiting for result"
    private Applicant applicant;
    private  String currentState;
    //one of "incomplete", "forward", "pending", "rejected", "hired"



    public Application(JobPosting jobPosting, Applicant applicant) {
        this.jobPosting = jobPosting;
        this.applicant = applicant;
        this.heading = applicant.getUsername() + " -> " + jobPosting.getPosition();
        this.attachedDocuments = new ArrayList<>();
        this.interviews = new HashMap<>();
        this.currentState = "incomplete";
        this.heading = applicant.getUsername() + jobPosting.getPosition();

        this.addObserver(jobPosting);
        this.addObserver(applicant);
        applicant.addApplication(this);
    }

    // getters
    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public String getHeading() {
        return heading;
    }

    public  ArrayList getAttachedDocuments(){
        return attachedDocuments;
    }

    public HashMap<String, ArrayList<Interview>> getInterviews() {
        return interviews;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public String getApplicantName() {
        return this.applicant.getUsername();
    }

    public String getCurrentState() {
        return this.currentState;
    }

    // setters

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setAttachedDocuments(ArrayList<String> attachedDocuments) {
        this.attachedDocuments = attachedDocuments;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
        setChanged();
        notifyObservers(currentState);
    }


    public void setInterviews(HashMap<String, ArrayList<Interview>> interviews) {
        this.interviews = interviews;
    }

    public void setJobPosting(JobPosting jobPosting) {
        this.jobPosting = jobPosting;
    }

    //------end

    public ArrayList<Interview> findInterviews(String key){
        return interviews.get(key);
    }

    public void addInterview(Interview interview){
        this.interviews.get(interview.getCurrentState()).add(interview);
    }

    public void dropApplication(){
        this.applicant.removeApplication(this);
        this.jobPosting.removeApplication(this);
    }

    public  void  moveInterview(Interview interview, String from, String to){
        this.interviews.get(from).remove(interview);
        this.interviews.get(to).remove(interview);
    }

    public String attachToApplication(String fileName) {

        TheSystem.documentManager.addToAttachedDocuments(fileName, this.heading);
        return "Document attached.";
    }


    public void receiveInterviewResult(String result) {
        this.currentState = result;
        this.applicant.moveApplication(this, this.currentState);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.moveInterview((Interview) o, "pending", (String) arg);
        this.receiveInterviewResult((String) arg);
    }

    //Todo:
    public void submitApplication(){}



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
*/
