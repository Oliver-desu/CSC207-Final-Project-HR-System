package main;

import domain.Exceptions.InvalidInputException;
import domain.Test;
import domain.job.JobPosting;
import domain.storage.EmploymentCenter;
import domain.user.Company;
import gui.major.LoginFrame;

import java.time.LocalDate;

public class Main {
    private EmploymentCenter employmentCenter = new EmploymentCenter();
    private LoginFrame login = new LoginFrame(this);
    private static LocalDate localDate;

    public static void main(String[] args) {
        Test test = new Test();
        Company company;
        JobPosting jobPosting;
        test.addApplicants(50);
        for (int numCompanies = 0; numCompanies < 2; numCompanies++) {
            company = test.addCompany();
            test.addInterviewersForCompany(4, company);
        }

        company = test.getEmploymentCenter().getCompany("0");
        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getEmploymentCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("2"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getEmploymentCenter().getApplicant("0"), jobPosting);
        test.addDraftApplicationForJobPosting(test.getEmploymentCenter().getApplicant("1"), jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("3"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("4"), jobPosting);

        jobPosting = test.addJobPosting(company);
        for (int i = 0; i < 20; i++) {
            if (i % 3 == 0) {
                test.addDraftApplicationForJobPosting(test.getEmploymentCenter().getApplicant(Integer.toString(i)), jobPosting);
            } else {
                test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant(Integer.toString(i)), jobPosting);
            }
        }
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.addNewRound(jobPosting);

        jobPosting = test.addJobPosting(company);
        test.addDraftApplicationForJobPosting(test.getEmploymentCenter().getApplicant("0"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("1"), jobPosting);
        test.addSubmittedApplicationForJobPosting(test.getEmploymentCenter().getApplicant("2"), jobPosting);
        test.addNewRoundAndFinishMatching(jobPosting, company);
        test.endCurrentRound(jobPosting);
        test.hireApplicants(jobPosting);

        test.addJobPostings(5, company);


//        new Main();
    }

    public EmploymentCenter getEmploymentCenter() {
        return employmentCenter;
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
