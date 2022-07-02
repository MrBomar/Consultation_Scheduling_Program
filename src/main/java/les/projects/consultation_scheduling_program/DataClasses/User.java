package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;
import les.projects.consultation_scheduling_program.Views.DialogMessage;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class stores the data related to the users in the database and provides method for adding, removing,
 * and updating that data.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class User {
    private final int id;
    private final String userName, userPassword;
    private static List<User> allUsers = new ArrayList<>();

    /**
     * This constructor instantiates a User object.
     * @param id The record ID of the user.
     * @param name The name of the user.
     * @param password The password of the user.
     */
    public User(int id, String name, String password) {
        this.id = id;
        this.userName = name;
        this.userPassword = password;
    }

    /**
     * This method overrides the default toString() method for the User class.
     * @return The name of the user.
     */
    @Override
    public final String toString() {
        return this.userName;
    }

    /**
     * This method creates a new user and adds it to the database, then refreshes the list of users.
     * @param name The name of the user.
     * @param password The password of the user.
     */
    public static void addUser(String name, String password) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM users");

            rs.moveToInsertRow();
            rs.updateString("User_Name", name);
            rs.updateString("Password", password);
            rs.updateTimestamp("Create_Date", DTC.currentTimestamp());
            rs.updateString("Created_By", Main.currentUser.getUserName());
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.insertRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(Message.RecordNotAdded,new String[]{lrb.getString("user")});
            dialog.showAndWait();
        }
    }

    /**
     * This method gets the record ID of the user.
     * @return The record ID of the user.
     */
    public final int getId() { return this.id; }

    /**
     * This method gets the name of the user.
     * @return The name of the user.
     */
    public final String getUserName() { return this.userName; }

    /**
     * This method gets the name of the user using the record ID of the user. A lambda is used to filter the user list
     * instead of using a Predicate, which is more verbose and harder to understand.
     * @param id The record ID of the user.
     * @return The name of the user.
     */
    public static String getUserName(int id) {
        return allUsers.stream().filter(i -> i.id == id).findFirst().get().userName;
    }

    /**
     * This method compares the username and password entered in the login form and verifies if it matches any of
     * the user records. Two lambdas are used for filter lists instead of using Predicates, which are more verbose
     * and harder to understand.
     * @param userName The supplied name of the user.
     * @param inputPassword The supplied password of the user.
     * @return Returns true if the username and password match a record in the user list.
     */
    public static Boolean verifyUser(String userName, String inputPassword) {
        //Determine if the userName exists
        Boolean anyMatch = allUsers.stream().anyMatch(i -> i.getUserName().equals(userName));
        if(anyMatch) {
            String objPassword = allUsers.stream().filter(i -> i.userName.equals(userName)).findFirst().get().userPassword;
            return inputPassword.equals(objPassword);
        }
        return false;
    }

    /**
     * This method gets a User object by matching the supplied username. A lambda is used here to filter the list of
     * users instead of using a Predicate, which is more verbose and harder to use.
     * @param userName The username of the user.
     * @return The user object related to the username.
     */
    public static User getUserByUserName(String userName) {
        return allUsers.stream().filter(i -> i.userName.equals(userName)).findFirst().get();
    }

    /**
     * This method finds and return the User object using the record ID of the user.
     * @param id The record ID of the user.
     * @return A User object related to the record ID.
     */
    public static User getById(int id) { return allUsers.stream().filter(i -> i.id == id).findFirst().get(); }

    /**
     * This method load the data from the database and creates User objects from the Users table.
     */
    public static void loadData() {
        try {
            //Get results from database
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * from users";
            ResultSet rs = stmt.executeQuery(qry);

            if (!rs.next()) {
                DialogMessage dialog = new DialogMessage(Message.DataNotFound,new String[]{lrb.getString("user")});
                dialog.showAndWait();
            } else {
                allUsers = FXCollections.observableList(new ArrayList<>());

                //Loop through results and add users to the list.
                do {
                    User user = new User(
                            rs.getInt("User_ID"),
                            rs.getString("User_Name"),
                            rs.getString("Password")
                    );
                    allUsers.add(user);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(Message.DataNotLoaded,new String[]{lrb.getString("user")});
            dialog.showAndWait();
        }
    }
}
