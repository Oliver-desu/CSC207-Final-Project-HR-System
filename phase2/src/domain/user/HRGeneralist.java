package domain.user;

import java.util.HashMap;

public class HRGeneralist extends User {

    private String companyId;


    public HRGeneralist(HashMap<String, String> map, String companyId) {
        super(map);
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return this.companyId;
    }

}