package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public SimpleObjectProperty<Customer> customer;
    public SimpleObjectProperty<Contact> contact;
    public SimpleObjectProperty<User> user;
    public SimpleIntegerProperty id;
    public SimpleStringProperty title, description, location, type;
    public SimpleObjectProperty<ZonedDateTime> start, end;
    public static ObservableList<Appointment> allAppointments;

    public Appointment(int id, String title, String description, String location,
                       String type, ZonedDateTime start, ZonedDateTime end, Customer customer, User user, Contact contact) {
        this.id.set(id);
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.start.set(start);
        this.end.set(end);
        this.customer.set(customer);
        this.user.set(user);
        this.contact.set(contact);
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
        this.title.set(title);
        this.description.set(description);
        this.location.set(location);
        this.type.set(type);
        this.start.set(start);
        this.end.set(end);
        this.customer.set(customer);
        this.user.set(user);
        this.contact.set(contact);
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

    public static void loadData() {
        //FIXME - Need to load from Database
        Appointment[] appointments = new Appointment[] {
            new Appointment(0,"Appointment1","Description1", "Location1","Type1",
                    ZonedDateTime.of(LocalDate.of(2022, 1,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(),Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(1,"Appointment2","Description2", "Location2","Type2",
                    ZonedDateTime.of(LocalDate.of(2022,2,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(2,"Appointment3","Description3", "Location3","Type3",
                    ZonedDateTime.of(LocalDate.of(2022,3,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(3,"Appointment4","Description4", "Location4","Type4",
                    ZonedDateTime.of(LocalDate.of(2022,4,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(4,"Appointment5","Description5", "Location5","Type5",
                    ZonedDateTime.of(LocalDate.of(2022,5,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(5,"Appointment6","Description6", "Location6","Type6",
                    ZonedDateTime.of(LocalDate.of(2022,6,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(6,"Appointment7","Description7", "Location7","Type7",
                    ZonedDateTime.of(LocalDate.of(2022,6,8), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(7,"Appointment8","Description8", "Location8","Type8",
                    ZonedDateTime.of(LocalDate.of(2022,6,15), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(8,"Appointment9","Description9", "Location9","Type9",
                    ZonedDateTime.of(LocalDate.of(2022,6,22), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1)),
            new Appointment(9,"Appointment10","Description10", "Location10","Type10",
                    ZonedDateTime.of(LocalDate.of(2022,7,1), LocalTime.of(1,30),
                            ZoneId.systemDefault()), ZonedDateTime.now(), Customer.getById(1),User.getById(1),Contact.getById(1))
        };
        allAppointments = FXCollections.observableList(new ArrayList<>(List.of(appointments)));
    }

    //Getters
    public final int getId() { return this.id.get(); }
    //FIXME - These need to be converted to local time zone.
    public final Contact getContact() { return this.contact.get(); }
    public final Customer getCustomer() { return this.customer.get(); }
    public final User getUser() { return this.user.get(); }
    public final String getStartFormatted() {
        return this.start.get().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Main.locale));
    }
    public final String getEndFormatted() {
        return this.end.get().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Main.locale));
    }
}
