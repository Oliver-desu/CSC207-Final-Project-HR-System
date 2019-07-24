import domain.job.JobPosting;
import domain.job.JobInfo;
import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;
import domain.user.*;

import java.util.HashMap;

public class Test {

    private static int numApplicants;
    private static int numInterviewers;
    private static int numCompanies;
    private static int numCoordinators;
    private static int numJobPostings;


    public static void main(String[] args) {
        UserPool userPool = new UserPool();
        addApplicants(userPool, 10);
        addCompanies(5);
        for (Company company: CompanyPool.getCompanies()) {
            addInterviewersForCompany(userPool, 10, company);
            addCoordinatorsForCompany(userPool, 10, company);
            addJobPostingsForCompany(10, company);
        }

        System.out.println("Num applications: " + numApplicants);
        System.out.println("Num interviewers: " + numInterviewers);
        System.out.println("Num companies: " + numCompanies);
        System.out.println("Num coordinators: " + numCoordinators);
        System.out.println("Num jobPostings: " + numJobPostings);
    }

    static void addApplicants(UserPool userPool, int num) {
        Applicant applicant;
        HashMap<String, String> values;
        int amount = numApplicants;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("username", Integer.toString(i));
            values.put("dateCreated", "2019-01-01");
            applicant = new Applicant(values);
            userPool.register(applicant);
            numApplicants ++;
        }
    }

    static void addInterviewersForCompany(UserPool userPool, int num, Company company) {
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

    static void addCompanies(int num) {
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

            CompanyPool.addCompany(company.getId(), company);
            numCompanies ++;
        }
    }

    static void addCoordinatorsForCompany(UserPool userPool, int num, Company company) {
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

    static void addJobPostingsForCompany(int num, Company company) {
        JobPosting jobPosting;
        JobInfo jobInfo;
        HashMap<String, String> values;
        int amount = numJobPostings;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("id", Integer.toString(i));
            values.put("positionName", "Boss");
            values.put("numPositions", "1");
            values.put("postDate", "2019-01-01");
            values.put("closeDate", "2019-02-01");
            values.put("requirement", "Bossy");
            jobInfo = new JobInfo(values);
            jobPosting = new JobPosting(jobInfo);
            JobPool.addJobPosting(jobPosting.getJobId(), jobPosting);
            numJobPostings ++;
        }
    }

}
