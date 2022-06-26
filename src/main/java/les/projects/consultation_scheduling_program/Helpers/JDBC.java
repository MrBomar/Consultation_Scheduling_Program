package les.projects.consultation_scheduling_program.Helpers;

import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection(){
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static ResultSet newResultSet(String query) throws Exception {
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            return stmt.executeQuery(query);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage("Database Error", "Query could not be executed.");
            dialog.showAndWait();
            throw e;
        }
    }
}
