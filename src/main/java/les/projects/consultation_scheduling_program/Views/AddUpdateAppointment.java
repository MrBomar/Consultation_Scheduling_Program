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
import les.projects.consultation_scheduling_program.Main;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Add/Update Appointment dialog.
 * @author Leslie C. Bomar 3rd
 */
public class AddUpdateAppointment extends DialogBase {
    //Object Data
    private Appointment currentAppointment;

    //Fields
    private final ComboBox_Contact contact = new ComboBox_Contact(lrb.getString("contact"), Contact.getAllContacts(), true);
    private final ComboBox_Customer customer = new ComboBox_Customer(lrb.getString("customer"), Customer.getAllCustomers(), true);
    private final TextAreaLabeled description = new TextAreaLabeled(lrb.getString("description"));
    private final DateTimePicker end = new DateTimePicker("End");
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("appointment_id"), false, true);
    private final TextFieldLabeled location = new TextFieldLabeled(lrb.getString("location"), true, false);
    private final DateTimePicker start = new DateTimePicker("Start");
    private final TextFieldLabeled title = new TextFieldLabeled(lrb.getString("title"), true, false);
    private final TextFieldLabeled type = new TextFieldLabeled(lrb.getString("type"), false, false);

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

    /**
     * This event handler saves the data from the form into the Appointment object.
     * The Appointment object will handle the database updates.
     */
    private final EventHandler<MouseEvent> saveClick = event -> {
        //Before attempting to save data we must make sure that changes have been made to the data.
        if(this.changesHaveBeenMade()) {
            //We also need to verify that no required fields are blank.
            if(this.requiredFieldsFilled()) {
                if (currentAppointment == null) {
                    //There is no current appointment, so we need to add a new one.
                    Appointment.add(
                            this.title.getInput(),
                            this.description.getInput(),
                            this.location.getInput(),
                            this.type.getInput(),
                            this.start.getEntry(),
                            this.end.getEntry(),
                            this.customer.getSelectedItem(),
                            this.contact.getSelectedItem()
                    );
                } else {
                    //We have an appointment to update.
                    this.currentAppointment.update(
                            this.title.getInput(),
                            this.description.getInput(),
                            this.location.getInput(),
                            this.type.getInput(),
                            this.start.getEntry(),
                            this.end.getEntry(),
                            this.customer.getSelectedItem(),
                            Main.currentUser,
                            this.contact.getSelectedItem()
                    );
                }
                DialogMessage dialog = new DialogMessage(Message.RecordSaved);
                dialog.showAndWait();
                this.close();
            } else {
                //We have fields that require filling out.
                DialogMessage dialog = new DialogMessage(Message.InvalidInput);
                dialog.showAndWait();
            }
        } else {
            //No changes were detects
            DialogMessage dialog = new DialogMessage(Message.NothingChanged);
            dialog.showAndWait();
        }
    };

    /**
     * This method launches a new dialog confirming the user wants to drop changes and close the appointment dialog.
     * @param event The event is only used as a trigger.
     */
    private void confirmCancel(Event event) {
        if(this.changesHaveBeenMade()) {
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
            dialog.showAndWait();
            if(dialog.getResult()) {
                this.close();
            } else {
                event.consume();
            }
        } else {
            this.close();
        }
    }

    /**
     * This method checks all input elements on the form to see if their data changed from their initial state.
     * @return Returns true if any of the data have been changed from initial state.
     */
    private boolean changesHaveBeenMade() {
        if(contact.isChanged()) return true;
        if(customer.isChanged()) return true;
        if(description.isChanged()) return true;
        if(end.validEntry()) return true;
        if(location.isChanged()) return true;
        if(start.validEntry()) return true;
        if(title.isChanged()) return true;
        if(type.isChanged()) return true;
        return false;
    }

    /**
     * This method checks all input elements to ensure that they have valid data.
     * @return Returns true if all input elements contain valid data.
     */
    private boolean requiredFieldsFilled() {
        if(!contact.itemIsSelected()) {
            //Contact is not selected
            DialogMessage dialog = new DialogMessage("Invalid Input", "No contact is selected. Please select a contact before saving.");
            dialog.showAndWait();
            return false;
        } else if (!customer.itemIsSelected()) {
            //Customer is not selected
            DialogMessage dialog = new DialogMessage("Invalid Input", "No customer is selected. Please select a customer before saving.");
            dialog.showAndWait();
            return false;
        } else if (!end.validEntry()) {
            //End time is not valid
            DialogMessage dialog = new DialogMessage("Invalid Input", "The 'End' time and date specified is invalid. Please review your entry.");
            dialog.showAndWait();
            return false;
        } else if (!location.isNotBlank()) {
            //Location is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "You must type a location, any location will do.");
            dialog.showAndWait();
            return false;
        } else if (!start.validEntry()) {
            //Start time is not valid
            DialogMessage dialog = new DialogMessage("Invalid Input", "The 'Start' time and date specified is invalid. Please review your entry.");
            dialog.showAndWait();
            return false;
        } else if (!title.isNotBlank()) {
            //Title is blank
            DialogMessage dialog = new DialogMessage("Invalid Input", "You must type a title, any title will do.");
            dialog.showAndWait();
            return false;
        } else {
            return true;
        }
    }
}
