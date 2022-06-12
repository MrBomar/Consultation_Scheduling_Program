package les.projects.consultation_scheduling_program.DataClasses;

import java.util.*;

public class User {
    private int userID;
    private String userName, userPassword;
    private static List<User> allUsers = new ArrayList<>();

    public User(int id, String name, String password) {
        this.userID = id;
        this.userName = name;
        this.userPassword = password;
    }

    @Override
    public final String toString() {
        return this.userName;
    }

    public static void addUser(String name, String password) {
        int nextID = (allUsers.size() > 0)?Collections.max(allUsers, Comparator.comparing(i -> i.getUserID())).getUserID() + 1:1;
        allUsers.add(new User(
                nextID,
                name, password
        ));
    }

    public final int getUserID() {
        return this.userID;
    }

    public final String getUserName() {
        return this.userName;
    }

    public final static String getUserName(int id) { return allUsers.stream().filter(i -> i.userID == id).findFirst().get().userName; }

    public static Boolean verifyUser(String userName, String inputPassword) {
        //Determine if the userName exists
        Boolean anyMatch = allUsers.stream().anyMatch(i -> i.getUserName().equals(userName));
        if(anyMatch) {
            String objPassword = allUsers.stream().filter(i -> i.userName.equals(userName)).findFirst().get().userPassword;
            if(inputPassword.equals(objPassword)) return true;
        }
        return false;
    }
}
