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
import static les.projects.consultation_scheduling_program.Main.mrb;

/**
 * This class renders the login dialog.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Login extends DialogBase {
    private final TextFieldLabeledLarge userId = new TextFieldLabeledLarge(lrb.getString("User_ID"), "", false);
    private final PasswordFieldLabeledLarge password = new PasswordFieldLabeledLarge(lrb.getString("Password"), "", false);

    /**
     * This constructor instantiates the login dialog. Two lambdas are used to eliminate instantiating EventListeners
     * and to make the code more concise and readable.
     */
    public Login() {
        super(lrb.getString("program_title"));
        User.loadData();

        TextFieldLabeledLarge timeZone = new TextFieldLabeledLarge(lrb.getString("Timezone"), DTC.getLocalTimeZone(), true);
        VBox center = new VBox();
            center.getChildren().addAll(userId,password,timeZone);
            this.center.getChildren().add(center);

        //Add Buttons
        ButtonStandard login = new ButtonStandard(lrb.getString("Login"), 1, 4);
        login.setOnMouseClicked(this::attemptLogin);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("Cancel"), 2, 4);
        cancel.setOnMouseClicked(e -> Platform.exit());
        this.bottom.getChildren().addAll(login,new ButtonGap(),cancel);
        this.setOnCloseRequest(e -> Platform.exit());
    }

    /**
     * This method attempts to match the username and password provided against the records in the database.
     * @param event Mouse event.
     */
    private void attemptLogin(Event event) {
        if(User.verifyUser(userId.getInput(), password.getInput())) {
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
                DialogMessage dialog = new DialogMessage(Message.LoginFail);
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
