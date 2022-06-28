package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.time.ZonedDateTime;

/**
 * This class manages the data objects related to the second report. These object are extracts from the
 * Appointments table.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public final class ReportTwoItem {
    private final SimpleObjectProperty<Customer> customer;
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleStringProperty type;
    private final SimpleStringProperty title;
    private final SimpleObjectProperty<ZonedDateTime> start;
    private final SimpleObjectProperty<ZonedDateTime> end;

    /**
     * This constructor instantiates a ReportTwoItem.
     * @param customer The Customer object related to the appointment.
     * @param contact The Contact object related to the appointment.
     * @param type The type of the appointment.
     * @param title The title of the appointment.
     * @param start The start time and date of the appointment.
     * @param end The end time and date of the appointment.
     */
    public ReportTwoItem(Customer customer, Contact contact, String type,
                         String title, ZonedDateTime start, ZonedDateTime end) {
        this.customer = new SimpleObjectProperty<>(customer);
        this.contact = new SimpleObjectProperty<>(contact);
        this.type = new SimpleStringProperty(type);
        this.title = new SimpleStringProperty(title);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
    }

    /**
     * This method gets the Customer object related to the appointment.
     * @return The Customer object related to the appointment.
     */
    public final Customer getCustomer() { return this.customer.get(); }

    /**
     * This method gets the Contact object related to the appointment.
     * @return The Contact object related to the appointment.
     */
    public final Contact getContact() { return this.contact.get(); }

    /**
     * This method gets the type of the appointment.
     * @return The type of the appointment.
     */
    public final String getType() { return this.type.get(); }

    /**
     * This method gets the title of the appointment.
     * @return The title of the appointment.
     */
    public final String getTitle() { return this.title.get(); }

    /**
     * This method gets the start date and time of the appointment.
     * @return The start date and time of the appointment.
     */
    public final ZonedDateTime getStart() { return this.start.get(); }

    /**
     * This method get the end date and time of the appointment.
     * @return The end date and time of the appointment.
     */
    public final ZonedDateTime getEnd() { return this.end.get(); }
}
