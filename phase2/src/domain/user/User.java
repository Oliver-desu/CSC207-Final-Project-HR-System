package domain.user;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class User {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String username;
    private String password;
    private String realName;
    private LocalDate dateCreated;

    public User(HashMap<String, String> map) {
        this.username = map.get("username");
        this.password = map.get("password");
        this.dateCreated = LocalDate.parse(map.get("dateCreated"), formatter);
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
