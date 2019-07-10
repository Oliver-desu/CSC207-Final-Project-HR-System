package domain;

//import login.SearchObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Application extends Observable implements Observer {


    //implements SearchObject
    public enum ApplicationState{INCOMPLETE, WAITING_FOR_NEXT_ROUND, INTERVIEWING, PENDING, REJECTED, HIRED };
    private  String Heading;  // contains applicant name, position
    private ArrayList<String> attachedDocuments;
    private JobPosting jobPosting;
    private HashMap<String,ArrayList<Interview>> interviews;
    //Key: "upcoming", "waiting for result"
    private Applicant applicant;
    private  String currentState;
    //one of "incomplete", "interviewing", "pending", "rejected", "hired"






    public Application(JobPosting jobPosting, Applicant applicant) {
        this.jobPosting = jobPosting;
        this.applicant = applicant;
    }
    // getters
    public JobPosting getJobPosting() {
        return jobPosting;
    }

    public String getHeading() {
        return Heading;
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

    // setters

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public void setAttachedDocuments(ArrayList<String> attachedDocuments) {
        this.attachedDocuments = attachedDocuments;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public void setHeading(String heading) {
        Heading = heading;
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
        String currentState = interview.getCurrentState();
        addValueToArraylistInHashmap(interviews,currentState,interview);

     }

    public  void addValueToArraylistInHashmap(HashMap<String,ArrayList<Interview>> map,String key, Object value ){

        try {ArrayList v = map.get(key);

            v.add(value);
            map.put(key,v);}
        catch (ClassCastException e){
            System.out.println("try the right type");
        }

    }
    public  void removeValueInArraylistInHashmap(HashMap<String,ArrayList<Interview>> map,String key, Object value ){

        try {ArrayList v = map.get(key);

            v.remove(value);
            map.put(key,v);}
        catch (ClassCastException e){
            System.out.println("try the right type");
        }

    }
    public void  dropApplication(){
        this.applicant.dropApplication(this);
        this.jobPosting.receiveApplication(this);
    }

    public  void  moveInterview(Interview interview,String from, String to){
        removeValueInArraylistInHashmap(interviews,from,to);
        addValueToArraylistInHashmap(interviews,to,interview);
    }


    // TODO: 2019-07-10
    public String attachToApplication(String fileName) {
        return null;
    }


    @Override
    public void update(Observable o, Object arg) {

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
*/
