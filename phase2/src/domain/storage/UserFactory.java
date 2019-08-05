package domain.storage;

import domain.Enums.UserType;
import domain.Exceptions.UnmatchedPasswordException;
import domain.Exceptions.UserAlreadyExistsException;
import domain.Exceptions.WrongEmailFormatException;
import domain.user.*;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class {@code UserFactory} is a factory class that creates {@code User}
 * for other classes.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserType
 * @see User
 * @see Applicant
 * @see Employee
 * @see Company
 * @since 2019-08-04
 */
public class UserFactory implements Serializable {

    /**
     * The {@code Storage} used to store all the users created.
     *
     * @see     Storage
     * @see     #createUser(HashMap, UserType)
     */
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
            return new Employee(infoMap, companyId, UserType.RECRUITER);
        } else {
            return new NullUser();
        }
    }

    private User createInterviewer(HashMap<String, String> infoMap) {
        String companyId = infoMap.get("Company id:");
        if (companyExists(companyId)) {
            Company company = Storage.getCompany(companyId);
            company.addInterviewerId(infoMap.get("Username:"));
            return new Employee(infoMap, companyId, UserType.INTERVIEWER);
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
            return new Employee(infoMap, companyId, UserType.HIRING_MANAGER);
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
        } else if (Storage.getEmployee(infoMap.get("Username:"), registerType) != null) {
            throw new UserAlreadyExistsException();
        }
    }


}
