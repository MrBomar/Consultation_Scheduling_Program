package les.projects.consultation_scheduling_program.Enums;

import static les.projects.consultation_scheduling_program.Main.mrb;

/**
 * This enum list contains all the messages displayed to the user in dialogs.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public enum Message {
    /**
     * Message displayed confirming the appointment has been cancelled.
     */
    AppointmentCanceled(
            mrb.getString("appointment_canceled_title"),
            mrb.getString("appointment_canceled_message")
    ),

    /**
     * Message requesting the user confirm the deletion of the selected appointment.
     */
    ConfirmAppointmentCancellation(
            mrb.getString("confirm_appointment_cancellation_title"),
            mrb.getString("confirm_appointment_cancellation_message")
    ),

    /**
     * Message requesting the user confirm the deletion of the selected customer.
     */
    ConfirmCustomerDelete(
            mrb.getString("confirm_customer_delete_title"),
            mrb.getString("confirm_customer_delete_message")
    ),

    /**
     * Message requesting the user confirm they wat to drop the changes they have made.
     */
    ConfirmDropChanges(
            mrb.getString("confirm_drop_changes_title"),
            mrb.getString("confirm_drop_changes_message")
    ),

    /**
     * Message requesting the user confirm the logout action.
     */
    ConfirmLogout(
            mrb.getString("confirm_logout_title"),
            mrb.getString("confirm_logout_message")
    ),

    /**
     * Message informing the user that conflicting appointments exist.
     */
    ConflictingAppointment(
            mrb.getString("conflicting_appointment_title"),
            mrb.getString("conflicting_appointment_message")
    ),

    /**
     * Message displayed to the user if the query could not be executed.
     */
    DatabaseError(
            "Database Error",
            "The query could not be executed."
    ),

    /**
     * The message displayed if the specified data could not be found in the database.
     */
    DataNotFound(
            "Data Not Found",
            "No {} found in the database."
    ),

    /**
     * The message displayed if the data in the database can't be uploaded.
     */
    DataNotLoaded(
            "Data Could Not Be Loaded",
            "The {} could not be loaded from the database."
    ),

    /**
     * Message informs the user that either the form is incomplete, or the information entered is invalid.
     */
    InvalidInput(
            "Invalid Input",
            "This form contains invalid information or there are required fields that have not been filled." +
                    "Please verify the data entered."
    ),

    /**
     * Message displayed when a required field is blank.
     */
    InvalidInputEntered(
            "Invalid Input",
            "You must type a {}, any {} will do."
    ),

    /**
     * Message displayed when a required field has no selection.
     */
    InvalidInputSelected(
            "Invalid Input",
            "No {} is selected. Please select a {} before saving."
    ),

    /**
     * Message displayed when the time selected cannot be converted to a DateTime object.
     */
    InvalidInputTime(
            "Invalid Input",
            "The '{}' time and date specified is invalid. Please review your entry."
    ),

    /**
     * Message informing the user that the appointment they are attempting to schedule falls outside the acceptable
     * range of appointment hours.
     */
    InvalidTimeRange(
            "Invalid Time Range",
            "All appointments must be scheduled between 08:00 am and 10:00 pm Eastern Standard Time."
    ),

    /**
     * Message informing the user that the appointment start time must take place before the end time.
     */
    InvalidTimeOrder(
            "Invalid Time Order",
            "The appointment end time and date must be set after the appointment start time and date."
    ),

    /**
     * Message informing the user that the login process failed.
     */
    LoginFail (
            mrb.getString("login_failed_title"),
            mrb.getString("login_failed_message")
    ),

    /**
     * Message displayed if the user attempts to perform an action on an appointment but no appointment is selected.
     */
    NoAppointmentSelected (
            mrb.getString("no_appointment_selected_title"),
            mrb.getString("no_appointment_selected_message")
    ),

    /**
     * Message displayed if the user attempts to perform an action on a customer but no customer is selected.
     */
    NoSelectedCustomer (
            mrb.getString("no_selected_customer_title"),
            mrb.getString("no_selected_customer_message")
    ),

    /**
     * Message displayed if the customer attempts to save a record that contains no changes.
     */
    NothingChanged(
            "Nothing Changes",
            "You haven't made any changes to the data, so nothing needs to be saved."
    ),

    /**
     * Message displayed after login if the user has no upcoming appointments.
     */
    NoUpcomingAppointment(
            mrb.getString("no_upcoming_appointment_title"),
            mrb.getString("no_upcoming_appointment_message")
    ),

    /**
     * Message displayed when a record could not be added.
     */
    RecordNotAdded(
            "Could Not Add Record",
            "The {} could not be added to the database."
    ),

    /**
     * Message displayed when a record could not be deleted.
     */
    RecordNotDeleted(
            "Could Not Delete Record",
            "The {} could not be deleted from the database."
    ),

    /**
     * Message displayed when a record could not be updated.
     */
    RecordNotUpdated(
            "Record Could Not Be Updated",
            "The {} could not be updated in the database."
    ),

    /**
     * Message displayed confirming the record has been saved.
     */
    RecordSaved(
            "Record Saved",
            "Your changes have been saved."
    ),

    /**
     * Message displayed when a record could not be uploaded from the database.
     */
    ReportNotLoaded(
            "Report Cannot Be Loaded",
            "Cannot load the {} report."
    ),

    /**
     * Message displayed after login if the user has upcoming appointment.
     */
    UpcomingAppointment(
            "Upcoming Appointment",
            "The following appointment is starting within 15 minutes. \n \nAppointment ID: {}" +
                    "\nDate: {}\nTime: {}"
    )
    ;

    public final String title, message;

    /**
     * Instantiates a Message enum value.
     * @param title The title of the message.
     * @param message The content of the message.
     */
    Message(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
