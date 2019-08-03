package domain.user;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public class User implements Serializable {

    public enum UserType {
        APPLICANT,
        INTERVIEWER,
        HR_GENERALIST,
        HR_COORDINATOR
    }

    private UserType userType;

    private String username;
    private char[] password;
    private HashMap<String, String> userDetail;

    public User(HashMap<String, String> map, UserType userType) {
        this.username = map.get("Username:");
        setPassword(map.get("Password:"));
        this.userType = userType;
        setUserDetail(map);
    }


    public User() {
    }

    public UserType getUserType() {
        return this.userType;
    }

    public String getUsername() {
        return this.username;
    }

    public HashMap<String, String> getUserDetail() {
        return userDetail;
    }

    private void setUserDetail(HashMap<String, String> map) {
        userDetail = new HashMap<>(map);
        userDetail.remove("Username:");
        userDetail.remove("Password:");
    }

    private String getRealName() {
        return this.userDetail.get("First name:") + " " + this.userDetail.get("Last/Family name:");
    }

    private void setPassword(String password) {
        String validPassword = password.replaceAll(", ", "");
        this.password = validPassword.substring(1, validPassword.length() - 1).toCharArray();
    }

    public boolean matchPassword(char[] password) {
        return Arrays.equals(this.password, password);
    }

    public boolean isNull() {
        return false;
    }

    public String getCompanyId() {
        return "";
    }

    @Override
    public String toString() {
        return "Username: " + username + "\n" +
                "Name: " + getRealName() + "\n" +
                "Email: " + userDetail.get("Email:") + "\n" +
                "Occupation: " + userDetail.get("Occupation:") + "\n" +
                "Work experiences: " + userDetail.get("Work experiences:") + "\n" +
                "Education background: " + userDetail.get("Education background:") + "\n" +
                "Major in: " + userDetail.get("Major in:");
    }
}
