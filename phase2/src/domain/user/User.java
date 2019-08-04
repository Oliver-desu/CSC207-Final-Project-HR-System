package domain.user;

import domain.Enums.UserType;
import domain.Exceptions.NotCompanyWorkerException;
import domain.show.ShowAble;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public abstract class User implements Serializable, ShowAble {

    private UserType userType;
    private String username;
    private char[] password;
    private HashMap<String, String> userDetail;


    public User() {
    }

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

    public HashMap<String, String> getUserDetail() {
        return userDetail;
    }

    private void setUserDetail(HashMap<String, String> map) {
        userDetail = new HashMap<>(map);
        userDetail.remove("Username:");
        userDetail.remove("Password:");
    }

    String getRealName() {
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

    public abstract String getCompanyId() throws NotCompanyWorkerException;

    @Override
    public String toString() {
        return getInfoString("Username", username) +
                getInfoString("Name", getRealName()) +
                getInfoString("Email", userDetail.get("Email:")) +
                getInfoString("Occupation", userDetail.get("Occupation:")) +
                getInfoString("Work experiences", userDetail.get("Work experiences:")) +
                getInfoString("Education background", userDetail.get("Education background:")) +
                getInfoString("Major in", userDetail.get("Major in:"));
    }
}
