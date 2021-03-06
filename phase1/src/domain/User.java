package domain;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String username;
    private String password;


    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return this.username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User name: " + this.username + "\n" +
                "Password: " + this.password;
    }



    public boolean matchPassword(String password) {
        return (this.password == password);
    }



}
