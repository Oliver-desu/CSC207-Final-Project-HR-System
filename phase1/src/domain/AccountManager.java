package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class AccountManager implements Serializable {

    private HashMap<String, Company> companies = new HashMap<>();
    private HashMap<String, Applicant> applicants = new HashMap<>();
    private HashMap<String, ArrayList<String>> interviewers = new HashMap<>();


    public HashMap<String, Applicant> getApplicants() {
        return applicants;
    }

    public HashMap<String, Company> getCompanies() {
        return companies;
    }

    public boolean matchPassword(String password, User user) {
        return user.getPassword().equals(password);
    }

    public String matchPosition(String companyName, String interviewer) {
        if (interviewers.get(companyName).contains(interviewer)) {
            return "You've logged in as an interviewer.";
        } else {
            interviewers.get(companyName).add(interviewer);
            return "You've logged in as a new interviewer.";
        }
    }

    public User getUser(String userName, String userType) {
        if (userType.equals("applicant")) {
            return applicants.get(userName);
        } else if (userType.equals("company")) {
            return companies.get(userName);
        } else {
            return null;
        }
    }

    public String loginUser(String userName, String password, String userType) {
        User user = getUser(userName, userType);
        if (user == null || !matchPassword(password, user)) {
            return "Login failed. Wrong username or wrong password.";
        } else {
            return "You've logged in.";
        }
    }

    public User registerUser(String userName, String password, String userType) {
        if (userType.equals("applicant") && !applicants.containsKey(userName)) {
            Applicant applicant = new Applicant(userName, password);
            applicants.put(userName, applicant);
            return applicant;
        } else if (userType.equals("company") && !companies.containsKey(userName)) {
            Company company = new Company(userName, password);
            companies.put(userName, company);
            interviewers.put(userName, new ArrayList<>());
            return company;
        } else {
            return null;
        }
    }

    public ArrayList<String> getInterviewers(String companyName) {
        return interviewers.get(companyName);
    }

    public void addInterviewer(String companyName, String interviewer) {
        interviewers.get(companyName).add(interviewer);
    }
























//    HashMap<User.UserType, ArrayList<User> > users;
//
//    void addUser(User user){
////        this implementation may be hard to maintain
//        ArrayList<User> lst;
//        if(user.getUserType() instanceof Applicant){
//            lst = getUsers(User.UserType.Applicant);
//            lst.add(user);
//            users.put(User.UserType.Applicant, lst);
//        }else if(user.getUserType() instanceof  HumanResource){
//            lst = getUsers(User.UserType.HumanResource);
//            lst.add(user);
//            users.put(User.UserType.HumanResource, lst);
//        }else if(user.getUserType() instanceof Interviewer){
//            lst = getUsers(User.UserType.Interviewer);
//            lst.add(user);
//            users.put(User.UserType.Interviewer, lst);
//        }
//    }
//
//    private ArrayList<User> getUsers(User.UserType type){
//        return users.get(type);
//    }
//
//// Yichun: maybe better to change from ArrayList to Map<User.UserType, Map<userName, User>>
//    User getUser(User.UserType userType, String userName){
//        ArrayList<User> lst = getUsers(userType);
//        for(User user: lst) {
//            if (user.getUsername().equals(userName)) {
//                return user;
//            }
//        }
//        return null;  //return null if no matched user is found
//    }
//
//    boolean matchPassword(User user, String password){
//        return user.getPassword().equals(password);
//    }
//
//    Object loginUser(String userName, String password){
//        //        assume all users have distinct usernames
//        User user;
//        for(User.UserType type :User.UserType.values()){
//            if((user = getUser(type, userName))!= null){
//                return user.getUserType();
//            }
//        }
//        return null; //return null if no such user exists
//    }
//
//    User createUser(ArrayList<User> users){
//        //todo:
//        return null;
//    }

}