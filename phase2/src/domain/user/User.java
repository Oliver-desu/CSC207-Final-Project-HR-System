package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;
import domain.applying.Interview;
import domain.job.JobPosting;
import gui.major.UserMenu;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Class {@code User} is an abstract class that contains basic information
 * for different types of users. All types of user must extend {@code User}.
 *
 * @author group 0120 of CSC207 summer 2019
 * @see UserType
 * @see Applicant
 * @see Employee
 * @since 2019-08-04
 */
public abstract class User implements Serializable {

    /**
     * The type of the {@code User}.
     *
     * @see UserType
     * @see #getUserType()
     */
    private UserType userType;

    /**
     * The username used to log in, also the id for the {@code User} because
     * username is always unique.
     *
     * @see #getUsername()
     */
    private String username;

    /**
     * The password for the user to log in.
     *
     * @see #matchPassword(char[])
     * @see #setPassword(String)
     */
    private char[] password;

    /**
     * Stores any other information besides username and password.
     *
     * @see #setUserDetail(HashMap)
     * @see #getUserDetail()
     */
    private HashMap<String, String> userDetail;


    public User() {
    }

    /**
     * creat a new User with given  username and password
     *
     * @param map      stores username and password
     * @param userType the userType of this user
     */
    public User(HashMap<String, String> map, UserType userType) {
        this.username = map.get("Username:");
        setPassword(map.get("Password:"));
        this.userType = userType;
        setUserDetail(map);
    }

    /**
     * return  the type of this user
     *
     * @return a UserType{@code UserType} represent the type of this user
     * @see Employee#getJobPostings()
     * @see Employee#getInterviews()
     * @see gui.major.MenuPanel #setup()
     */
    public UserType getUserType() {
        return this.userType;
    }

    /**
     * return the username of this user
     * @return a string represent the username of this user
     * @see Company#addRecruiterId(String)
     * @see domain.applying.Application#Application(Applicant, JobPosting)
     * @see Interview#toString()
     * @see Interview#getFilterMap()
     * @see domain.storage.Storage#getUser(String, UserType)
     * @see Applicant#toString()
     * @see Employee#getFilterMap()
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * return any other information besides username and password.
     * @return a hashmap represent any other information besides username and password.
     * @see   Applicant#toString()
     */
    HashMap<String, String> getUserDetail() {
        return userDetail;
    }

    /**
     * set this user's UserDetail
     * @param map represent the any other information beside username and password
     * @see   null
     */
    private void setUserDetail(HashMap<String, String> map) {
        userDetail = new HashMap<>(map);
        userDetail.remove("Username:");
        userDetail.remove("Password:");
    }

    /**
     * return the real name of this user
     * @return a string represent the realname of this user
     * @see   Employee#getFilterMap()
     */
    String getRealName() {
        return this.userDetail.get("First name:") + " " + this.userDetail.get("Last/Family name:");
    }

    /**
     * set this user's password to given password
     * @param password  the new password  need to be set
     * @see   User#User(HashMap, UserType)
     */
    private void setPassword(String password) {
        String validPassword = password.replaceAll(", ", "");
        this.password = validPassword.substring(1, validPassword.length() - 1).toCharArray();
    }

    /**
     * return true if the this user's password match the given password
     * @return true if the this user's password match the given password
     * @see  null
     */
    public boolean matchPassword(char[] password) {
        return Arrays.equals(this.password, password);
    }

    /**
     * return  true is this user is false
     * @return true is this user is false
     * @see domain.storage.UserFactory#createUser(HashMap, UserType)
     */
    public boolean isNull() {
        return false;
    }

    /**
     * return  the ID of this company
     * @return a string represent the ID of this company
     * @see  Employee#getFilterMap()
     * @see UserMenu#getCompany()
     */
    public abstract String getCompanyId() throws NotCompanyWorkerException;

}
