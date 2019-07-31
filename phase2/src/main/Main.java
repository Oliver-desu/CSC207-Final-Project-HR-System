package main;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;
import domain.user.Applicant;
import gui.major.Login;

public class Main {
    private JobPool jobPool = new JobPool();
    private UserPool userPool = new UserPool();
    private CompanyPool companyPool = new CompanyPool();
    private Login login = new Login(this);

    public static void main(String[] args) {
        Test test = new Test();
        Company company;
        Applicant applicant;
        JobPosting jobPosting;
        for (int numApplicants = 0; numApplicants < 10; numApplicants++) {
            test.addApplicant();
        }
        for (int numCompanies = 0; numCompanies < 2; numCompanies++) {
            company = test.addCompany();
            test.addInterviewersForCompany(4, company);
            test.addCoordinatorsForCompany(1, company);
        }

        company = test.getCompanyPool().getCompany("0");
        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("2"), jobPosting);
        test.addNewRound(jobPosting, company);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("1"), jobPosting);
        test.addNewRound(jobPosting, company);

        jobPosting = test.addJobPosting(company);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("3"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("4"), jobPosting);

//        new Main();
    }

    public CompanyPool getCompanyPool() {
        return companyPool;
    }

    public JobPool getJobPool() {
        return jobPool;
    }

    public UserPool getUserPool() {
        return userPool;
    }

    public void returnToLogin() {
        login.setVisible(true);
    }
}
