package domain.user;

import java.time.LocalDate;
import java.util.HashMap;

public class User {
    private String username;
    private String password;
    private String realName;
    private LocalDate dateCreated;

    public User() {
    }

    public String getUsername() {
        return null;
    }

    public String getPassword() {
        return null;
    }

    public String getRealName() {
        return null;
    }

    public void setPassword(String password) {
    }

    public void setRealName(String realName) {
    }

    public static boolean isValidInfo(HashMap<String, String> values) {
        return false;
    }
}
