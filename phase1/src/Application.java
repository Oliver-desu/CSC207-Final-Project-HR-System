import login.SearchObject;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class Application implements SearchObject {

    private JobPosting jobposting;
    private Applicant applicant;
    private LocalDate closeDate;
    private String status;
    private ArrayList<Document> documents;

    public Application(JobPosting jobposting, Applicant applicant) {
        this.jobposting = jobposting;
        this.applicant = applicant;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCloseDate() {

    }

    public  void setApplicant(Applicant a){this.applicant = a;}

    public  void setJobposting(JobPosting a){this.jobposting= a;}


    public void addDocument(Document document) {
        this.documents.add(document);
    }

    public JobPosting getJobPosting() {
        return this.jobposting;
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
        return null;
    }

    public String getId(){
        return null;
    }


    @Override
    public String getSearchValue1() {
        return null;
    }

    @Override
    public String getSearchValue2() {
        return null;
    }

    @Override
    public String getSearchValue3() {
        return null;
    }

    @Override
    public ActionListener getSelectAction() {
        return null;
    }
}
