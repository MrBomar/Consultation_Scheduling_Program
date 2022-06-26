package les.projects.consultation_scheduling_program.Views;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import les.projects.consultation_scheduling_program.Components.*;
import les.projects.consultation_scheduling_program.DataClasses.Appointment;
import les.projects.consultation_scheduling_program.DataClasses.Contact;
import les.projects.consultation_scheduling_program.DataClasses.Customer;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Main;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class renders the Add/Update Appointment dialog.
 * @author Leslie C. Bomar 3rd
 */
public class AddUpdateAppointment extends DialogBase {
    //Object Data
    private Appointment currentAppointment;

    //Fields
    private final ComboBoxStyled<Contact> contact = new ComboBoxStyled<>(Contact.allContacts, lrb.getString("contact"));
    private final ComboBoxStyled<Customer> customer = new ComboBoxStyled<>(Customer.allCustomers, lrb.getString("customer"));
    private final TextAreaLabeled description = new TextAreaLabeled(lrb.getString("description"));
    private final DateTimePicker end = new DateTimePicker("End");
    private final TextFieldLabeled id = new TextFieldLabeled(lrb.getString("appointment_id"), false, true);
    private final TextFieldLabeled location = new TextFieldLabeled(lrb.getString("location"), true, false);
    private final DateTimePicker start = new DateTimePicker("Start");
    private final TextFieldLabeled title = new TextFieldLabeled(lrb.getString("title"), true, false);
    private final TextFieldLabeled type = new TextFieldLabeled(lrb.getString("type"), false, false);

    //Reference Only
    private final TableView<Appointment> appointmentsTable;

    /**
     * <p>Method instantiates a dialog without any appointment record. Used for new appointment records.</p>
     */
    public AddUpdateAppointment(TableView<Appointment> appointmentsTable) {
        super(lrb.getString("create_new_appointment"));
        this.id.setPromptText("(Auto generated)");
        this.appointmentsTable = appointmentsTable;
        this.build();
    }

    /**
     * <p>Method is used to open the dialog with an existing record for editing.</p>
     *
     * @param appointment The appointment object to be edited.
     */
    public AddUpdateAppointment(TableView<Appointment> appointmentsTable, Appointment appointment) {
        super(lrb.getString("edit_appointment"));
        this.appointmentsTable = appointmentsTable;
        this.build();

        this.currentAppointment = appointment;
        this.id.setInitialValue("" + this.currentAppointment.getId() + "");
        this.location.setInitialValue(this.currentAppointment.getLocation());
        this.customer.setInitialValue(this.currentAppointment.getCustomer());
        this.contact.setInitialValue(this.currentAppointment.getContact());
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
        BorderPaneStyled customerPane = new BorderPaneStyled(lrb.getString("customer"), true);
        customerPane.setRight(this.customer);
        BorderPaneStyled contactPane = new BorderPaneStyled(lrb.getString("contact"), true);
        contactPane.setRight(this.contact);

        VBox center = new VBox();
        center.setPadding(new Insets(0, 20, 30, 20));
        center.getChildren().addAll(id, location, customerPane, contactPane, type, start, end, title, description);
        this.center.getChildren().add(center);

        //Add Buttons
        ButtonStandard save = new ButtonStandard(lrb.getString("save"));
        save.setOnMouseClicked(this::saveClick);
        ButtonStandard cancel = new ButtonStandard(lrb.getString("cancel"));
        cancel.setOnMouseClicked(this::confirmCancel);
        this.bottom.getChildren().addAll(save, new ButtonGap(), cancel);

        this.customer.setWidth(200);
        this.contact.setWidth(200);
        this.setOnCloseRequest(this::confirmCancel);
    }

    /**
     * This event handler saves the data from the form into the Appointment object.
     * The Appointment object will handle the database updates.
     */
    private void saveClick(Event e) {
        if(!this.changesHaveBeenMade()) {
            //No changes were detects
            DialogMessage dialog = new DialogMessage(Message.NothingChanged);
            dialog.showAndWait();
            return;
        }

        if(!this.requiredFieldsFilled()) {
            //We have fields that require filling out.
            DialogMessage dialog = new DialogMessage(Message.InvalidInput);
            dialog.showAndWait();
        }

        if(!this.validTimeRange()) return;

        if(!this.noConflictingAppointments()) return;

        if (currentAppointment == null) {
            //There is no current appointment, so we need to add a new one.
            Appointment.add(
                    this.title.getInput(),
                    this.description.getInput(),
                    this.location.getInput(),
                    this.type.getInput(),
                    this.start.getEntry(),
                    this.end.getEntry(),
                    this.customer.getValue(),
                    Main.currentUser,
                    this.contact.getValue()
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
                    this.customer.getValue(),
                    Main.currentUser,
                    this.contact.getValue()
            );
        }
        DialogMessage dialog = new DialogMessage(Message.RecordSaved);
        dialog.showAndWait();
        Appointment.loadData();
        this.close();
    }

