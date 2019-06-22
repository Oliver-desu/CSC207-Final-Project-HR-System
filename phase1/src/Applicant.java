import java.time.LocalDate;
public class Application {

    private JobPosting jobposting;
    private Applicant applicant;
    private LocalDate closeDate;
    private String status;
    private Document douments;

    public Application(JobPosting jobposting, Applicant applicant) {
        this.jobposting = jobposting;
        this.applicant = applicant;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCloseDate() {

    }

    public void setDocument(Document documents) {
        this.documents = documents;
    }

    public JobPosting getJobPosting() {
        return this.jobposting;
    }

    public ArrayList<Document> getDocument(){
        return this.documents;
    }

    public String shortForm() {

    }

    public String detailForm() {

    }




}

