package domain;

import domain.applying.Application;
import domain.job.InterviewRound;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;
import domain.user.Applicant;
import domain.user.HRCoordinator;
import domain.user.HRGeneralist;
import domain.user.Interviewer;

public class ShortTest {

    public JobPool jobPool = new JobPool();
    public UserPool userPool = new UserPool();
    public CompanyPool companyPool = new CompanyPool();
    public Company company;
    public JobPosting jobPosting;
    public Applicant applicant1;
    public Applicant applicant2;
    public Application application1;
    public Application application2;
    public HRGeneralist hrGeneralist;
    public HRCoordinator hrCoordinator;
    public Interviewer interviewer;
    public InterviewRound interviewRound;

    ShortTest() {
        company = new Company();
        companyPool.addCompany(company.getId(), company);
        jobPosting = new JobPosting();
        jobPool.addJobPosting(jobPosting.getJobId(), jobPosting);
        company.addJobPostingId(jobPosting.getJobId());
        interviewRound = new InterviewRound();
        jobPosting.addInterviewRound(interviewRound);
        applicant1 = new Applicant();
        applicant2 = new Applicant();
        userPool.register(applicant1);
        userPool.register(applicant2);
        application1 = new Application();
    }

}
