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
    LoginFail (
            mrb.getString("login_failed_title"),
            mrb.getString("login_failed_message")
    ),
    NoSelectedCustomer (
            mrb.getString("no_selected_customer_title"),
            mrb.getString("no_selected_customer_message")
    ),
    NoUpcomingAppointment(
            mrb.getString("no_upcoming_appointment_title"),
            mrb.getString("no_upcoming_appointment_message")
    );

    public final String title, message;

    Message(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
