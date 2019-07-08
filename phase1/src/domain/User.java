package domain;

public abstract class User {

    private String username;
    private String password;
    private Object type;

    public enum UserType {Applicant, HumanResource, Interviewer}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.type = new Applicant();//todo
    }

    public User(String username, String password, UserType position, Company company) {
        if(position == UserType.HumanResource){
            this.type = new HumanResource(company);
        }else if(position == UserType.Interviewer){
            this.type = new Interviewer(company); //todo
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    public Object getUserType(){
        return this.type;
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
                "Date Created: " + this.password;
    }
}
