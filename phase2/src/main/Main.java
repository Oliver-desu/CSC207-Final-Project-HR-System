package main;

import domain.Exceptions.InvalidInputException;
import domain.Test;
import domain.job.JobPosting;
import domain.storage.Storage;
import domain.user.Company;
import gui.major.Login;

import java.time.LocalDate;

public class Main {
    private Storage storage = new Storage();
    private Login login = new Login(this);
    private static LocalDate localDate;

    public static void main(String[] args) {
        Test test = new Test();
        Company company;
        JobPosting jobPosting;
        test.addApplicants(50);
        for (int numCompanies = 0; numCompanies < 2; numCompanies++) {
            company = test.addCompany();
            test.addInterviewersForCompany(4, company);
            test.addRecruitersForCompany(1, company);
        }

        company = test.getStorage().getCompany("0");
        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getStorage().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("2"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getStorage().getApplicant("0"), jobPosting);
        test.addDraftApplicationForJobPosting(test.getStorage().getApplicant("1"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("3"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("4"), jobPosting);

        jobPosting = test.addJobPosting(company);
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                test.addDraftApplicationForJobPosting(test.getStorage().getApplicant(Integer.toString(i)), jobPosting);
            } else {
                test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant(Integer.toString(i)), jobPosting);
            }
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getStorage().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getStorage().getApplicant("2"), jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.hireApplicants(jobPosting);

        test.addJobPostings(5, company);


//        new Main();
    }

    public Storage getStorage() {
        return storage;
    }

    public void returnToLogin() {
        login.setVisible(true);
    }

    public static LocalDate getLocalDate() {
        if (localDate == null) {
            localDate = LocalDate.now();
        }
        return localDate;
    }

    public static void setDaysElapse(String daysElapse) throws InvalidInputException {
        try {
            int days = Integer.parseInt(daysElapse);
            localDate = getLocalDate().plusDays(days);
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    public void saveSystem() {

    }
}
