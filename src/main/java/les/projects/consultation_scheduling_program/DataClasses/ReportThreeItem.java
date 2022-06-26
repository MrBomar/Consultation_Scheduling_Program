package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.ZonedDateTime;

public final class ReportThreeItem {
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleIntegerProperty appointmentId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty type;
    private final SimpleStringProperty description;
    private final SimpleObjectProperty<ZonedDateTime> start;
    private final SimpleObjectProperty<ZonedDateTime> end;
    private final SimpleIntegerProperty customerId;

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

    public final Contact getContact() { return this.contact.get(); }
    public final Integer getAppointmentId() { return this.appointmentId.get(); }
    public final String getTitle() { return this.title.get(); }
    public final String getType() { return this.type.get(); }
    public final String getDescription() { return this.description.get(); }
    public final ZonedDateTime getStart() { return this.start.get(); }
    public final ZonedDateTime getEnd() { return this.end.get(); }
    public final Integer getCustomerId() { return this.customerId.get(); }
}
