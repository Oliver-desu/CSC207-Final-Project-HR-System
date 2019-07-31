package domain.storage;

import domain.user.*;

import java.util.HashMap;

public class UserFactory {

    private UserPool userPool;


    public UserFactory(UserPool userPool) {
        this.userPool = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, UserPool.UserType registerType) {
        if (registerType.equals(UserPool.UserType.APPLICANT)) return createApplicant(infoMap);
        else if (registerType.equals(UserPool.UserType.HR_COORDINATOR)) return createCoordinator(infoMap);
        else if (registerType.equals(UserPool.UserType.INTERVIEWER)) return createInterviewer(infoMap);
        else if (registerType.equals(UserPool.UserType.HR_GENERALIST)) return createGeneralist(infoMap);
        else return new NullUser();
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        if (!infoMap.get("Password:").isEmpty() &&
                userPool.getApplicant(infoMap.get("Username:")) == null) {
            Applicant applicant = new Applicant(infoMap);
            userPool.register(applicant);
            return applicant;
        } else {
            return new NullUser();
        }
    }

    private User createCoordinator(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                userPool.getHRCoordinator(infoMap.get("Username:")) == null) {
            Company company = userPool.getCompany(companyId);
            company.addHRCoordinatorId(infoMap.get("Username:"));
            HRCoordinator coordinator = new HRCoordinator(infoMap, companyId);
            userPool.register(coordinator);
            return coordinator;
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && companyExists(companyId) &&
                userPool.getInterviewer(infoMap.get("Username:")) == null) {
            Company company = userPool.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            Interviewer interviewer = new Interviewer(infoMap, companyId);
            userPool.register(interviewer);
            return interviewer;
        } else {
            return new NullUser();
        }
    }

    private User createGeneralist(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && !companyExists(companyId) &&
                userPool.getHRGeneralist(infoMap.get("Username:")) == null) {
            HRGeneralist generalist = new HRGeneralist(infoMap, companyId);
            userPool.register(generalist);
            return generalist;
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return userPool.getCompany(companyId) != null;
    }


}
