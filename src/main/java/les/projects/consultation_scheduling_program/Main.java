package les.projects.consultation_scheduling_program;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import les.projects.consultation_scheduling_program.DataClasses.*;
import les.projects.consultation_scheduling_program.Views.Login;

import java.io.IOException;
import java.util.*;

public class Main extends Application {
    public static Stage appStage;
    public static double appWidth = 1305;
    public static double appHeight = 800;
    public static User currentUser = null;
    public static final Locale locale = Locale.getDefault();
    public static final ResourceBundle lrb = ResourceBundle.getBundle("Labels", locale);
    public static final ResourceBundle mrb = ResourceBundle.getBundle("Messages", locale);

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

    public static void main(String[] args) {
        launch();
    }

    public static void logoutView() {
        //Create Scene
        currentUser = null;
        Scene scene = new Scene(new Pane());
        scene.setFill(Color.DARKGRAY);

        appStage.setScene(scene);
        appStage.show();
        Login login = new Login();
        login.show();
    }

    public static void loadData() {
        Appointment.loadData();
        Contact.loadData();
        Country.loadData();
        Division.loadData();
        Customer.loadData();
    }
}