package domain.storage;

import domain.Enums.UserType;
import domain.Exceptions.UnmatchedPasswordException;
import domain.Exceptions.UserAlreadyExistsException;
import domain.Exceptions.WrongEmailFormatException;
import domain.user.*;
import gui.major.UserRegister;

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

    private static final long serialVersionUID = -6178992956146363626L;


    /**
     * The {@code Storage} used to store all the users created.
     *
     * @see Storage
     * @see #createUser(HashMap, UserType)
     */
    private Storage Storage;


    /**
     * Create a new user factory.
     *
     * @param userPool the {@code Storage} used to store all users created
     * @see gui.major.UserRegister
     */
    public UserFactory(Storage userPool) {
        this.Storage = userPool;
    }

    /**
     * Create and return a new user given his/her basic information and user type.
     *
     * @param infoMap      a hash map that stores all information needed to create a new user
     * @param registerType the user type of the new user being created
     * @return a new {@code User}
     * @see UserRegister
     */
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

    /**
     * Create a new applicant.
     *
     * @param infoMap the map storing basic information of the applicant
     * @return the new {@code Applicant}
     * @see UserFactory#createUser(HashMap, UserType)
     */
    private User createApplicant(HashMap<String, String> infoMap) {
        return new Applicant(infoMap);
    }

    /**
     * Create a new recruiter.
     *
     * @param infoMap the map storing basic information of the recruiter
     * @return the new {@code Employee} of type {@code UserType.RECRUITER}
     * @see UserFactory#createUser(HashMap, UserType)
     */
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

    /**
     * Create a new Interviewer.
     *
     * @param infoMap the map that contains basic information of the interviewer
     * @return the new {@code Employee} of type {@code UserType.INTERVIEWER}
     * @see UserFactory#createUser(HashMap, UserType)
     */
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

    /**
     * Create a new company and its hiring manager.
     *
     * @param infoMap the map that contains the basic information of the new company and its hiring manager
     * @return the new {@code Employee} of type {@code UserType.HIRING_MANAGER}
     * @see UserFactory#createUser(HashMap, UserType)
     */
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

    /**
     * Return whether a company exists.
     * A helper method for createRecruiter, createInterviewer, createHiringManagerAndCompany.
     *
     * @param companyId the id of the company to be checked
     * @return whether the company exists
     * @see UserFactory#createRecruiter(HashMap)
     * @see UserFactory#createInterviewer(HashMap)
     * @see UserFactory#createHiringManagerAndCompany(HashMap)
     */
    private boolean companyExists(String companyId) {
        return Storage.getCompany(companyId) != null;
    }

    /**
     * Check whether the information in {@code InfoMap} is valid to create a new user.
     * It is a helper method for createUser.
     *
     * @param infoMap      the map that consists of basic information about the user
     * @param registerType the type of the new user
     * @see UserFactory#createUser(HashMap, UserType)
     */
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
