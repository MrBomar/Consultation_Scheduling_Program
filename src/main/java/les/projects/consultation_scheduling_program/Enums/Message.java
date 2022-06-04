package les.projects.consultation_scheduling_program.Enums;

public enum Message {
    AppointmentCanceled(
            "Appointment Canceled",
            "FIXME - Should display appointment type and record ID."
    ),
    HoursViolation(
            "Invalid Appointment Hours",
            "This appointment cannot be scheduled during the time specified." +
                    "\n\n" +
                    "All appointments must be scheduled between 08:00 a.m. and 10:00 p.m. EST"
    ),
    ConflictingAppointment(
            "Conflicting Appointment",
            "This appointment cannot be scheduled during the time specified." +
                    "\n\n" +
                    "This customer already has an appointment scheduled during the specified hours."
    ),
    LoginFail (
            "Login Failed",
            "The username and password combination you provided does not match our records."),
    NoUpcomingAppointment(
            "No Upcoming Appointments",
            "You have no upcoming appointments within the next 15 minutes."),
    CustomerDeleted(
            "Customer Deleted",
            "FIXME - Should display customer name."
    ),
    ConfirmAppointmentCancellation(
            "Cancel Selected Appointment?",
            "Are you sure you want to cancel this appointment?"
    ),
    ConfirmCustomerDelete(
            "Delete Customer Records?",
            "Deleting this customer will also delete all of their records.\n Are you sure you want to cancel?"
    ),
    ConfirmLogout(
            "Confirm Logout",
            "Are you sure you want to log out?"
    ),
    ConfirmDropChanges(
            "Drop Changes?",
            "Closing this form will drop all changes you have made. \n\n Are you sure you want to close?"
    );

    public final String title, message;

    Message(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
