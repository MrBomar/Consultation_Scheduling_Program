package les.projects.consultation_scheduling_program.Enums;

import static les.projects.consultation_scheduling_program.Main.mrb;

public enum Message {
    AppointmentCanceled(
            mrb.getString("appointment_canceled_title"),
            mrb.getString("appointment_canceled_message")
    ),
    ConfirmAppointmentCancellation(
            mrb.getString("confirm_appointment_cancellation_title"),
            mrb.getString("confirm_appointment_cancellation_message")
    ),
    ConfirmCustomerDelete(
            mrb.getString("confirm_customer_delete_title"),
            mrb.getString("confirm_customer_delete_message")
    ),
    ConfirmDropChanges(
            mrb.getString("confirm_drop_changes_title"),
            mrb.getString("confirm_drop_changes_message")
    ),
    ConfirmLogout(
            mrb.getString("confirm_logout_title"),
            mrb.getString("confirm_logout_message")
    ),
    ConflictingAppointment(
            mrb.getString("conflicting_appointment_title"),
            mrb.getString("conflicting_appointment_message")
    ),
    CustomerDeleted(
            mrb.getString("customer_deleted_title"),
            mrb.getString("customer_deleted_message")
    ),
    HoursViolation(
            mrb.getString("hours_violation_title"),
            mrb.getString("hours_violation_message")
    ),
    InvalidInput(
            "Invalid Input",
            "This for contains invalid information or there are required fields that have not been filled." +
                    "Please verify the data entered."
    ),
    LoginFail (
            mrb.getString("login_failed_title"),
            mrb.getString("login_failed_message")
    ),
    MissingContactRecord(
            "No Contact Record",
            "The record for the specified customer could not be found."
    ),
    MissingCountryRecord(
            "No Country Record",
            "The record for the specified country could not be found."
    ),
    MissingCustomerRecord(
            "No Customer Record",
            "The record for the specified customer could not be found."
    ),
    MissingDivisionRecord(
            "Do Division Record",
            "The record for the specified division could not be found."
    ),
    MissingHourRecord(
            "No Hour Record",
            "The record for the specified hour could not be found."
    ),
    MissingMinuteRecord(
            "No Minute Record",
            "The record for the specified minute could not be found."
    ),
    NoAppointmentSelected (
            mrb.getString("no_appointment_selected_title"),
            mrb.getString("no_appointment_selected_message")
    ),
    NoSelectedCustomer (
            mrb.getString("no_selected_customer_title"),
            mrb.getString("no_selected_customer_message")
    ),
    NothingChanged(
            "Nothing Changes",
            "You haven't made any changes to the data, so nothing needs to be saved."
    ),
    NoUpcomingAppointment(
            mrb.getString("no_upcoming_appointment_title"),
            mrb.getString("no_upcoming_appointment_message")
    ),
    NoValueSelected(
            mrb.getString("no_value_selected_title"),
            mrb.getString("no_value_selected_message")
    ),
    ProgrammingError_MissingClass(
            mrb.getString("prog_error_missing_class_title"),
            mrb.getString("prog_error_missing_class_message")
    ),
    RecordSaved(
            "Record Saved",
            "Your changes have been saved."
    )
    ;

    public final String title, message;

    Message(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
