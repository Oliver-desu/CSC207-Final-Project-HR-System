package main;

import domain.storage.CompanyPool;
import domain.storage.JobPool;
import domain.storage.UserPool;

public class Main {
    private JobPool jobPool = new JobPool();
    private UserPool userPool = new UserPool();
    private CompanyPool companyPool = new CompanyPool();

    public CompanyPool getCompanyPool() {
        return companyPool;
    }

    public JobPool getJobPool() {
        return jobPool;
    }

    public UserPool getUserPool() {
        return userPool;
    }
}
