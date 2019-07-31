package domain;

import domain.applying.Application;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.*;
import domain.user.Applicant;
import domain.user.HRCoordinator;
import domain.user.HRGeneralist;
import domain.user.Interviewer;
import main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Test {

    private int numApplicants;
    private int numInterviewers;
    private int numCompanies;
    private int numCoordinators;
    private int numJobPostings;

    private Main main;
    private UserPool userPool;
    private JobPool jobPool;
    private CompanyPool companyPool;


    public Test() {
        this.main = new Main();
        this.userPool = main.getUserPool();
        this.jobPool = main.getJobPool();
        this.companyPool = main.getCompanyPool();
    }

    public static void main(String[] args) {
        Test test = new Test();
    }

    public Main getMain() {
        return main;
    }

    public UserPool getUserPool() {
        return userPool;
    }

    public JobPool getJobPool() {
        return jobPool;
    }

    public CompanyPool getCompanyPool() {
        return companyPool;
    }

    public Applicant getRandomApplicant() {
        return userPool.getApplicant(Integer.toString(new Random().nextInt(numApplicants)));
    }

    public Company getRandomCompany() {
        return companyPool.getCompany(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public HRGeneralist getRandomGeneralist() {
        return userPool.getHRGeneralist(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public Interviewer getRandomInterviewer(Company company) {
        ArrayList<String> interviewerIds = company.getInterviewerIds();
        return userPool.getInterviewer(interviewerIds.get(new Random().nextInt(interviewerIds.size())));
    }

    public HRCoordinator getRandomCoordinator(Company company) {
        ArrayList<String> coordinatorIds = company.getHRCoordinatorIds();
        return userPool.getHRCoordinator(coordinatorIds.get(new Random().nextInt(coordinatorIds.size())));
    }

    public JobPosting getRandomJobPosting(Company company) {
        ArrayList<String> jobPostingIds = company.getJobPostingIds();
        return jobPool.getJobPosting(jobPostingIds.get(new Random().nextInt(jobPostingIds.size())));
    }

    public String getRandomDocumentName(DocumentManager documentManager) {
        Random rand = new Random();
        ArrayList<String> docNames = documentManager.getAllDocNames();
        return docNames.get(rand.nextInt(docNames.size()));
    }

    public Applicant addApplicant() {
        HashMap<String, String> values = new HashMap<>();
        values.put("Username:", Integer.toString(numApplicants));
        values.put("Password:", "[h, o, l, y, s, h, i, t]");
        values.put("dateCreated", "2019-01-01");
        Applicant applicant = new Applicant(values);
        this.addDocuments(5, applicant.getDocumentManager());
        userPool.register(applicant);
        numApplicants++;
        return applicant;
    }

    public void addApplicants(int num) {
        for (int i = 0; i < num; i++) {
            this.addApplicant();
        }
    }

    public Company addCompany() {
        HashMap<String, String> generalistValues = new HashMap<>();
        generalistValues.put("Username:", Integer.toString(numCompanies));
        generalistValues.put("Password:", "[h, o, l, y, s, h, i, t]");
        generalistValues.put("dateCreated", "2019-01-01");
        HRGeneralist generalist = new HRGeneralist(generalistValues, Integer.toString(numCompanies));
        userPool.register(generalist);

        HashMap<String, String> compValues = new HashMap<>();
        compValues.put("id", Integer.toString(numCompanies));
        compValues.put("generalistId", generalist.getUsername());
        Company company = new Company(compValues);
        companyPool.addCompany(company.getId(), company);
        numCompanies++;

        this.addInterviewersForCompany(1, company);
        this.addCoordinatorsForCompany(1, company);
//        this.addJobPostings(1, company);
        return company;
    }

    public void addCompanies(int num) {
        for (int i = 0; i < num; i++) {
            this.addCompany();
        }
    }

    public void addInterviewersForCompany(int num, Company company) {
        Interviewer interviewer;
        HashMap<String, String> values;
        int amount = numInterviewers;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("dateCreated", "2019-01-01");
            interviewer = new Interviewer(values, company.getId());
            userPool.register(interviewer);
            company.addInterviewerId(interviewer.getUsername());
            numInterviewers ++;
        }
    }

    public void addCoordinatorsForCompany(int num, Company company) {
        HRCoordinator coordinator;
        HashMap<String, String> values;
        int amount = numCoordinators;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("dateCreated", "2019-01-01");
            coordinator = new HRCoordinator(values, company.getId());
            userPool.register(coordinator);
            company.addHRCoordinatorId(coordinator.getUsername());
            numCoordinators ++;
        }
    }

    public JobPosting addJobPosting(Company company) {
        HashMap<String, String> values = new HashMap<>();
        values.put("Job id:", Integer.toString(numJobPostings));
        values.put("Company id:", company.getId());
        values.put("Position name:", "Boss");
        values.put("Num of positions:", Integer.toString(2));
        values.put("Close date:", "2019-08-01");
        values.put("Post date:", "2019-07-29");
        values.put("CV:", "Optional");
        values.put("Cover letter:", "Optional");
        values.put("Reference:", "Optional");
        values.put("Extra document:", "Optional");
        JobPosting jobPosting = new JobPosting();
        new Info(jobPosting, values);
        jobPool.addJobPosting(jobPosting.getJobId(), jobPosting);
        company.addJobPostingId(jobPosting.getJobId());
        this.getRandomCoordinator(company).addJobPosting(jobPosting);
        numJobPostings++;
        return jobPosting;
    }

    public void addJobPostings(int num, Company company) {
        for (int i = 0; i < num; i++) {
            this.addJobPosting(company);
        }

    }

    public void addDocuments(int num, DocumentManager documentManager) {
        int amount = documentManager.getNumOfDocuments();
        for (int i=amount; i<amount+num; i++) {
            documentManager.addDocument(Integer.toString(i), new Document(Integer.toString(i)));
        }
    }

    public void addApplicationForApplicant(JobPosting jobPosting, Applicant applicant) {
        // add application for applicant and submit to jobPosting
        HashMap<String, String> values = new HashMap<>();
        values.put("applicantId", applicant.getUsername());
        values.put("jobPostingId", jobPosting.getJobId());
        Application application = new Application(values);
        String docName = this.getRandomDocumentName(applicant.getDocumentManager());
        application.getDocumentManager().addDocument(docName, applicant.getDocumentManager().findDocument(docName));
        applicant.addApplication(jobPosting.getJobId(), application);
        application.apply(jobPool, companyPool);
    }

    public Application addDraftApplicationForJobPosting(Applicant applicant, JobPosting jobPosting) {
        HashMap<String, String> values = new HashMap<>();
        values.put("applicantId", applicant.getUsername());
        values.put("jobPostingId", jobPosting.getJobId());
        Application application = new Application(values);
        for (String docName : applicant.getDocumentManager().getAllDocNames()) {
            application.getDocumentManager().addDocument(docName, applicant.getDocumentManager().findDocument(docName));
        }
        applicant.addApplication(jobPosting.getJobId(), application);
        return application;
    }

    public Application addSubmittedApplicationForJobPosting(Applicant applicant, JobPosting jobPosting) {
        Application application = this.addDraftApplicationForJobPosting(applicant, jobPosting);
        application.apply(jobPool, companyPool);
        return application;
    }

    public InterviewRound addNewRound(JobPosting jobPosting, Company company) {
        jobPosting.close();
        InterviewRound interviewRound = new InterviewRound("new round");
        jobPosting.addInterviewRound(interviewRound);
        jobPosting.nextRound();
        return interviewRound;
    }

    public void addNewRoundAndFinishMatching(JobPosting jobPosting, Company company) {
        InterviewRound interviewRound = this.addNewRound(jobPosting, company);
        Interview interview;
        Interviewer interviewer;
        Info interviewInfo;
        HashMap<String, String> values;
        for (Application application: interviewRound.getUnmatchedApplications()) {
            interview = application.getInterviewByRound(interviewRound.getRoundName());
            values = new HashMap<>();
            interviewer = this.getRandomInterviewer(company);
            values.put("Time:", "2019-08-02 10:00");
            values.put("Location:", "BA1160");
            values.put("Duration(min):", "30");
            interviewInfo = new Info(interview, values);
            interview.match(interviewer, interviewInfo);
        }
        interviewRound.checkStatus();
    }


}
