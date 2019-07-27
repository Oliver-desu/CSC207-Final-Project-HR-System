package domain.storage;

import java.util.ArrayList;
import java.util.HashMap;

public class CompanyPool {

    private HashMap<String, Company> companies = new HashMap<>();


    public ArrayList<Company> getCompanies() {
        return new ArrayList<>(this.companies.values());
    }

    public Company getCompany(String id) {
        return this.companies.get(id);
    }

    public void addCompany(String id, Company company) {
        this.companies.put(id, company);
    }

}
