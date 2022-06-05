package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import les.projects.consultation_scheduling_program.Components.*;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Message;

import static les.projects.consultation_scheduling_program.Main.lrb;

public class AddUpdateAppointment extends DialogBase {
    private Appointment currentAppointment;
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("appointment_id"), true);
    private final TextFieldLabeled location = new TextFieldLabeled(lrb.getString("location"));
    private final ComboBoxBorderPane customer = new ComboBoxBorderPane(lrb.getString("customer"), Customer.getAllCustomers());
    private final ComboBoxBorderPane contact = new ComboBoxBorderPane(lrb.getString("contact"), Contact.getAllContacts());
    private final TextFieldLabeled type = new TextFieldLabeled(lrb.getString("type"));
    private final DateTimePickerLabeled start = new DateTimePickerLabeled(lrb.getString("start_date_and_time"));
    private final DateTimePickerLabeled end = new DateTimePickerLabeled(lrb.getString("end_date_and_time"));
    private final TextFieldLabeled title = new TextFieldLabeled(lrb.getString("title"));
    private final TextAreaLabeled description = new TextAreaLabeled(lrb.getString("description"));

    public AddUpdateAppointment() {
        super(lrb.getString("create_new_appointment"));
        this.id.setPromptText("(Auto generated)");
        this.build();
    }

    public AddUpdateAppointment(Appointment appointment) {
        super(lrb.getString("edit_appointment"));
        this.build();
        //FIXME - Insert logic to pull appointment record and populate the fields.
        this.currentAppointment = appointment;
        this.id.setInput("" + this.currentAppointment.getId()+ "");
        this.location.setInput(this.currentAppointment.getLocation());
    }

    private void build() {
        VBox center = new VBox();
            center.setPadding(new Insets(0,20,30,20));
            center.getChildren().addAll(id,location,customer,contact,type,start,end,title,description);
            this.center.getChildren().add(center);

        //Add Buttons
        ButtonStandard save = new ButtonStandard(lrb.getString("save"));
        //Used Lambda Here
        save.setOnMouseClicked(saveClick);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        //Used Lambda Here
        cancel.setOnMouseClicked(cancelClick);
        this.bottom.getChildren().addAll(save, new ButtonGap(), cancel);
        this.customer.setComboBoxWidth(200);
        this.contact.setComboBoxWidth(200);
        this.setOnCloseRequest(confirmCancel);
    }

    private final EventHandler<WindowEvent> confirmCancel = event -> this.confirmCancel(event);

    private final EventHandler<MouseEvent> cancelClick = event -> this.confirmCancel(event);

    private final EventHandler<MouseEvent> saveClick = event -> {
        try {
            if (currentAppointment == null) {
                Appointment.add(this.title.getInput(), this.description.getInput(),this.location.getInput(),
                        this.type.getInput(), this.start.getInput(), this.end.getInput(), this.customer.getID(),
                        this.contact.getID());
            }
        } catch (Exception e) {
            //FIXME - Need to create this message in french and english.
            DialogMessage dialog = new DialogMessage("Error", "Something went wrong.");
            dialog.showAndWait();
        }

    };

    private void confirmCancel(Event event) {
        DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
        dialog.showAndWait();

        if(dialog.getResult()) {
            this.close();
        } else {
            event.consume();
        }
    }
}
