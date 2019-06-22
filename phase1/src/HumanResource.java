import java.util.ArrayList;

public class HumanResource extends User {

    private static ArrayList<HumanResource> allHumanResources = new ArrayList<HumanResource>();
    private Company company;

    public HumanResource(String username, String password, Company company) {
        super(username, password);
        this.company = company;
        allHumanResources.add(this);
    }

    public static ArrayList<HumanResource> getAllHumanResources() {
        return allHumanResources;
    }

    public Company getCompany() {
        return this.company;
    }

//    Unfinished methods:

    @Override
    public String toString() {
        return null;
    }


}
