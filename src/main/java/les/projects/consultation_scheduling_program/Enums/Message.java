package les.projects.consultation_scheduling_program.Enums;

import static les.projects.consultation_scheduling_program.Main.mrb;

/**
 * This enum list contains all of the messages displayed to the user in dialogs.
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
     * Message confirming that the customer has been deleted.
     */
    CustomerDeleted(
            mrb.getString("customer_deleted_title"),
            mrb.getString("customer_deleted_message")
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
     * Message displayed if the specified contact cannot be found.
     */
    MissingContactRecord(
            "No Contact Record",
            "The record for the specified customer could not be found."
    ),

    /**
     * Message displayed if the specified country cannot be found.
     */
    MissingCountryRecord(
            "No Country Record",
            "The record for the specified country could not be found."
    ),

    /**
     * Message displayed of the specified customer cannot be found.
     */
    MissingCustomerRecord(
            "No Customer Record",
            "The record for the specified customer could not be found."
    ),

    /**
     * Message displayed if the specified division cannot be found.
     */
    MissingDivisionRecord(
            "Do Division Record",
            "The record for the specified division could not be found."
    ),

    /**
     * Message displayed if the specified hour is not in the Hour enum.
     */
    MissingHourRecord(
            "No Hour Record",
            "The record for the specified hour could not be found."
    ),

    /**
     * Message displayed if the specified minute is not in the Minute enum.
     */
    MissingMinuteRecord(
            "No Minute Record",
            "The record for the specified minute could not be found."
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
     * Message displayed if the user attempts to perform a record function without selecting a record.
     */
    NoValueSelected(
            mrb.getString("no_value_selected_title"),
            mrb.getString("no_value_selected_message")
    ),

    /**
     * Message displayed if a method cannot find the required class.
     */
    ProgrammingError_MissingClass(
            mrb.getString("prog_error_missing_class_title"),
            mrb.getString("prog_error_missing_class_message")
    ),

    /**
     * Message displayed confirming the record has been saved.
     */
    RecordSaved(
            "Record Saved",
            "Your changes have been saved."
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
