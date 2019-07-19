package domain.user;

import java.time.LocalDate;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String realName;
    private LocalDate dateCreated;

    public User(String username, String password, LocalDate dateCreated) {
        this.username = username;
        this.password = password;
        this.dateCreated = dateCreated;
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
