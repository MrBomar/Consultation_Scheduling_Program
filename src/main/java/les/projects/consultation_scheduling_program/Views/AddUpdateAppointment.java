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

public class AddUpdateAppointment extends DialogBase {
    private Appointment currentAppointment = null;
    private final TextFieldLabeled id = new TextFieldLabeled("Appointment ID", true);
    private final TextFieldLabeled location = new TextFieldLabeled("Location");
    private final ComboBoxSelector customer = new ComboBoxSelector(Customer.getAllCustomers());
    private final ComboBoxSelector contact = new ComboBoxSelector(Contact.getAllContacts());
    private final TextFieldLabeled type = new TextFieldLabeled("Type");
    private final DateTimePickerLabeled start = new DateTimePickerLabeled("Start Date and Time");
    private final DateTimePickerLabeled end = new DateTimePickerLabeled("End Date and Time");
    private final TextFieldLabeled title = new TextFieldLabeled("Title");
    private final TextAreaLabeled description = new TextAreaLabeled("Description");

    public AddUpdateAppointment() {
        super("Create New Appointment");
        this.build();
    }

    public AddUpdateAppointment(Appointment appointment) {
        super("Edit Appointment");
        this.build();
        //FIXME - Insert logic to pull customer record and populate the fields.
        this.currentAppointment = appointment;
        this.id.setInput("" + this.currentAppointment.getId()+ "");
        this.location.setInput(this.currentAppointment.getLocation());
        //START HERE -- Need to convert the customer field into a ComboBox

    }

    private void build() {
        VBox center = new VBox();
            center.setPadding(new Insets(0,20,30,20));
            center.getChildren().addAll(id,location,customer,contact,type,start,end,title,description);
            this.center.getChildren().add(center);

        //Add Buttons
        ButtonStandard save = new ButtonStandard("Save");
        //Used Lambda Here
        save.setOnMouseClicked(saveClick);
        ButtonStandard cancel = new ButtonStandard("Cancel");
        //Used Lambda Here
        cancel.setOnMouseClicked(cancelClick);
        this.bottom.getChildren().addAll(save, new ButtonGap(), cancel);

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
