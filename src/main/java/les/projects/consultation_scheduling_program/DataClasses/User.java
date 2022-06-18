package les.projects.consultation_scheduling_program.DataClasses;

import java.util.*;

public class User {
    private final int id;
    private final String userName, userPassword;
    private static final List<User> allUsers = new ArrayList<>();

    public User(int id, String name, String password) {
        this.id = id;
        this.userName = name;
        this.userPassword = password;
    }

    @Override
    public final String toString() {
        return this.userName;
    }

    public static void addUser(String name, String password) {
        int nextID = (allUsers.size() > 0)?Collections.max(allUsers, Comparator.comparing(User::getId)).getId() + 1:1;
        allUsers.add(new User(
                nextID,
                name, password
        ));
    }

    public final int getId() {
        return this.id;
    }

    public final String getUserName() {
        return this.userName;
    }

    public static String getUserName(int id) { return allUsers.stream().filter(i -> i.id == id).findFirst().get().userName; }

    public static Boolean verifyUser(String userName, String inputPassword) {
        //Determine if the userName exists
        Boolean anyMatch = allUsers.stream().anyMatch(i -> i.getUserName().equals(userName));
        if(anyMatch) {
            String objPassword = allUsers.stream().filter(i -> i.userName.equals(userName)).findFirst().get().userPassword;
            if(inputPassword.equals(objPassword)) return true;
        }
        return false;
    }

    public static User getUserByUserName(String userName) {
            return allUsers.stream().filter(i -> i.userName.equals(userName)).findFirst().get();
    }

    public static User getById(int id) {
        return allUsers.stream().filter(i -> i.id == id).findFirst().get();
    }
}
