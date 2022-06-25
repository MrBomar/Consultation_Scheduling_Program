package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public static boolean add(String title, String description, String location,
                           String type, ZonedDateTime start, ZonedDateTime end,
                           Customer customer, User user,Contact contact) {
        try {
            //Get records
            Statement stmt = JDBC.connection.createStatement();
            String query = "SELECT * FROM appointments";
            ResultSet rs = stmt.executeQuery(query);

            //Insert new record
            rs.moveToInsertRow();
            rs.updateString("Title", title);
            rs.updateString("Description", description);
            rs.updateString("Location", location);
            rs.updateString("Type", type);
            rs.updateTimestamp("Start", DTC.zonedDateTimeToTimestamp(start));
            rs.updateTimestamp("End", DTC.zonedDateTimeToTimestamp(end));
            rs.updateTimestamp("Create_Date", DTC.currentTimestamp());
            rs.updateString("Created_By", Main.currentUser.getUserName());
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateInt("CustomerID", customer.getId());
            rs.updateInt("User", user.getId());
            rs.updateInt("Contact", contact.getID());

            //Save new record
            rs.insertRow();

            //Refresh observable list.
            loadData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Cannot Add Record", "The appointment could not be added to the database.");
            dialog.showAndWait();
            return false;
        }
    }

    public final boolean update(String title, String description, String location, String type,
                          ZonedDateTime start, ZonedDateTime end, Customer customer, User user, Contact contact) {
        try {
            //Pull current record.
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM appointments WHERE Appointment_ID = " + this.getId();
            ResultSet rs = stmt.executeQuery(qry);

            //Update the record fields
            rs.updateString("Title", title);
            rs.updateString("Description", description);
            rs.updateString("Location", location);
            rs.updateString("Type", type);
            rs.updateTimestamp("Start", DTC.zonedDateTimeToTimestamp(start));
            rs.updateTimestamp("End", DTC.zonedDateTimeToTimestamp(end));
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateInt("Customer_ID", customer.getId());
            rs.updateInt("User_ID", user.getId());
            rs.updateInt("Contact_ID", contact.getID());
            rs.updateRow();

            //Refresh the observable list.
            loadData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage("Record Could Not Be Updated", "We are unable to update the appointment record.");
            dialog.showAndWait();
            return false;
        }
    }

    public final boolean delete() {
        try {
            //Get the selected record.
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM appointments WHERE Appointment_ID = " + this.getId();
            ResultSet rs = stmt.executeQuery(qry);

            //Delete the selected record.
            rs.deleteRow();

            //Refresh the ObservableList
            loadData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage("Can't Delete", "Something went wrong and we couldn't delete this appointment.");
            dialog.showAndWait();
            return false;
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
        //Here we pull all the Appointment data from the database
        try {
            //Create statement and get results.
            Statement stmt = JDBC.connection.createStatement();
            String query = "SELECT * FROM appointments";
            ResultSet rs = stmt.executeQuery(query);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage("Data Not Found", "No appointments were found in the database.");
                dialog.showAndWait();
            } else {
                allAppointments = FXCollections.observableList(new ArrayList<Appointment>());

                //Loop through results and create new appointments.
                do {
                    Appointment appt = new Appointment(
                            rs.getInt("Appointment_ID"),
                            rs.getString("Title"),
                            rs.getString("Description"),
                            rs.getString("Location"),
                            rs.getString("Type"),
                            DTC.timestampToZonedDateTime(rs.getObject("Start", Timestamp.class)),
                            DTC.timestampToZonedDateTime(rs.getObject("End", Timestamp.class)),
                            Customer.getById(rs.getInt("Customer_ID")),
                            User.getById(rs.getInt("User_ID")),
                            Contact.getById(rs.getInt(("Contact_ID")))
                    );
                    allAppointments.add(appt);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Data Could Not Be Loaded","Appointment could not be loaded from the database.");
            dialog.showAndWait();
        }
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
