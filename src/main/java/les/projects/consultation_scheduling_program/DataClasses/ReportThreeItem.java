package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.ZonedDateTime;

/**
 * This class manages the data objects related to the second report. These objects are extracts from the
 * Appointments table.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public final class ReportThreeItem {
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleIntegerProperty appointmentId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleObjectProperty<ZonedDateTime> start;
    private final SimpleObjectProperty<ZonedDateTime> end;
    private final SimpleIntegerProperty customerId;

    /**
     * This constructor instantiates a ReportTwoItem.
     * @param contact The Contact object of the appointment.
     * @param appointmentId The record ID of the appointment.
     * @param title The title of the appointment.
     * @param type The type of the appointment.
     * @param description The description of the appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param customerId The record ID of the customer rated to the appointment.
     */
    public ReportThreeItem(Contact contact, Integer appointmentId, String title, String type, String description,
                           ZonedDateTime start, ZonedDateTime end, Integer customerId) {
        this.contact = new SimpleObjectProperty<>(contact);
        this.appointmentId = new SimpleIntegerProperty(appointmentId);
        this.title = new SimpleStringProperty(title);
        this.type = new SimpleStringProperty(type);
        this.description = new SimpleStringProperty(description);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.customerId = new SimpleIntegerProperty(customerId);
    }

    /**
     * This method gets the Contact object related to the appointment.
     * @return The Contact object related to the appointment.
     */
    public Contact getContact() { return this.contact.get(); }

    /**
     * This method gets the record ID of the appointment.
     * @return The record ID of the appointment.
     */
    public Integer getAppointmentId() { return this.appointmentId.get(); }

    /**
     * This method gets the title of the appointment.
     * @return The title of the appointment.
     */
    public String getTitle() { return this.title.get(); }

    /**
     * This method gets the type of the appointment.
     * @return The type of the appointment.
     */
    public String getType() { return this.type.get(); }

    /**
     * This method gets the description of the appointment.
     * @return The description of the appointment.
     */
    public String getDescription() { return this.description.get(); }

    /**
     * This method gets the start date and time of the appointment.
     * @return The start date and time of the appointment.
     */
    public ZonedDateTime getStart() { return this.start.get(); }

    /**
     * This method gets the end date and time of the appointment.
     * @return The end date and time of the appointment.
     */
    public ZonedDateTime getEnd() { return this.end.get(); }

    /**
     * This method gets the record ID of the customer related to the appointment.
     * @return The record ID of the customer related to the appointment.
     */
    public Integer getCustomerId() { return this.customerId.get(); }
}
