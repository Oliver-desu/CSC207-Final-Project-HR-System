package domain.user;

import java.util.Arrays;
import java.util.HashMap;

public class User {

    private UserType userType;

    private String username;
    private char[] password;
    private String realName;

    public User(HashMap<String, String> map, UserType userType) {
        this.username = map.get("Username:");
        setPassword(map.get("Password:"));
        setRealName(map.get("First name:"), map.get("Last/Family name:"));
        this.userType = userType;
    }


    public User() {
    }

    public UserType getUserType() {
        return this.userType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getRealName() {
        return this.realName;
    }

    private void setPassword(String password) {
        String validPassword = password.replaceAll(", ", "");
        this.password = validPassword.substring(1, validPassword.length() - 1).toCharArray();
    }

    private void setRealName(String firstName, String lastName) {
        this.realName = firstName + " " + lastName;
    }

    public enum UserType {
        APPLICANT,
        INTERVIEWER,
        HR_GENERALIST,
        HR_COORDINATOR
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
}
