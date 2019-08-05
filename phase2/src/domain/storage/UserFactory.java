package domain.storage;

import domain.Enums.UserType;
import domain.Exceptions.UnmatchedPasswordException;
import domain.Exceptions.UserAlreadyExistsException;
import domain.Exceptions.WrongEmailFormatException;
import domain.user.*;

import java.io.Serializable;
import java.util.HashMap;

public class UserFactory implements Serializable {

    private Storage Storage;


    public UserFactory(Storage userPool) {
        this.Storage = userPool;
    }

    public User createUser(HashMap<String, String> infoMap, UserType registerType) {
        validValues(infoMap, registerType);
        User user;
        if (registerType.equals(UserType.APPLICANT)) user = createApplicant(infoMap);
        else if (registerType.equals(UserType.RECRUITER)) user = createRecruiter(infoMap);
        else if (registerType.equals(UserType.INTERVIEWER)) user = createInterviewer(infoMap);
        else if (registerType.equals(UserType.HIRING_MANAGER)) user = createHiringManagerAndCompany(infoMap);
        else user = new NullUser();

        if (!user.isNull()) {
            this.Storage.register(user, registerType);
        }
        return user;
    }

    private User createApplicant(HashMap<String, String> infoMap) {
        return new Applicant(infoMap);
    }

    private User createRecruiter(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (companyExists(companyId)) {
            Company company = Storage.getCompany(companyId);
            company.addRecruiterId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, UserType.RECRUITER);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (companyExists(companyId)) {
            Company company = Storage.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new CompanyWorker(infoMap, companyId, UserType.INTERVIEWER);
        } else {
            return new NullUser();
        }
    }

    private User createHiringManagerAndCompany(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (!companyExists(companyId)) {
            HashMap<String, String> values = new HashMap<>();
            values.put("id", companyId);
            values.put("hiringManagerId", infoMap.get("Username:"));
            this.Storage.registerCompany(new Company(values));
            return new CompanyWorker(infoMap, companyId, UserType.HIRING_MANAGER);
        } else {
            return new NullUser();
        }
    }

    private boolean companyExists(String companyId) {
        return Storage.getCompany(companyId) != null;
    }

    private void validValues(HashMap<String, String> infoMap, UserType registerType) {
        if (infoMap.get("Password:").equals("[]")) {
            throw new UnmatchedPasswordException();
        } else if (!infoMap.get("Email:").matches(".+@(.+\\.)com")) {
            throw new WrongEmailFormatException();
        } else if (Storage.getCompanyWorker(infoMap.get("Username:"), registerType) != null) {
            throw new UserAlreadyExistsException();
        } else if (registerType.equals(UserType.HIRING_MANAGER) && companyExists(infoMap.get("Company id:"))) {

        }
    }


}
