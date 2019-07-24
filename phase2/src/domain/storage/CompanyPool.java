package domain.storage;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyPool {

    private static HashMap<String, Company> companies = new HashMap<>();


    public static ArrayList<Company> getCompanies() {
        ArrayList<Company> tempCompanies = new ArrayList<>();
        tempCompanies.addAll(companies.values());
        return tempCompanies;
    }

    public static Company getCompany(String id) {
        return companies.get(id);
    }

    public static void addCompany(String id, Company company) {
        companies.put(id, company);
    }

}
