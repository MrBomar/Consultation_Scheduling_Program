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
            mrb.getString("database_error_title"),
            mrb.getString("database_error_message")
    ),

    /**
     * The message displayed if the specified data could not be found in the database.
     */
    DataNotFound(
            mrb.getString("data_not_found_title"),
            mrb.getString("data_not_found_message")
    ),

    /**
     * The message displayed if the data in the database can't be uploaded.
     */
    DataNotLoaded(
            mrb.getString("data_not_loaded_title"),
            mrb.getString("data_not_loaded_message")
    ),

    /**
     * Message informs the user that either the form is incomplete, or the information entered is invalid.
     */
    InvalidInput(
            mrb.getString("invalid_input_title"),
            mrb.getString("invalid_input_message")
    ),

    /**
     * Message displayed when a required field is blank.
     */
    InvalidInputEntered(
            mrb.getString("invalid_input_entered_title"),
            mrb.getString("invalid_input_entered_message")
    ),

    /**
     * Message displayed when a required field has no selection.
     */
    InvalidInputSelected(
            mrb.getString("invalid_input_selected_title"),
            mrb.getString("invalid_input_selected_message")
    ),

    /**
     * Message displayed when the time selected cannot be converted to a DateTime object.
     */
    InvalidInputTime(
            mrb.getString("invalid_input_time_title"),
            mrb.getString("invalid_input_time_message")
    ),

    /**
     * Message informing the user that the appointment they are attempting to schedule falls outside the acceptable
     * range of appointment hours.
     */
    InvalidTimeRange(
            mrb.getString("invalid_time_range_title"),
            mrb.getString("invalid_time_range_message")
    ),

    /**
     * Message informing the user that the appointment start time must take place before the end time.
     */
    InvalidTimeOrder(
            mrb.getString("invalid_time_order_title"),
            mrb.getString("invalid_time_order_message")
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
            mrb.getString("nothing_changed_title"),
            mrb.getString("nothing_changed_message")
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
            mrb.getString("record_not_added_title"),
            mrb.getString("record_not_added_description")
    ),

    /**
     * Message displayed when a record could not be deleted.
     */
    RecordNotDeleted(
            mrb.getString("record_not_deleted_title"),
            mrb.getString("record_not_deleted_message")
    ),

    /**
     * Message displayed when a record could not be updated.
     */
    RecordNotUpdated(
            mrb.getString("record_not_updated_title"),
            mrb.getString("record_not_updated_message")
    ),

    /**
     * Message displayed confirming the record has been saved.
     */
    RecordSaved(
            mrb.getString("record_saved_title"),
            mrb.getString("record_saved_message")
    ),

    /**
     * Message displayed when a record could not be uploaded from the database.
     */
    ReportNotLoaded(
            mrb.getString("report_not_loaded_title"),
            mrb.getString("report_not_loaded_message")
    ),

    /**
     * Message displayed after login if the user has upcoming appointment.
     */
    UpcomingAppointment(
            mrb.getString("upcoming_appointment_title"),
            mrb.getString("upcoming_appointment_message")
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
