package main;

import domain.Test;
import domain.job.JobPosting;
import domain.storage.InfoCenter;
import domain.user.Company;
import gui.major.Login;

public class Main {
    private InfoCenter infoCenter = new InfoCenter();
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

        company = test.getInfoCenter().getCompany("0");
        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getInfoCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("2"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getInfoCenter().getApplicant("0"), jobPosting);
        test.addDraftApplicationForJobPosting(test.getInfoCenter().getApplicant("1"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("3"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("4"), jobPosting);

        jobPosting = test.addJobPosting(company);
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                test.addDraftApplicationForJobPosting(test.getInfoCenter().getApplicant(Integer.toString(i)), jobPosting);
            } else {
                test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant(Integer.toString(i)), jobPosting);
            }
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getInfoCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getInfoCenter().getApplicant("2"), jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.hireApplicants(jobPosting);

        test.addJobPostings(5, company);


//        new Main();
    }

    public InfoCenter getInfoCenter() {
        return infoCenter;
    }

    public void returnToLogin() {
        login.setVisible(true);
    }
}
