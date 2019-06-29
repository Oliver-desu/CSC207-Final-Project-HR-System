package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class HumanResource extends User {

    private static ArrayList<HumanResource> allHumanResources = new ArrayList<HumanResource>();
    private Company company;

    public HumanResource(String username, String password, LocalDate dateCreated, Company company) {
        super(username, password, dateCreated);
        this.company = company;
        allHumanResources.add(this);
    }

    public static ArrayList<HumanResource> getAllHumanResources() {
        return allHumanResources;
    }

    public Company getCompany() {
        return this.company;
    }

//   TODO: Unfinished methods:

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = ((User) this).getAccount();
        return result;
    }

    @Override
    public String toString() {
        return null;
    }

    public ArrayList<Applicant> getApplicants(){
        return null;
    }

}
