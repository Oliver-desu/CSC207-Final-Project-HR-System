package main;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.Company;
import domain.storage.JobPool;
import domain.storage.UserPool;
import gui.major.Login;

public class Main {
    private JobPool jobPool = new JobPool();
    private UserPool userPool = new UserPool();
    private Login login = new Login(this);

    public static void main(String[] args) {
        Test test = new Test();
        Company company;
        JobPosting jobPosting;
        test.addApplicants(50);
        for (int numCompanies = 0; numCompanies < 2; numCompanies++) {
            company = test.addCompany();
            test.addInterviewersForCompany(4, company);
            test.addCoordinatorsForCompany(1, company);
        }

        company = test.getUserPool().getCompany("0");
        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("2"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("1"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("3"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("4"), jobPosting);

        jobPosting = test.addJobPosting(company);
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant(Integer.toString(i)), jobPosting);
            } else {
                test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant(Integer.toString(i)), jobPosting);
            }
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getUserPool().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getUserPool().getApplicant("2"), jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.hireApplicants(jobPosting);

        test.addJobPostings(5, company);


//        new Main();
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
