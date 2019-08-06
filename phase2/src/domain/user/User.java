package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;

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
     * Constructor for {@code User}.
     * @param map   a hash map of information needed to create a new {@code User}, the content
     *              of this map can be found in class {@code UserRegister}
     * @param userType  the type of this {@code User}
     * @see UserType
     * @see gui.major.UserRegister
     */
    public User(HashMap<String, String> map, UserType userType) {
        this.username = map.get("Username:");
        setPassword(map.get("Password:"));
        this.userType = userType;
        setUserDetail(map);
    }

    public UserType getUserType() {
        return this.userType;
    }

    public String getUsername() {
        return this.username;
    }

    HashMap<String, String> getUserDetail() {
        return userDetail;
    }

    /**
     * Store all the details to {@code userDetail}.
     * @param map contains all the information to create a {@code User}, it is
     *            the same map in the constructor
     * @see #User(HashMap, UserType)
     */
    private void setUserDetail(HashMap<String, String> map) {
        userDetail = new HashMap<>(map);
        userDetail.remove("Username:");
        userDetail.remove("Password:");
    }

    /**
     * Return the real name of this {@code User}.
     * @return the real name of this {@code User}
     * @see #setUserDetail(HashMap)
     */
    String getRealName() {
        return this.userDetail.get("First name:") + " " + this.userDetail.get("Last/Family name:");
    }

    /**
     * Set this user's password.
     * @param password  a string that has format {@code "[(.+, )*.+]"}
     * @see   #User(HashMap, UserType)
     */
    private void setPassword(String password) {
        String validPassword = password.replaceAll(", ", "");
        this.password = validPassword.substring(1, validPassword.length() - 1).toCharArray();
    }

    public boolean matchPassword(char[] password) {
        return Arrays.equals(this.password, password);
    }

    /**
     * Indicate whether this {@code User} is {@code NullUser} or not. Always return false for
     * a normal {@code User}.
     * @return false
     * @see domain.storage.UserFactory#createUser(HashMap, UserType)
     */
    public boolean isNull() {
        return false;
    }

    /**
     * Abstract method that returns id(name) of the company this {@code User}
     * works for if the {@code User} is an {@code Employee}, otherwise throw
     * exception.
     */
    public abstract String getCompanyId() throws NotCompanyWorkerException;

}
