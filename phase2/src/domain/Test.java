package domain;

import domain.applying.Application;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.job.JobPosting;
import domain.job.JobInfo;
import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;
import domain.user.*;
import domain.user.Applicant;
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

    private UserPool userPool = new UserPool();
    private JobPool jobPool = new JobPool();
    private CompanyPool companyPool = new CompanyPool();


    public static void main(String[] args) {
        Test test = new Test();

        Random rand = new Random();
        test.addApplicants(10);
        test.addCompanies(5);
        JobPosting jobPosting;
        for (Company company: test.getCompanyPool().getCompanies()) {
            test.addInterviewersForCompany(10, company);
            test.addCoordinatorsForCompany(10, company);
            test.addJobPostingsForCompany(10, company);
            jobPosting = test.getRandomJobPosting(company);
            test.addApplicationsForJobPosting(3, jobPosting);
        }

        System.out.println("Num applicants: " + test.getNumApplicants());
        System.out.println("Num interviewers: " + test.getNumInterviewers());
        System.out.println("Num companies: " + test.getNumCompanies());
        System.out.println("Num coordinators: " + test.getNumCoordinators());
        System.out.println("Num jobPostings: " + test.getNumJobPostings());
    }

    public void setDefault() {
        // default setting is:
        // 1) add 10 applicants
        // 2) add 5 companies
        // 3) add 10 interviewers, 10 coordinators for each company
        // 4) add 1 jobPosting for each company, then add 3 applications submitted for that jobPosting(thus creating 3 new applicants each time)

        Random rand = new Random();
        this.addApplicants(10);
        this.addCompanies(5);
        JobPosting jobPosting;
        for (Company company: companyPool.getCompanies()) {
            this.addInterviewersForCompany(10, company);
            this.addCoordinatorsForCompany(10, company);
            this.addJobPostingsForCompany(10, company);
            jobPosting = this.getRandomJobPosting(company);
            this.addApplicationsForJobPosting(3, jobPosting);
        }
    }

    public int getNumApplicants() {
        return numApplicants;
    }

    public int getNumCompanies() {
        return numCompanies;
    }

    public int getNumCoordinators() {
        return numCoordinators;
    }

    public int getNumInterviewers() {
        return numInterviewers;
    }

    public int getNumJobPostings() {
        return numJobPostings;
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

    public void addApplicants(int num) {
        domain.user.Applicant applicant;
        HashMap<String, String> values;
        int amount = numApplicants;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            applicant = new domain.user.Applicant(values);
            userPool.register(applicant);
            numApplicants ++;
        }
    }

    public void addInterviewersForCompany(int num, Company company) {
        Interviewer interviewer;
        HashMap<String, String> values;
        int amount = numInterviewers;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            interviewer = new Interviewer(values, company.getId());
            userPool.register(interviewer);
            company.addInterviewerId(interviewer.getUsername());
            numInterviewers ++;
        }
    }

    public void addCompanies(int num) {
        Company company;
        HRGeneralist generalist;
        HashMap<String, String> values;
        int amount = numCompanies;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            generalist = new HRGeneralist(values, Integer.toString(i));

            values = new HashMap<>();
            values.put("id", Integer.toString(i));
            values.put("generalistId", generalist.getUsername());
            company = new Company(values);

            companyPool.addCompany(company.getId(), company);
            userPool.register(generalist);
            numCompanies ++;
        }
    }

    public void addCoordinatorsForCompany(int num, Company company) {
        HRCoordinator coordinator;
        HashMap<String, String> values;
        int amount = numCoordinators;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            coordinator = new HRCoordinator(values, company.getId());
            userPool.register(coordinator);
            company.addHRCoordinatorId(coordinator.getUsername());
            numCoordinators ++;
        }
    }

    public void addJobPostingsForCompany(int num, Company company) {
        Random rand = new Random();
        JobPosting jobPosting;
        JobInfo jobInfo;
        HashMap<String, String> values;
        int amount = numJobPostings;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("id", Integer.toString(i));
            values.put("companyId", company.getId());
            values.put("positionName", "Boss");
            values.put("numPositions", Integer.toString(1+rand.nextInt(3)));
            values.put("postDate", "2019-01-01");
            values.put("closeDate", "2019-02-01");
            values.put("requirement", "Bossy");
            jobInfo = new JobInfo(values);
            jobPosting = new JobPosting(jobInfo);
            jobPool.addJobPosting(jobPosting.getJobId(), jobPosting);
            company.addJobPostingId(jobPosting.getJobId());
            numJobPostings ++;
        }
    }

    public void addDocumentsForApplicant(int num, Applicant applicant) {
        Document document;
        int amount = applicant.getDocumentManager().getNumOfDocuments();
        for (int i=amount; i<amount+num; i++) {
            applicant.getDocumentManager().addDocument(Integer.toString(i), new Document("some content"));
        }
    }

    public String getRandomDocumentName(DocumentManager documentManager) {
        Random rand = new Random();
        ArrayList<String> docNames = documentManager.getAllDocNames();
        return docNames.get(rand.nextInt(docNames.size()));
    }

    public void addApplicationForApplicant(JobPosting jobPosting, Applicant applicant) {
        HashMap<String, String> values = new HashMap<>();
        values.put("applicantId", applicant.getUsername());
        values.put("jobPostingId", jobPosting.getJobId());
        Application application = new Application(values);
        String docName = this.getRandomDocumentName(applicant.getDocumentManager());
        application.getDocumentManager().addDocument(docName, applicant.getDocumentManager().findDocument(docName));
        applicant.addApplication(jobPosting.getJobId(), application);
        jobPosting.applicationSubmit(application, companyPool);
    }

    public void addApplicationsForJobPosting(int num, JobPosting jobPosting) {
        // Create num applicants, then create 1 application for each of them
        int beforeNewApplicants = numApplicants;
        this.addApplicants(num);
        HashMap<String, String> values;
        Application application;
        for (int applicantId=beforeNewApplicants; applicantId<numApplicants; applicantId++) {
            values = new HashMap<>();
            values.put("applicantId", Integer.toString(applicantId));
            values.put("jobPostingId", jobPosting.getJobId());
            application = new Application(values);
            application.apply(jobPool, companyPool);
        }
    }

//    public static void addNewRoundForJobPosting(JobPool jobPool, String jobPostingId)


}
