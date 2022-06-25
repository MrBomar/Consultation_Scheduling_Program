package les.projects.consultation_scheduling_program.Views;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import les.projects.consultation_scheduling_program.Components.*;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.User;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.LoginActivity;
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class Login extends DialogBase {
    private final TextFieldLabeledLarge userId = new TextFieldLabeledLarge(lrb.getString("user_id"), "", false);
    private final PasswordFieldLabeledLarge password = new PasswordFieldLabeledLarge(lrb.getString("password"), "", false);

    public Login() {
        super(lrb.getString("program_title"));
        User.loadData();

        TextFieldLabeledLarge timeZone = new TextFieldLabeledLarge(lrb.getString("timezone"), DTC.getLocalTimeZone(), true);
        VBox center = new VBox();
            center.getChildren().addAll(userId,password,timeZone);
            this.center.getChildren().add(center);

        //Add Buttons
        ButtonStandard login = new ButtonStandard(lrb.getString("login"), 1, 4);
        login.setOnMouseClicked(this::attemptLogin);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"), 2, 4);
        cancel.setOnMouseClicked(e -> Platform.exit());
        this.bottom.getChildren().addAll(login,new ButtonGap(),cancel);
        this.setOnCloseRequest(e -> Platform.exit());
    }

    private void attemptLogin(Event event) {
        if(User.verifyUser(userId.getInput(), password.getInput())) {
            System.out.println("User ID and password verified.");
            LoginActivity.loginAttempt(userId.getInput(), true);
            try {
                Main.currentUser = User.getUserByUserName(userId.getInput());
                Main.loadData();
                Appointment.upcomingAppointments();
                Main.appStage.setScene(new Scene(new Console(new AppointmentsView())));
                Main.appStage.show();
                close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                DialogMessage dialog = new DialogMessage("Login Failed","We could not match the Username provided. Login denied.");
                dialog.showAndWait();
                userId.resetInput();
                password.resetInput();
            }
        } else {
            LoginActivity.loginAttempt(userId.getInput(), false);
            DialogMessage dialog = new DialogMessage(Message.LoginFail);
            dialog.showAndWait();
            userId.resetInput();
            password.resetInput();
        }
    }
}
