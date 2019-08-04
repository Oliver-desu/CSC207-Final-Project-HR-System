package domain.storage;

import domain.Enums.UserType;
import domain.Exceptions.UnmatchedPasswordException;
import domain.Exceptions.UserAlreadyExistsException;
import domain.Exceptions.WrongEmailFormatException;
import domain.user.*;

import java.io.Serializable;
import java.util.HashMap;

public class UserFactory implements Serializable {

    private InfoCenter infoCenter;


    public UserFactory(InfoCenter userPool) {
        this.infoCenter = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, UserType registerType) {
        validValues(infoMap, registerType);
        User user;
        if (registerType.equals(UserType.APPLICANT)) user = createApplicant(infoMap);
        else if (registerType.equals(UserType.HR_COORDINATOR)) user = createCoordinator(infoMap);
        else if (registerType.equals(UserType.INTERVIEWER)) user = createInterviewer(infoMap);
        else if (registerType.equals(UserType.HR_GENERALIST)) user = createGeneralistAndCompany(infoMap);
        else user = new NullUser();

        if (!user.isNull()) {
            this.infoCenter.register(user, registerType);
        }
        return user;
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        return new Applicant(infoMap);
    }

    private User createCoordinator(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (companyExists(companyId)) {
            Company company = infoCenter.getCompany(companyId);
            company.addHRCoordinatorId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, UserType.HR_COORDINATOR);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (companyExists(companyId)) {
            Company company = infoCenter.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, UserType.INTERVIEWER);
        } else {
            return new NullUser();
        }
    }

    private User createGeneralistAndCompany(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!companyExists(companyId)) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id", companyId);
            values.put("generalistId", infoMap.get("Username:"));
            this.infoCenter.registerCompany(new Company(values));
            return new CompanyWorker(infoMap, companyId, UserType.HR_GENERALIST);
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return infoCenter.getCompany(companyId) != null;
    }

    private void validValues(HashMap<String, String> infoMap, UserType registerType) {
        if (infoMap.get("Password:").equals("[]")) {
            throw new UnmatchedPasswordException();
        } else if (!infoMap.get("Email:").matches(".+@(.+\\.)com")) {
            throw new WrongEmailFormatException();
        } else if (infoCenter.getCompanyWorker(infoMap.get("Username:"), registerType) != null) {
            throw new UserAlreadyExistsException();
        } else if (registerType.equals(UserType.HR_GENERALIST) && companyExists(infoMap.get("Company id:"))) {

        }
    }


}
