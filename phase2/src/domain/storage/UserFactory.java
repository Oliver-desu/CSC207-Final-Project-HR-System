package domain.storage;

import domain.user.*;

import java.util.HashMap;

public class UserFactory {

    private InfoCenter infoCenter;


    public UserFactory(InfoCenter userPool) {
        this.infoCenter = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, InfoCenter.UserType registerType) {
        if (registerType.equals(InfoCenter.UserType.APPLICANT)) return createApplicant(infoMap);
        else if (registerType.equals(InfoCenter.UserType.HR_COORDINATOR)) return createCoordinator(infoMap);
        else if (registerType.equals(InfoCenter.UserType.INTERVIEWER)) return createInterviewer(infoMap);
        else if (registerType.equals(InfoCenter.UserType.HR_GENERALIST)) return createGeneralist(infoMap);
        else return new NullUser();
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        if (!infoMap.get("Password:").isEmpty() &&
                infoCenter.getApplicant(infoMap.get("Username:")) == null) {
            Applicant applicant = new Applicant(infoMap);
            infoCenter.register(applicant);
            return applicant;
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
            HRCoordinator coordinator = new HRCoordinator(infoMap, companyId);
            infoCenter.register(coordinator);
            return coordinator;
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
            Interviewer interviewer = new Interviewer(infoMap, companyId);
            infoCenter.register(interviewer);
            return interviewer;
        } else {
            return new NullUser();
        }
    }

    private User createGeneralist(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!infoMap.get("Password:").isEmpty() && !companyExists(companyId) &&
                infoCenter.getHRGeneralist(infoMap.get("Username:")) == null) {
            HRGeneralist generalist = new HRGeneralist(infoMap, companyId);
            infoCenter.register(generalist);
            return generalist;
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return infoCenter.getCompany(companyId) != null;
    }


}
