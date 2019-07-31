package domain.storage;

import java.util.HashMap;

public class CompanyPool {

    private HashMap<String, Company> companies = new HashMap<>();


    public Company getCompany(String id) {
        return this.companies.get(id);
    }

    public void addCompany(String id, Company company) {
        this.companies.put(id, company);
    }

}
