package main;

import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;
import gui.major.Login;

public class Main {
    private JobPool jobPool = new JobPool();
    private UserPool userPool = new UserPool();
    private CompanyPool companyPool = new CompanyPool();
    private Login login = new Login(this);

    public static void main(String[] args) {
//        Test test = new Test();
//        Applicant applicant = test.addApplicant();
//        Company company = test.addCompany();
//        JobPosting jobPosting = test.getRandomJobPosting(company);
//        test.addJobPostings(2, company);
//
//        test.addSubmittedApplicationForJobPosting(applicant, jobPosting);
//        test.addNewRoundAndFinishMatching(jobPosting, company);

        new Main();
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
