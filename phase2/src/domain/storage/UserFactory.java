package domain.storage;

import domain.user.Applicant;
import domain.user.CompanyWorker;
import domain.user.NullUser;
import domain.user.User;

import java.io.Serializable;
import java.util.HashMap;

public class UserFactory implements Serializable {

    private InfoCenter infoCenter;


    public UserFactory(InfoCenter userPool) {
        this.infoCenter = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, User.UserType registerType) {
        User user;
        if (registerType.equals(User.UserType.APPLICANT)) user = createApplicant(infoMap);
        else if (registerType.equals(User.UserType.HR_COORDINATOR)) user = createCoordinator(infoMap);
        else if (registerType.equals(User.UserType.INTERVIEWER)) user = createInterviewer(infoMap);
        else if (registerType.equals(User.UserType.HR_GENERALIST)) user = createGeneralistAndCompany(infoMap);
        else user = new NullUser();

        if (!user.isNull()) {
            this.infoCenter.register(user, registerType);
        }
        return user;
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        if (!infoMap.get("Password:").isEmpty() &&
                infoCenter.getApplicant(infoMap.get("Username:")) == null) {
            return new Applicant(infoMap);
        } else {
            return new NullUser();
        }
    }

    private User createCoordinator(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                infoCenter.getCompanyWorker(infoMap.get("Username:"), User.UserType.HR_COORDINATOR) == null) {
            Company company = infoCenter.getCompany(companyId);
            company.addHRCoordinatorId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, User.UserType.HR_COORDINATOR);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                infoCenter.getCompanyWorker(infoMap.get("Username:"), User.UserType.INTERVIEWER) == null) {
            Company company = infoCenter.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, User.UserType.INTERVIEWER);
        } else {
            return new NullUser();
        }
    }

    private User createGeneralistAndCompany(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && !companyExists(companyId) &&
                infoCenter.getCompanyWorker(infoMap.get("Username:"), User.UserType.HR_GENERALIST) == null) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id", companyId);
            values.put("generalistId", infoMap.get("Username:"));
            this.infoCenter.registerCompany(new Company(values));
            return new CompanyWorker(infoMap, companyId, User.UserType.HR_GENERALIST);
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return infoCenter.getCompany(companyId) != null;
    }


}
