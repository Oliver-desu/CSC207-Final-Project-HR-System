import java.time.LocalDate;
import java.util.HashMap;

public abstract class User {

    private String username;
    private String password;
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

    public LocalDate getDateCreated() {
        return this.dateCreated;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public HashMap<String, String> getAccount() {
        HashMap<String, String> result = new HashMap<String, String>();
        result.put("Username", this.username);
        result.put("Password", this.password);
        result.put("Date Created", this.dateCreated.toString());
        return result;
    }

}
