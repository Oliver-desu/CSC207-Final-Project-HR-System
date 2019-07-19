package domain.user;

import java.util.HashMap;

public class HRGeneralist extends User {
    private String companyId;

    public HRGeneralist() {
    }

    public String getCompanyId() {
        return null;
    }


    public static boolean isValidInfo(HashMap<String, String> values) {
        return true;
    }

}