package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Main;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Appointment {
    private final SimpleObjectProperty<Customer> customer;
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleObjectProperty<User> user;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty title, description, location, type;
    private final SimpleObjectProperty<ZonedDateTime> start, end;
    public static ObservableList<Appointment> allAppointments;

    public Appointment(int id, String title, String description, String location,
                       String type, ZonedDateTime start, ZonedDateTime end, Customer customer, User user, Contact contact) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.start = new SimpleObjectProperty<>(start);
        this.end = new SimpleObjectProperty<>(end);
        this.customer = new SimpleObjectProperty<>(customer);
        this.user = new SimpleObjectProperty<>(user);
        this.contact = new SimpleObjectProperty<>(contact);
    }

    @Override
    public String toString() {
        return this.title.get();
    }

    public static void add(String title, String description, String location,
                           String type, ZonedDateTime start, ZonedDateTime end, Customer customer, User user,Contact contact) {
        //FIXME - Update this method to save the Appointment to the database then refresh allAppointments
        //FIXME - ZonedDateTime needs to be converted to UTC before saving changes.

        int nextID = (allAppointments.size() > 0)?
                Collections.max(allAppointments, Comparator.comparing(Appointment::getId)).getId() + 1 :
                1;
        allAppointments.add(
                new Appointment(nextID,title,description,location,type,start,end,customer,user,contact)
        );

    }

    public void update(String title, String description, String location, String type,
                          ZonedDateTime start, ZonedDateTime end, Customer customer, User user, Contact contact) {
        this.setTitle(title);
        this.setDescription(description);
        this.setLocation(location);
        this.setType(type);
        this.setStart(start);
        this.setEnd(end);
        this.setCustomer(customer);
        this.setUser(user);
        this.setContact(contact);
    }

    public final void delete() {
        //FIXME - Must delete from database and return confirmation.
        try {
            allAppointments.remove(this);
        } catch (Exception e) {
            DialogMessage dialog = new DialogMessage("Can't Delete", "Something went wrong and we couldn't delete this appointment.");
            dialog.showAndWait();
        }
    }

    public static ObservableList<Appointment> getAllAppointments() { return allAppointments;}

    public static void upcomingAppointments() {
        Appointment[] userAppointments = allAppointments.stream()
                .filter(a -> a.getUser().equals(Main.currentUser)).toArray(Appointment[]::new);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime fifteenMinutesFromNow = now.plusMinutes(15);
        boolean noAppointments = true;
        for(Appointment a: userAppointments) {
            if(a.getStart().isAfter(now) && a.getStart().isBefore(fifteenMinutesFromNow)) {
                //We have an upcoming appointment.
                String[] args = new String[]{ "" + a.getId(), DTC.getDate(a.getStart()).toString(), DTC.getTimeString(a.getStart()) };
                DialogMessage dialog = new DialogMessage(Message.UpcomingAppointment, args);
                dialog.showAndWait();
                noAppointments = false;
            }
        }

        if(noAppointments) {
            DialogMessage dialog = new DialogMessage(Message.NoUpcomingAppointment);
            dialog.showAndWait();
        }
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Appointment[] appointments = new Appointment[] {
            new Appointment(0,
                    "Appointment1",
                    "Description1",
                    "Location1",
                    "Type1",
                    ZonedDateTime.of(LocalDate.of(2022, 6,23), LocalTime.of(9,30), ZoneId.systemDefault()),
                    ZonedDateTime.of(LocalDate.of(2022, 6,23), LocalTime.of(10,30), ZoneId.systemDefault()),
                    Customer.getById(1),
                    User.getById(1),
                    Contact.getById(1)),
            new Appointment(1,
                    "Upcoming Appointment",
                    "Test to see if we can trigger an upcoming appointment event.",
                    "In your desk chair.",
                    "Any type.",
                    ZonedDateTime.of(LocalDate.of(2022, 6,23), LocalTime.of(18,15), ZoneId.systemDefault()),
                    ZonedDateTime.of(LocalDate.of(2022, 6,23), LocalTime.of(18,30), ZoneId.systemDefault()),
                    Customer.getById(1),
                    User.getById(1),
                    Contact.getById(1))
//            new Appointment(1,"Appointment2","Description2", "Location2","Type2",
//                    ZonedDateTime.of(LocalDate.of(2022,2,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(2,"Appointment3","Description3", "Location3","Type3",
//                    ZonedDateTime.of(LocalDate.of(2022,3,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(3,"Appointment4","Description4", "Location4","Type4",
//                    ZonedDateTime.of(LocalDate.of(2022,4,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(4,"Appointment5","Description5", "Location5","Type5",
//                    ZonedDateTime.of(LocalDate.of(2022,5,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(5,"Appointment6","Description6", "Location6","Type6",
//                    ZonedDateTime.of(LocalDate.of(2022,6,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(6,"Appointment7","Description7", "Location7","Type7",
//                    ZonedDateTime.of(LocalDate.of(2022,6,8), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(7,"Appointment8","Description8", "Location8","Type8",
//                    ZonedDateTime.of(LocalDate.of(2022,6,15), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(8,"Appointment9","Description9", "Location9","Type9",
//                    ZonedDateTime.of(LocalDate.of(2022,6,22), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
//            new Appointment(9,"Appointment10","Description10", "Location10","Type10",
//                    ZonedDateTime.of(LocalDate.of(2022,7,1), LocalTime.of(1,30),
//                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1))
        };
        allAppointments = FXCollections.observableList(new ArrayList<>(List.of(appointments)));
    }

    //Formatters
    public final String getStartFormatted() {
        return this.start.get().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Main.locale));
    }
    public final String getEndFormatted() {
        return this.end.get().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Main.locale));
    }

    //Getters
    public final Contact getContact() { return this.contact.get(); }
    public final Customer getCustomer() { return this.customer.get(); }
    public final String getDescription() { return this.description.get(); }
    public final ZonedDateTime getEnd() { return this.end.get(); }
    public final int getId() { return this.id.get(); }
    public final String getLocation() { return this.location.get(); }
    public final ZonedDateTime getStart() { return this.start.get(); }
    public final String getTitle() { return this.title.get(); }
    public final String getType() { return this.type.get(); }
    public final User getUser() { return this.user.get(); }

    //Property Getters
    public final ObjectProperty<Contact> getContactProperty() { return this.contact; }
    public final ObjectProperty<Customer> getCustomerProperty() { return this.customer; }
    public final Property<String> getDescriptionProperty() { return this.description; }
    public final ObjectProperty<ZonedDateTime> getEndProperty() { return this.end; }
    public final Property<Number> getIdProperty() {  return this.id; }
    public final Property<String> getLocationProperty() { return this.location; }
    public final ObjectProperty<ZonedDateTime> getStartProperty() { return this.start; }
    public final Property<String> getTitleProperty() { return this.title; }
    public final Property<String> getTypeProperty() { return this.type; }
    public final ObjectProperty<User> getUserProperty() { return this.user; }

    //Property Setters
    public final void setContact(Contact contact) { this.contact.set(contact); }
    public final void setCustomer(Customer customer) { this.customer.set(customer); }
    public final void setDescription(String description) { this.description.set(description); }
    public final void setEnd(ZonedDateTime end) { this.end.set(end); }
    public final void setId(int id) { this.id.set(id); }
    public final void setLocation(String location) { this.location.set(location); }
    public final void setStart(ZonedDateTime start) { this.start.set(start); }
    public final void setTitle(String title) { this.title.set(title); }
    public final void setType(String type) { this.type.set(type); }
    public final void setUser(User user) { this.user.set(user); }
}
