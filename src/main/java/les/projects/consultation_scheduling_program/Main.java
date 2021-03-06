package les.projects.consultation_scheduling_program;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import les.projects.consultation_scheduling_program.DataClasses.*;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Views.Login;
import java.io.IOException;
import java.util.*;

/**
 * This class is the launch of the application.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Main extends Application {
    public static Stage appStage;
    public static double appWidth = 1305;
    public static double appHeight = 800;
    public static User currentUser = null;
    public static final Locale locale = Locale.getDefault();
    public static final ResourceBundle lrb = ResourceBundle.getBundle("Labels", locale);
    public static final ResourceBundle mrb = ResourceBundle.getBundle("Messages", locale);


    /**
     * This method overrides the default start method for Application. This is a required method for JavaFX.
     * @param stage The base of the application view.
     * @throws IOException Self explanatory.
     */
    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;
        appStage.setMaxWidth(appWidth);
        appStage.setMinWidth(appWidth);
        appStage.setWidth(appWidth);
        appStage.setMaxHeight(appHeight);
        appStage.setMinHeight(appHeight);
        appStage.setHeight(appHeight);
        appStage.setTitle(lrb.getString("program_title"));
        appStage.setResizable(false);

        logoutView();
    }

    /**
     * This method is called when the application is closed. Is used to close the database connection on exit.
     */
    @Override
    public void stop() {
        JDBC.closeConnection();
    }

    /**
     * This method is the entry method for the application. This is a required Java method.
     * @param args Array of string to use in starting arguments.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
    }

    /**
     * This method is used for closing the active windows and returning to the login screen.
     */
    public static void logoutView() {
        //Create Scene
        currentUser = null;
        Scene scene = new Scene(new Pane());
        scene.setFill(Color.DARKGRAY);

        appStage.setScene(scene);
        appStage.show();

        //Display the login screen
        Login login = new Login();
        login.show();
    }

    /**
     * This method is used for the initial loading of data from the database.
     */
    public static void loadData() {
        Contact.loadData();
        Country.loadData();
        Division.loadData();
        Customer.loadData();
        Appointment.loadData();
    }
}