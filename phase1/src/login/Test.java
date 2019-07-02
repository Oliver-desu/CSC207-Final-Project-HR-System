package login;
import domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Test {

    private static LocalDate currentDate = LocalDate.now();
    private static ArrayList<Company> companies = new ArrayList<>();
    private static ArrayList<Applicant> applicants = new ArrayList<>();
    private static ArrayList<Application> applications = new ArrayList<>();
    private static ArrayList<JobPosting> jobPostings = new ArrayList<>();
    private static ArrayList<HumanResource> humanResources = new ArrayList<>();
    private static ArrayList<Interviewer> interviewers = new ArrayList<>();
    private static ArrayList<Interview> interviews = new ArrayList<>();

    public static HumanResource getRandomHumanResource() {
        Random rand = new Random();
        return humanResources.get(rand.nextInt(humanResources.size()));
    }

    public static Applicant getRandomApplicant() {
        Random rand = new Random();
        return applicants.get(rand.nextInt(applicants.size()));
    }

    public static Interviewer getRandomInterviewer() {
        Random rand = new Random();
        return interviewers.get(rand.nextInt(interviewers.size()));
    }

    private static void addApplicants(int num) {
        int size = applicants.size();
        for (int i=size; i<size+num; i++) {
            applicants.add(new Applicant(Integer.toString(i), Integer.toString(i), currentDate));
        }
    }

    private static void addCompanies(int num) {
        int size = companies.size();
        for (int i=size; i<size+num; i++) {
            companies.add(new Company(Integer.toString(i)));
        }
    }

    private static void addHumanResourceForCompany(Company company) {
        HumanResource humanResource = new HumanResource(company.getCompanyName(), company.getCompanyName(),
                currentDate, company);
        company.setHumanResource(humanResource);
        humanResources.add(humanResource);

    }

    private static void addInterviewersForCompany(Company company, int num) {
        int size = interviewers.size();
        for (int i=size; i<size+num; i++) {
            Interviewer interviewer = new Interviewer(Integer.toString(i), Integer.toString(i), currentDate,
                    "real" + i, company);
            company.addInterviewers(interviewer);
            interviewers.add(interviewer);
        }
    }

    private static void addJobPostingsForCompany(Company company, int num) {
        int size = jobPostings.size();
        for (int i=size; i<size+num; i++) {
            JobPosting jobPosting = new JobPosting(company, currentDate, currentDate.plusDays(30), Integer.toString(i));
            company.addJobPostings(jobPosting);
            jobPostings.add(jobPosting);
        }
    }

    public static void addApplications(int num) {
        Random rand = new Random();
        int size = applications.size();
        for (int i=size; i<size+num; i++) {
            Applicant applicant = applicants.get(rand.nextInt(applicants.size()));
            JobPosting jobPosting = jobPostings.get(rand.nextInt(jobPostings.size()));
            Application application = new Application(jobPosting, applicant, Integer.toString(i));
            applications.add(application);
            applicant.addApplication(application);
            jobPosting.getCompany().addApplicants(applicant);
        }
    }

    private static void initialize(int applicantNum, int companyNum, int interviewerNum, int jobPostingNum) {
        addApplicants(applicantNum);
        addCompanies(companyNum);
        for (Company company: companies) {
            addHumanResourceForCompany(company);
            addInterviewersForCompany(company, interviewerNum);
            addJobPostingsForCompany(company, jobPostingNum);
        }
    }

    private static void newDayUpdate() {

    }

    public static void mainLoop() {
        Random rand = new Random();
        initialize(100, 10, 5, 3);
        for (int i=0; i<=10; i++) {
            currentDate = currentDate.plusDays(1);
            int j = rand.nextInt(50);
            Company company = companies.get(rand.nextInt(companies.size()));
            if (j < 10) {
                addApplications(1);
            } else if (j < 15) {
                addJobPostingsForCompany(company, 1);
            }

        }
    }

    public static void main(String[] args) {
        mainLoop();
    }

}
