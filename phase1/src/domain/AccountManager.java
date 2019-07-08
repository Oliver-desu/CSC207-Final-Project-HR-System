package domain;

import java.util.ArrayList;
import java.util.HashMap;

public class AccountManager{
    HashMap<User.UserType, ArrayList<User> > users;

    void addUser(User user){
//        this implementation may be hard to maintain
        ArrayList<User> lst;
        if(user.getUserType() instanceof Applicant){
            lst = getUsers(User.UserType.Applicant);
            lst.add(user);
            users.put(User.UserType.Applicant, lst);
        }else if(user.getUserType() instanceof  HumanResource){
            lst = getUsers(User.UserType.HumanResource);
            lst.add(user);
            users.put(User.UserType.HumanResource, lst);
        }else if(user.getUserType() instanceof Interviewer){
            lst = getUsers(User.UserType.Interviewer);
            lst.add(user);
            users.put(User.UserType.Interviewer, lst);
        }
    }

    private ArrayList<User> getUsers(User.UserType type){
        return users.get(type);
    }

// Yichun: maybe better to change from ArrayList to Map<User.UserType, Map<userName, User>>
    User getUser(User.UserType userType, String userName){
        ArrayList<User> lst = getUsers(userType);
        for(User user: lst) {
            if (user.getUsername().equals(userName)) {
                return user;
            }
        }
        return null;  //return null if no matched user is found
    }

    boolean matchPassword(User user, String password){
        return user.getPassword().equals(password);
    }

    Object loginUser(String userName, String password){
        //        assume all users have distinct usernames
        User user;
        for(User.UserType type :User.UserType.values()){
            if((user = getUser(type, userName))!= null){
                return user.getUserType();
            }
        }
        return null; //return null if no such user exists
    }

    User createUser(ArrayList<User> users){
        //todo:
        return null;
    }

}