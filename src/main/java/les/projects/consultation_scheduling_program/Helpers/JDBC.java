package les.projects.consultation_scheduling_program.Helpers;

import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static les.projects.consultation_scheduling_program.Main.lrb;


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
            e.printStackTrace();
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

    /**
     * This method pulls a single record from the specified table.
     * @param recordID The ID number of the record
     * @param table The table the record is located on.
     * @return Returns the result of the executed query.
     * @throws Exception Thrown if query cannot be executed.
     */
    public static ResultSet getRecord(int recordID, String table) throws Exception {
        String idColumn;
        switch(table){
            case "appointments":
                idColumn = "Appointment_ID";
                break;
            case "contacts":
                idColumn = "Contact_ID";
                break;
            case "countries":
                idColumn = "Country_ID";
                break;
            case "customers":
                idColumn = "Customer_ID";
                break;
            case "first_level_divisions":
                idColumn = "Division_ID";
                break;
            case "users":
                idColumn = "User_ID";
                break;
            default:
                idColumn = "";
        }
        try {
            return newResultSet("SELECT * FROM " + table + " WHERE " + idColumn + " = " + recordID);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotUpdated,
                    new String[] {lrb.getString("record")}
            );
            dialog.showAndWait();
            throw e;
        }
    }

    /**
     * This method updates a single field on a single record in the database.
     * @param recordID The ID number of the record to be modified.
     * @param table The name of the table the record is stored on.
     * @param column The name of the column to be edited.
     * @param value The value to insert in the record column.
     */
    public static void updateStringValue(int recordID, String table, String column, String value) {
        try {
            ResultSet rs = getRecord(recordID, table);
            rs.next();
            rs.updateString(column, value);
            rs.updateRow();
        } catch (Exception e) {
            //Do nothing
        }
    }

    /**
     * This method updates a single field on a single record in the database.
     * @param recordID The ID number of the record to be modified.
     * @param table The name of the table the record is stored on.
     * @param column The name of the column to be edited.
     * @param value The value to insert in the record column.
     */
    public static void updateIntValue(int recordID, String table, String column, int value) {
        try {
            ResultSet rs = getRecord(recordID, table);
            rs.next();
            rs.updateInt(column, value);
            rs.updateRow();
        } catch (Exception e) {
            //Do nothing
        }
    }
}
