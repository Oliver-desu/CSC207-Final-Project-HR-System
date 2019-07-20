package domain.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;

public class User {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String username;
    private char[] password;
    private String realName;
    private LocalDate dateCreated;

    public User(HashMap<String, String> map) {
        this.username = map.get("username");
        setPassword(map.get("password").toCharArray());
        this.dateCreated = LocalDate.parse(map.get("dateCreated"), formatter);
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

    public boolean matchPassword(char[] password) {
        return Arrays.equals(this.password, password);
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserDetail() {
        return null;
    }

    public boolean isNull() {
        return false;
    }
}
