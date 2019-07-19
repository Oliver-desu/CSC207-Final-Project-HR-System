package domain.user;

import java.time.LocalDate;
import java.util.HashMap;

public class HRGeneralist extends User {
    private String companyId;

    public HRGeneralist(String username, String password, String companyId, LocalDate dateCreated) {
        super(username, password, dateCreated);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }


    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }

}