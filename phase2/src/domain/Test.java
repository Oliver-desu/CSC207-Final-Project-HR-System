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

    private static int numApplicants;
    private static int numInterviewers;
    private static int numCompanies;
    private static int numCoordinators;
    private static int numJobPostings;


    public static void main(String[] args) {
        Random rand = new Random();
        UserPool userPool = new UserPool();
        JobPool jobPool = new JobPool();
        CompanyPool companyPool = new CompanyPool();
        addApplicants(userPool, 10);
        addCompanies(5, userPool, companyPool);
        JobPosting jobPosting;
        int numApplicationsSubmitted = 0;
        for (Company company: companyPool.getCompanies()) {
            addInterviewersForCompany(userPool, 10, company);
            addCoordinatorsForCompany(userPool, 10, company);
            addJobPostingsForCompany(10, company, jobPool);
            jobPosting = Test.getRandomJobPosting(company, jobPool);
            Test.addApplicationsForJobPosting(3, jobPool, userPool, companyPool, jobPosting.getJobId());
            numApplicationsSubmitted += 3;
        }

        System.out.println("Num applicants: " + numApplicants);
        System.out.println("Num interviewers: " + numInterviewers);
        System.out.println("Num companies: " + numCompanies);
        System.out.println("Num coordinators: " + numCoordinators);
        System.out.println("Num jobPostings: " + numJobPostings);
        System.out.println("Num applications submitted: " + numApplicationsSubmitted);
    }

    public static Main defaultSetting() {
        // default setting is:
        // 1) add 10 applicants
        // 2) add 5 companies
        // 3) add 10 interviewers, 10 coordinators for each company
        // 4) add 1 jobPosting for each company, then add 3 applications submitted for that jobPosting(thus creating 3 new applicants each time)

        Main main = new Main();
        Random rand = new Random();
        UserPool userPool = main.getUserPool();
        JobPool jobPool = main.getJobPool();
        CompanyPool companyPool = main.getCompanyPool();
        addApplicants(userPool, 10);
        addCompanies(5, userPool, companyPool);
        JobPosting jobPosting;
        for (Company company: companyPool.getCompanies()) {
            addInterviewersForCompany(userPool, 10, company);
            addCoordinatorsForCompany(userPool, 10, company);
            addJobPostingsForCompany(10, company, jobPool);
            jobPosting = Test.getRandomJobPosting(company, jobPool);
            Test.addApplicationsForJobPosting(3, jobPool, userPool, companyPool, jobPosting.getJobId());
        }

        return main;
    }

    public static Applicant getRandomApplicant(UserPool userPool) {
        return userPool.getApplicant(Integer.toString(new Random().nextInt(numApplicants)));
    }

    public static Company getRandomCompany(CompanyPool companyPool) {
        return companyPool.getCompany(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public static HRGeneralist getRandomGeneralist(UserPool userPool) {
        return userPool.getHRGeneralist(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public static Interviewer getRandomInterviewer(Company company, UserPool userPool) {
        ArrayList<String> interviewerIds = company.getInterviewerIds();
        return userPool.getInterviewer(interviewerIds.get(new Random().nextInt(interviewerIds.size())));
    }

    public static HRCoordinator getRandomCoordinator(Company company, UserPool userPool) {
        ArrayList<String> coordinatorIds = company.getHRCoordinatorIds();
        return userPool.getHRCoordinator(coordinatorIds.get(new Random().nextInt(coordinatorIds.size())));
    }

    public static JobPosting getRandomJobPosting(Company company, JobPool jobPool) {
        ArrayList<String> jobPostingIds = company.getJobPostingIds();
        return jobPool.getJobPosting(jobPostingIds.get(new Random().nextInt(jobPostingIds.size())));
    }

    public static void addApplicants(UserPool userPool, int num) {
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

    public static void addInterviewersForCompany(UserPool userPool, int num, Company company) {
        Interviewer interviewer;
        HashMap<String, String> values;
        int amount = numInterviewers;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            interviewer = new Interviewer(values, company.getId());
            userPool.register(interviewer);
            numInterviewers ++;
        }
    }

    public static void addCompanies(int num, UserPool userPool, CompanyPool companyPool) {
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

    public static void addCoordinatorsForCompany(UserPool userPool, int num, Company company) {
        HRCoordinator coordinator;
        HashMap<String, String> values;
        int amount = numCoordinators;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            coordinator = new HRCoordinator(values, company.getId());
            userPool.register(coordinator);
            numCoordinators ++;
        }
    }

    public static void addJobPostingsForCompany(int num, Company company, JobPool jobPool) {
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

    public static void addDocumentsForApplicant(int num, domain.user.Applicant applicant) {
        Document document;
        int amount = applicant.getDocumentManager().getNumOfDocuments();
        for (int i=amount; i<amount+num; i++) {
            applicant.getDocumentManager().addDocument(Integer.toString(i), new Document("some content"));
        }
    }

    public static String getRandomDocumentName(DocumentManager documentManager) {
        Random rand = new Random();
        ArrayList<String> docNames = documentManager.getAllDocNames();
        return docNames.get(rand.nextInt(docNames.size()));
    }

    public static void addApplicationForApplicant(JobPosting jobPosting, Applicant applicant, CompanyPool companyPool) {
        HashMap<String, String> values = new HashMap<>();
        values.put("applicantId", applicant.getUsername());
        values.put("jobPostingId", jobPosting.getJobId());
        Application application = new Application(values);
        String docName = Test.getRandomDocumentName(applicant.getDocumentManager());
        application.getDocumentManager().addDocument(docName, applicant.getDocumentManager().findDocument(docName));
        applicant.addApplication(jobPosting.getJobId(), application);
        jobPosting.applicationSubmit(application, companyPool);
    }

    public static void addApplicationsForJobPosting(int num, JobPool jobPool, UserPool userPool,
                                                    CompanyPool companyPool, String jobPostingId) {
        // Create num applicants, then create 1 application for each of them
        int beforeNewApplicants = numApplicants;
        Test.addApplicants(userPool, num);
        HashMap<String, String> values;
        Application application;
        for (int applicantId=beforeNewApplicants; applicantId<numApplicants; applicantId++) {
            values = new HashMap<>();
            values.put("applicantId", Integer.toString(applicantId));
            values.put("jobPostingId", jobPostingId);
            application = new Application(values);
            application.apply(jobPool, companyPool);
        }
    }


}
