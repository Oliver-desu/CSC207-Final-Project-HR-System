package domain.user;

import java.time.LocalDate;
import java.util.HashMap;

public class HRGeneralist extends User {

    private String companyId;


    public HRGeneralist() {
        super();
        this.setUsername("HRGeneralist");
        this.companyId = "Google";
    }

    public HRGeneralist(HashMap<String, String> map, String companyId) {
        super(map);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }

}