package main;

import gui.general.LoginFrame;
import model.Test;
import model.exceptions.CannotSaveSystemException;
import model.exceptions.InvalidInputException;
import model.job.JobPosting;
import model.storage.EmploymentCenter;
import model.user.Company;

import java.io.*;
import java.time.LocalDate;

public class Main {

    private static final String DATA_LOCATION = "\\phase2\\data.ser";
    private static LocalDate currentDate;
    private EmploymentCenter employmentCenter = new EmploymentCenter();
    private LoginFrame login;
    private boolean successfullyLoaded = true;

    public Main() {
        loadSystem();
        login = new LoginFrame(this);
    }

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
        for (int i = 0; i < 3; i++) {
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

    public static LocalDate getCurrentDate() {
        if (currentDate == null) {
            currentDate = LocalDate.now();
        }
        return currentDate;
    }

    public void returnToLogin() {
        login.setVisible(true);
    }

    public static void setDaysElapse(String daysElapse) throws InvalidInputException {
        try {
            int days = Integer.parseInt(daysElapse);
            currentDate = getCurrentDate().plusDays(days);
        } catch (NumberFormatException e) {
            throw new InvalidInputException();
        }
    }

    public EmploymentCenter getEmploymentCenter() {
        return employmentCenter;
    }

    private String getPath() {
        return System.getProperty("user.dir") + DATA_LOCATION;
    }

    public boolean isSuccessfullyLoaded() {
        return successfullyLoaded;
    }

    private void loadSystem() {
        try {
            InputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(getPath()));
            ObjectInput input = new ObjectInputStream(bufferedInputStream);
            employmentCenter = (EmploymentCenter) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException e) {
            successfullyLoaded = false;
        }
    }

    public void saveSystem() throws CannotSaveSystemException {
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(getPath()));
            ObjectOutput output = new ObjectOutputStream(bufferedOutputStream);
            output.writeObject(employmentCenter);
            output.close();
        } catch (IOException e) {
            throw new CannotSaveSystemException();
        }
    }
}
