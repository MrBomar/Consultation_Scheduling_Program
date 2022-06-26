package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.ZonedDateTime;

public final class ReportTwoItem {
    private final SimpleObjectProperty<Customer> customer;
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleStringProperty type;
    private final SimpleStringProperty title;
    private final SimpleObjectProperty<ZonedDateTime> start;
    private final SimpleObjectProperty<ZonedDateTime> end;

    public ReportTwoItem(Customer customer, Contact contact, String type,
                         String title, ZonedDateTime start, ZonedDateTime end) {
        this.customer = new SimpleObjectProperty<>(customer);
        this.contact = new SimpleObjectProperty<>(contact);
        this.type = new SimpleStringProperty(type);
        this.title = new SimpleStringProperty(title);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
    }

    public final Customer getCustomer() { return this.customer.get(); }
    public final Contact getContact() { return this.contact.get(); }
    public final String getType() { return this.type.get(); }
    public final String getTitle() { return this.title.get(); }
    public final ZonedDateTime getStart() { return this.start.get(); }
    public final ZonedDateTime getEnd() { return this.end.get(); }
}
