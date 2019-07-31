package domain.user;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

public class User {

    private String username;
    private char[] password;
    private String realName;
    private LocalDate dateCreated;


    public User() {
    }

    public User(HashMap<String, String> map) {
        this.username = map.get("Username:");
        this.dateCreated = LocalDate.now();
        setPassword(map.get("Password:"));
    }

    public String getUsername() {
        return this.username;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPassword(String password) {
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
}
