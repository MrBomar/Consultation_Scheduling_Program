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

/**
 * This class renders the Add/Update Appointment dialog.
 * @author Leslie C. Bomar 3rd
 */
public class AddUpdateAppointment extends DialogBase {
    //Object Data
    private Appointment currentAppointment;

    //Fields
    private final ComboBoxBorderPane contact = new ComboBoxBorderPane(lrb.getString("contact"), Contact.getAllContacts(), true);
    private final ComboBoxBorderPane customer = new ComboBoxBorderPane(lrb.getString("customer"), Customer.getAllCustomers(), true);
    private final TextAreaLabeled description = new TextAreaLabeled(lrb.getString("description"));
    private final DateTimePickerLabeled end = new DateTimePickerLabeled(lrb.getString("end_date_and_time"));
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("appointment_id"), false, true);
    private final TextFieldLabeled location = new TextFieldLabeled(lrb.getString("location"), true, false);
    private final DateTimePickerLabeled start = new DateTimePickerLabeled(lrb.getString("start_date_and_time"));
    private final TextFieldLabeled title = new TextFieldLabeled(lrb.getString("title"), true, false);
    private final TextFieldLabeled type = new TextFieldLabeled(lrb.getString("type"), false, false);

    //Properties
    private boolean changed = false;

    /**
     * <p>Method instantiates a dialog without any appointment record. Used for new appointment records.</p>
     */
    public AddUpdateAppointment() {
        super(lrb.getString("create_new_appointment"));
        this.id.setPromptText("(Auto generated)");
        this.build();
    }

    /**
     * <p>Method is used to open the dialog with an existing record for editing.</p>
     * @param appointment The appointment object to be edited.
     */
    public AddUpdateAppointment(Appointment appointment) {
        super(lrb.getString("edit_appointment"));
        this.build();

        //FIXME - Insert logic to pull appointment record and populate the fields.
        this.currentAppointment = appointment;
        this.id.setInitialValue("" + this.currentAppointment.getId()+ "");
        this.location.setInitialValue(this.currentAppointment.getLocation());
        this.customer.setInitialValue(Customer.getObjById(this.currentAppointment.getCustomerID()));
        this.contact.setInitialValue(Contact.getObjById(this.currentAppointment.getContactID()));
        this.type.setInitialValue(this.currentAppointment.getType());
        this.start.setInitialValue(this.currentAppointment.getStart());
        this.end.setInitialValue(this.currentAppointment.getEnd());
        this.title.setInitialValue(this.currentAppointment.getTitle());
        this.description.setInitialValue(this.currentAppointment.getDescription());
    }

    /**
     * This method is used to format components and add them to the view.
     */
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
        //Before attempting to save data we must make sure that changes have been made to the data.

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
            System.out.print(e.getStackTrace());
        }
    };

    /**
     * This method launches a new dialog confirming the user wants to drop changes and close the appointment dialog.
     * @param event
     */
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
