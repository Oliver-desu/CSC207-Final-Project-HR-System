package domain;

import domain.Enums.InterviewStatus;
import domain.Enums.UserType;
import domain.applying.Application;
import domain.applying.Document;
import domain.applying.DocumentManager;
import domain.applying.Interview;
import domain.job.InterviewRound;
import domain.job.InterviewRoundManager;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.storage.UserFactory;
import domain.user.Applicant;
import domain.user.Company;
import domain.user.Employee;
import main.Main;

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
    private Storage storage;


    public Test() {
        this.main = new Main();
        this.storage = this.main.getStorage();
    }

    public static void main(String[] args) {
        new Test();
    }

    public Main getMain() {
        return main;
    }

    public Storage getStorage() {
        return storage;
    }

    public Company getRandomCompany() {
        return storage.getCompany(Integer.toString(new Random().nextInt(numCompanies)));
    }

    public Employee getRandomInterviewer(Company company) {
        ArrayList<String> interviewerIds = company.getInterviewerIds();
        return storage.getEmployee(interviewerIds.get(new Random().nextInt(interviewerIds.size())), UserType.INTERVIEWER);
    }

    public Employee getRandomRecruiter(Company company) {
        ArrayList<String> recruiterIds = company.getRecruiterIds();
        return storage.getEmployee(recruiterIds.get(new Random().nextInt(recruiterIds.size())), UserType.RECRUITER);
    }

    public JobPosting getRandomJobPosting(Company company) {
        ArrayList<String> jobPostingIds = company.getJobPostingIds();
        return storage.getJobPosting(jobPostingIds.get(new Random().nextInt(jobPostingIds.size())));
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
        storage.register(applicant, UserType.APPLICANT);
        numApplicants++;
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
            new UserFactory(storage).createUser(values, UserType.HIRING_MANAGER);
        } catch (Exception e) {
            System.out.println(e);
        }
        Employee hiringManager = storage.getEmployee(values.get("Username:"), UserType.HIRING_MANAGER);
        Company company = storage.getCompany(hiringManager.getCompanyId());
        numCompanies++;

        this.addInterviewersForCompany(1, company);
        this.addRecruitersForCompany(1, company);
        return company;
    }

    public void addInterviewersForCompany(int num, Company company) {
        Employee interviewer;
        HashMap<String, String> values;
        int amount = numInterviewers;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("Email:", "shit@gmail.com");
            interviewer = new Employee(values, company.getId(), UserType.INTERVIEWER);
            storage.register(interviewer, UserType.INTERVIEWER);
            company.addInterviewerId(interviewer.getUsername());
            numInterviewers ++;
        }
    }

    public void addRecruitersForCompany(int num, Company company) {
        Employee recruiter;
        HashMap<String, String> values;
        int amount = numRecruiters;
        for (int i=amount; i<amount+num; i++) {
            values = new HashMap<>();
            values.put("Username:", Integer.toString(i));
            values.put("Password:", "[h, o, l, y, s, h, i, t]");
            values.put("Email:", "shit@gmail.com");
            recruiter = new Employee(values, company.getId(), UserType.RECRUITER);
            storage.register(recruiter, UserType.RECRUITER);
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
        values.put("Close date:", LocalDate.now().plusDays(10).toString());
        values.put("Post date:", LocalDate.now().toString());
        values.put("CV:", "Optional");
        values.put("Cover letter:", "Optional");
        values.put("Reference:", "Optional");
        values.put("Extra document:", "Optional");
        JobPosting jobPosting = new JobPosting(values);
        storage.addJobPosting(jobPosting);
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
        for (int i=amount; i<amount+num; i++) {
            try {
                documentManager.addDocument(new Document(Integer.toString(i), "Some content"));
            } catch (Exception e) {
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
            System.out.println(e);
        }
        return application;
    }

    public Application addSubmittedApplicationForJobPosting(Applicant applicant, JobPosting jobPosting) {
        Application application = this.addDraftApplicationForJobPosting(applicant, jobPosting);
        try {
            application.apply(storage);
            return application;
        } catch (Exception e) {
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
        manager.nextRound();
        return interviewRound;
    }

    public void addNewRoundAndFinishMatching(JobPosting jobPosting, Company company) {
        InterviewRound interviewRound = this.addNewRound(jobPosting);
        Interview interview;
        Employee interviewer;
        for (Application application: interviewRound.getUnmatchedApplications()) {
            interview = application.getInterviewByRound(interviewRound.getRoundName());
            interviewer = this.getRandomInterviewer(company);
            interview.match(interviewer);
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
            manager.hire(application);
        }
        jobPosting.endJobPosting();
    }


}
