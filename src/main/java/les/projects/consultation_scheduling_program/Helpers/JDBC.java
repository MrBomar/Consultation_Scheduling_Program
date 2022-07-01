package les.projects.consultation_scheduling_program.Helpers;

import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * This class contains the specific methods needed to instantiate a database connection and execute queries against
 * the database.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    /**
     * This method creates a new connection with the database.
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * This method closes the connection with the database.
     */
    public static void closeConnection(){
        try {
            connection.close();
        } catch (Exception e) {
            //Do nothing.
        }
    }

    /**
     * This method creates and executes a statement against the database, then returns the result set.
     * @param query An SQL String to be executed.
     * @return The result set created by the SQL statement.
     * @throws Exception If the query is not successfully executed the exception is caught and rethrown.
     */
    public static ResultSet newResultSet(String query) throws Exception {
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return stmt.executeQuery(query);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage(Message.DatabaseError);
            dialog.showAndWait();
            throw e;
        }
    }
}
