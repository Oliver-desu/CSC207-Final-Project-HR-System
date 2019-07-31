package domain.storage;

import domain.user.*;

import java.util.HashMap;

public class UserFactory {

    private InfoCenter infoCenter;


    public UserFactory(InfoCenter userPool) {
        this.infoCenter = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, InfoCenter.UserType registerType) {
        User user;
        if (registerType.equals(InfoCenter.UserType.APPLICANT)) user = createApplicant(infoMap);
        else if (registerType.equals(InfoCenter.UserType.HR_COORDINATOR)) user = createCoordinator(infoMap);
        else if (registerType.equals(InfoCenter.UserType.INTERVIEWER)) user = createInterviewer(infoMap);
        else if (registerType.equals(InfoCenter.UserType.HR_GENERALIST)) user = createGeneralist(infoMap);
        else user = new NullUser();

        if (!user.isNull()) this.infoCenter.register(user, registerType);
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
                infoCenter.getHRCoordinator(infoMap.get("Username:")) == null) {
            Company company = infoCenter.getCompany(companyId);
            company.addHRCoordinatorId(infoMap.get("Username:"));
            return new HRCoordinator(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                infoCenter.getInterviewer(infoMap.get("Username:")) == null) {
            Company company = infoCenter.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new Interviewer(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private User createGeneralist(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && !companyExists(companyId) &&
                infoCenter.getHRGeneralist(infoMap.get("Username:")) == null) {
            return new HRGeneralist(infoMap, companyId);
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return infoCenter.getCompany(companyId) != null;
    }


}
