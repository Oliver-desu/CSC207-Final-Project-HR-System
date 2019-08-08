package model;

import main.Main;
import model.enums.InterviewStatus;
import model.enums.UserType;
import model.job.*;
import model.storage.EmploymentCenter;
import model.storage.UserFactory;
import model.user.Applicant;
import model.user.Company;
import model.user.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Test {

    private int numApplicants;
    private int numInterviewers;
    private int numCompanies;
    private int numRecruiters;
    private int numJobPostings;

    private Main main;
    private EmploymentCenter employmentCenter;


    public Test() {
        this.main = new Main();
        this.employmentCenter = this.main.getEmploymentCenter();
    }

    public static void main(String[] args) {
        new Test();
    }

    public Main getMain() {
        return main;
    }

    public EmploymentCenter getEmploymentCenter() {
        return employmentCenter;
    }

    public Company getRandomCompany() {
        return employmentCenter.getCompany(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public Employee getRandomInterviewer(Company company) {
        ArrayList<String> interviewerIds = company.getInterviewerIds();
        return employmentCenter.getEmployee(interviewerIds.get(new Random().nextInt(interviewerIds.size())), UserType.INTERVIEWER);
    }

    public Employee getRandomRecruiter(Company company) {
        ArrayList<String> recruiterIds = company.getRecruiterIds();
        return employmentCenter.getEmployee(recruiterIds.get(new Random().nextInt(recruiterIds.size())), UserType.RECRUITER);
    }

    public JobPosting getRandomJobPosting(Company company) {
        ArrayList<String> jobPostingIds = company.getJobPostingIds();
        return employmentCenter.getJobPosting(jobPostingIds.get(new Random().nextInt(jobPostingIds.size())));
    }

    public String getRandomDocumentName(DocumentManager documentManager) {
        Random rand = new Random();
        ArrayList<String> docNames = documentManager.getAllDocNames();
        return docNames.get(rand.nextInt(docNames.size()));
    }

    public Applicant addApplicant() {
        HashMap<String, String> values = new HashMap<>();
        values.put("Username:", Integer.toString(numApplicants));
        values.put("First name:", "Holy");
        values.put("Last/Family name:", "Shit");
        values.put("Password:", "[h, o, l, y, s, h, i, t]");
        values.put("Email:", "shit@gmail.com");
        Applicant applicant = new Applicant(values);
        this.addDocuments(5, applicant.getDocumentManager());
        employmentCenter.register(applicant, UserType.APPLICANT);
        numApplicants++;
        applicant.addmessage(new InterviewRound("welcome"));
        return applicant;
    }

    public void addApplicants(int num) {
        for (int i = 0; i < num; i++) {
            this.addApplicant();
        }
    }

    public Company addCompany() {
        HashMap<String, String> values = new HashMap<>();
        values.put("Username:", Integer.toString(numCompanies));
        values.put("Password:", "[h, o, l, y, s, h, i, t]");
        values.put("Email:", "shit@gmail.com");
        values.put("Company id:", Integer.toString(numCompanies));
        try {
            new UserFactory(employmentCenter).createUser(values, UserType.HIRING_MANAGER);
        } catch (Exception e) {
            System.out.println("addCompany");
            System.out.println(e);
        }
        Employee hiringManager = employmentCenter.getEmployee(values.get("Username:"), UserType.HIRING_MANAGER);
        Company company = employmentCenter.getCompany(hiringManager.getCompanyId());
        numCompanies++;

        this.addInterviewersForCompany(1, company);
        this.addRecruitersForCompany(1, company);
        return company;
    }

    public void addInterviewersForCompany(int num, Company company) {
        Employee interviewer;
        HashMap<String, String> values;
        int amount = numInterviewers;
        for (int i = amount; i < amount + num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("Email:", "shit@gmail.com");
            interviewer = new Employee(values, company.getId(), UserType.INTERVIEWER);
            employmentCenter.register(interviewer, UserType.INTERVIEWER);
            company.addInterviewerId(interviewer.getUsername());
            numInterviewers++;
        }
    }

    public void addRecruitersForCompany(int num, Company company) {
        Employee recruiter;
        HashMap<String, String> values;
        int amount = numRecruiters;
        for (int i = amount; i < amount + num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("Email:", "shit@gmail.com");
            recruiter = new Employee(values, company.getId(), UserType.RECRUITER);
            employmentCenter.register(recruiter, UserType.RECRUITER);
            company.addRecruiterId(recruiter.getUsername());
            numRecruiters++;
        }
    }

    public JobPosting addJobPosting(Company company) {
        HashMap<String, String> values = new HashMap<>();
        values.put("Job id:", Integer.toString(numJobPostings));
        values.put("Company id:", company.getId());
        values.put("Position name:", "Boss");
        values.put("Num of positions:", Integer.toString(2));
        values.put("Close date:", LocalDate.now().minusDays(3).toString());
        values.put("Post date:", LocalDate.now().minusDays(2).toString());
        values.put("CV:", "Optional");
        values.put("Cover letter:", "Optional");
        values.put("Reference:", "Optional");
        values.put("Extra document:", "Optional");
        JobPosting jobPosting = new JobPosting(values);
        employmentCenter.addJobPosting(jobPosting);
        company.addJobPostingId(jobPosting.getJobId());
        this.getRandomRecruiter(company).addFile(jobPosting);
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
        for (int i = amount; i < amount + num; i++) {
            try {
                documentManager.addDocument(new Document(Integer.toString(i), "Some content"));
            } catch (Exception e) {
                System.out.println("addDocuments");
                System.out.println(e);
            }
        }
    }

    public Application addDraftApplicationForJobPosting(Applicant applicant, JobPosting jobPosting) {
        Application application = new Application(applicant, jobPosting);
        for (String docName : applicant.getDocumentManager().getAllDocNames()) {
            try {
                application.getDocumentManager().addDocument(applicant.getDocumentManager().findDocument(docName));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        try {
            applicant.addApplication(jobPosting.getJobId(), application);
        } catch (Exception e) {
            System.out.println("addDraftApplicationForJobPosting");
            System.out.println(e);
        }
        return application;
    }

    public Application addSubmittedApplicationForJobPosting(Applicant applicant, JobPosting jobPosting) {
        Application application = this.addDraftApplicationForJobPosting(applicant, jobPosting);
        try {
            application.apply(employmentCenter);
            return application;
        } catch (Exception e) {
            System.out.println("addSubmittedApplicationForJobPosting");
            System.out.println(e);
            return null;
        }
    }

    public InterviewRound addNewRound(JobPosting jobPosting) {
        jobPosting.close();
        jobPosting.setInterviewRoundManager();
        InterviewRoundManager manager = jobPosting.getInterviewRoundManager();
        manager.checkStatus();
        InterviewRound interviewRound = new InterviewRound(Integer.toString(manager.getInterviewRounds().size()));
        manager.addInterviewRound(interviewRound);
        try {
            manager.nextRound(getEmploymentCenter());
        } catch (Exception e) {
            System.out.println("addNewRound");
            System.out.println(e);
        }
        return interviewRound;
    }

    public void addNewRoundAndFinishMatching(JobPosting jobPosting, Company company) {
        InterviewRound interviewRound = this.addNewRound(jobPosting);
        Interview interview;
        Employee interviewer;
        for (Application application : interviewRound.getUnmatchedApplications()) {
            interview = application.getInterviewByRound(interviewRound.getRoundName());
            interviewer = this.getRandomInterviewer(company);
            try {
                interview.match(interviewer);
            } catch (Exception e) {
                System.out.println("addNewRoundAndFinishMatching");
                System.out.println(e);
            }
        }
        interviewRound.checkStatus();
    }

    public void endCurrentRound(JobPosting jobPosting) {
        InterviewRound interviewRound = jobPosting.getInterviewRoundManager().getCurrentInterviewRound();
        Interview interview;
        for (Application application : interviewRound.getCurrentRoundApplications()) {
            interview = application.getInterviewByRound(interviewRound.getRoundName());
            if (new Random().nextBoolean()) {
                interview.setStatus(InterviewStatus.FAIL);
            } else {
                interview.setStatus(InterviewStatus.PASS);
            }
        }
        interviewRound.checkStatus();
    }

    public void hireApplicants(JobPosting jobPosting) {
        InterviewRoundManager manager = jobPosting.getInterviewRoundManager();
        for (Application application : manager.getRemainingApplications()) {
            try {
                manager.hire(application);
            } catch (Exception e) {
                System.out.println("hireApplicants");
                System.out.println(e);
            }
        }
        jobPosting.endJobPosting();
    }


}