    /**
     * This method launches a new dialog confirming the user wants to drop changes and close the appointment dialog.
     *
     * @param event The event is only used as a trigger.
     */
    private void confirmCancel(Event event) {
        if (this.changesHaveBeenMade()) {
            DialogConfirmation dialog = new DialogConfirmation(Message.ConfirmDropChanges);
            dialog.showAndWait();
            if (dialog.getResult()) {
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
     *
     * @return Returns true if any of the data have been changed from initial state.
     */
    private boolean changesHaveBeenMade() {
        return contact.isChanged() || customer.isChanged() || description.isChanged() || end.isChanged() ||
                location.isChanged() || start.isChanged() || title.isChanged() || type.isChanged();
    }

    /**
     * This method checks all input elements to ensure that they have valid data.
     *
     * @return Returns true if all input elements contain valid data.
     */
    private boolean requiredFieldsFilled() {
        if (this.contact.getSelectionModel().isEmpty()) {
            //Contact is not selected
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "No contact is selected. Please select a contact before saving.");
            dialog.showAndWait();
            return false;
        } else if (this.customer.getSelectionModel().isEmpty()) {
            //Customer is not selected
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "No customer is selected. Please select a customer before saving.");
            dialog.showAndWait();
            return false;
        } else if (!this.end.validEntry()) {
            //End time is not valid
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "The 'End' time and date specified is invalid. Please review your entry.");
            dialog.showAndWait();
            return false;
        } else if (!this.location.isNotBlank()) {
            //Location is blank
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "You must type a location, any location will do.");
            dialog.showAndWait();
            return false;
        } else if (!this.start.validEntry()) {
            //Start time is not valid
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "The 'Start' time and date specified is invalid. Please review your entry.");
            dialog.showAndWait();
            return false;
        } else if (!this.title.isNotBlank()) {
            //Title is blank
            DialogMessage dialog = new DialogMessage("Invalid Input",
                    "You must type a title, any title will do.");
            dialog.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    /**
     * This method verifies the date and time values that have been entered in the DateTimePicker are valid for business purposes.
     *
     * @return True if the dates and times entered are valid, false otherwise.
     */
    public boolean validTimeRange() {
        if (this.start.validEntry() && this.end.validEntry()) {
            ZonedDateTime startZDT = this.start.getEntry().withZoneSameInstant(ZoneId.of("America/New_York"));
            ZonedDateTime endZDT = this.end.getEntry().withZoneSameInstant(ZoneId.of("America/New_York"));

            LocalTime startLT = startZDT.toLocalTime();
            LocalTime endLT = endZDT.toLocalTime();
            LocalTime rangeStart = LocalTime.of(8, 00);
            LocalTime rangeEnd = LocalTime.of(22, 00);

            if (startLT.isBefore(rangeStart) || startLT.isAfter(rangeEnd) || endLT.isBefore(rangeStart) || endLT.isAfter(rangeEnd)) {
                //The times are not during the approved hours of operation.
                DialogMessage dialog = new DialogMessage(Message.InvalidTimeRange);
                dialog.showAndWait();
                return false;
            }

            if (startZDT.isAfter(endZDT)) {
                //The appointment end is before the appointment start.
                DialogMessage dialog = new DialogMessage(Message.InvalidTimeOrder);
                dialog.showAndWait();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method pulls all appointment for the selected customer and verifies that the current time and date provided do
     * not occur within and existing appointment's time frame.
     *
     * @return True if there are no previously scheduled appointment during the time specified.
     */
    public boolean noConflictingAppointments() {
        if (!this.start.validEntry() || !this.end.validEntry()) return false;
        boolean result = true;

        Appointment[] customerAppointments = Appointment.allAppointments.stream()
                .filter(i -> i.getCustomer().equals(this.customer.getValue())).toArray(Appointment[]::new);
        for (Appointment appt : customerAppointments) {
            if(isInWindow(this.start.getEntry(), appt.getStart(), appt.getEnd()) ||
            isInWindow(this.end.getEntry(), appt.getStart(),appt.getEnd()) ||
            overlapsWindow(appt.getStart(), appt.getEnd())
            ) {
                System.out.println("We have a problem.");
                //The input conflicts with existing appointment.
                DialogMessage dialog = new DialogMessage(Message.ConflictingAppointment);
                dialog.showAndWait();
                result = false;
                break;
            }
        }
        return result;
    }

    private boolean isInWindow(ZonedDateTime entry, ZonedDateTime start, ZonedDateTime end) {
        return (entry.isAfter(start) && entry.isBefore(end));
    }

    private boolean overlapsWindow(ZonedDateTime start, ZonedDateTime end) {
        boolean onOrBeforeStart = this.start.getEntry().isEqual(start) || this.start.getEntry().isBefore(start);
        boolean onOrAfterEnd = this.end.getEntry().isEqual(end) || this.end.getEntry().isAfter(end);
        return onOrBeforeStart && onOrAfterEnd;
    }
}

