package les.projects.consultation_scheduling_program.DataClasses;

import les.projects.consultation_scheduling_program.Main;

import java.util.*;

public class User {
    private int userID;
    private String userName, userPassword;
    private static List<User> allUsers = new ArrayList<User>();

    public User(int id, String name, String password) {
        this.userID = id;
        this.userName = name;
        this.userPassword = password;
    }

    public static void addUser(String name, String password) {
        int nextID = (allUsers.size() > 0)?Collections.max(allUsers, Comparator.comparing(i -> i.getUserID())).getUserID() + 1:1;
        allUsers.add(new User(
                nextID,
                name, password
        ));
    }

    public int getUserID() {
        return this.userID;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public static User[] getAllUsers() {
        return allUsers.toArray(new User[allUsers.size()]);
    }

    public static Boolean verifyUser(String userName, String password) {
        //Determine if the userName exists
        Boolean anyMatch = allUsers.stream().anyMatch(i -> i.getUserName().equals(userName));
        if(anyMatch) {
            for(User user: allUsers) {
                if(user.getUserName().equals(userName) && user.getUserPassword().equals(password)) {
                    Main.currentUser = user;
                    return true;
                }
            }
        }
        return false;
    }
}
