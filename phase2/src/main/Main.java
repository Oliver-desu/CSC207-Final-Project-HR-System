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
