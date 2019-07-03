package login;
import domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {

    private static LocalDate currentDate = LocalDate.now().minusDays(1);
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
        Random rand = new Random();
        int size = jobPostings.size();
        for (int i=size; i<size+num; i++) {
            JobPosting jobPosting = new JobPosting(company, currentDate, currentDate.plusDays(40), Integer.toString(i));
            jobPosting.setNumPositions(1 + rand.nextInt(2));
            jobPosting.setInterviewRounds(
                    new ArrayList<>(List.of("Phone", "In-person 1", "In-person 2", "In-person 3"))
            );
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
            if (jobPosting.isOpen() && jobPosting.isValidApplicant(applicant)) {
                Application application = new Application(jobPosting, applicant, Integer.toString(i));
                applications.add(application);
                applicant.addApplication(application);
                jobPosting.getCompany().addApplicants(applicant);
                jobPosting.addApplication(application);
            }
        }
    }

    public static void addNewRoundInterviews(JobPosting jobPosting) {
        Random rand = new Random();
        jobPosting.nextRound();
        ArrayList<Interviewer> tempInterviewers = jobPosting.getCompany().getInterviewers();
        ArrayList<Interview> tempInterviews = jobPosting.getRoundInterviews(jobPosting.getCurrentRound());
        for (Interview interview: tempInterviews) {
            interview.pendingInterview();
            interview.setId(Integer.toString(interviews.size()));
            interview.setInterviewer(tempInterviewers.get(rand.nextInt(tempInterviewers.size())));
            interview.setDate(currentDate.plusDays(rand.nextInt(10)));
            interviews.add(interview);
            interview.getApplication().getApplicant().addInterviews(interview);
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

    private static void updateJobPostings() {
        for (JobPosting jobPosting: jobPostings) {
            jobPosting.updateJobPosting(currentDate);
            ArrayList<Interview> tempInterviews = jobPosting.getRoundInterviews(jobPosting.getCurrentRound());
            if (jobPosting.canHire() && tempInterviews.size() >= jobPosting.getNumPositions()) {
                jobPosting.setFilled();
                for (Interview interview : tempInterviews) {
                    interview.failInterview();
                }
                for (int i = 0; i < jobPosting.getNumPositions(); i++) {
                    tempInterviews.get(i).passInterview();
                }
            } else if (jobPosting.canHire()) {
                jobPosting.setFilled();
            } else if (jobPosting.canStartNextRound()) {
                addNewRoundInterviews(jobPosting);
            }
        }
    }

    private static void updateInterviews() {
        Random rand = new Random();
        for (Interview interview: interviews) {
            if (interview.getDate().equals(currentDate)) {
                if (rand.nextInt(2) == 0) {
                    interview.passInterview();
                } else {
                    interview.failInterview();
                }
            }
        }
    }

    private static void newDayUpdate() {
        updateInterviews();
        updateJobPostings();
    }

    public static void mainLoop(int initApplicantNum, int initCompanyNum, int initInterviewerNum, int initJobPostingNum,
                                int numDayPass) {
        Random rand = new Random();
        initialize(initApplicantNum, initCompanyNum, initInterviewerNum, initJobPostingNum);
        for (int i=0; i<=numDayPass; i++) {
            currentDate = currentDate.plusDays(1);
            int j = rand.nextInt(100);
            Company company = companies.get(rand.nextInt(companies.size()));
            if (j < 5) {
                addApplications(3);
            } else if (j < 15) {
                addApplications(2);
            } else if (j < 45) {
                addApplications(1);
            } else if (j < 50) {
                addJobPostingsForCompany(company, 1);
            }
            newDayUpdate();
        }
    }

    public static void showAllInstances() {
        System.out.println("Current date: " + currentDate);
        System.out.println();
        for (Company company: Company.getAllCompanies()) {
            System.out.println("Company name: " + company.getCompanyName());
            System.out.println("Number of Interviewers: " + company.getInterviewers().size());
            System.out.println("JobPostings:");
            for (JobPosting jobPosting: company.getJobPostings()) {
                System.out.println("Id:" + jobPosting.getId());
                System.out.println("Post date: " + jobPosting.getPostDate());
                System.out.println("Close date: " + jobPosting.getCloseDate());
                System.out.println("Number of Position: " + jobPosting.getNumPositions());
                System.out.println("Status: " + jobPosting.getStatus());
                System.out.println("Number of initial applications: " + jobPosting.getAllApplications().size());
                System.out.println("Current round: " + jobPosting.getCurrentRound());
                System.out.println("Number of remaining applications: " + jobPosting.getPassApplications().size());
                System.out.println();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        mainLoop(10, 3, 5, 1, 365);
        showAllInstances();
    }

}
