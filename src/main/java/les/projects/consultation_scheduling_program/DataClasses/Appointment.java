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
import java.util.ArrayList;

/**
 * This class manages the Appointment data object and related methods. It also handles all associated database methods.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Appointment {
    private final SimpleObjectProperty<Customer> customer;
    private final SimpleObjectProperty<Contact> contact;
    private final SimpleObjectProperty<User> user;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty title, description, location, type;
    private final SimpleObjectProperty<ZonedDateTime> start, end;
    public static ObservableList<Appointment> allAppointments;

    /**
     * Creates a new Appointment object.
     * @param id The Appointment_ID value referenced in the database. (Integer)
     * @param title The Title value referenced in the database. (String)
     * @param description The Description value referenced in the database. (String)
     * @param location The Location value referenced in the database. (String)
     * @param type The Type value referenced in the database. (String)
     * @param start A ZonedDateTime object instantiated using the Timestamp Start referenced in the database set to the LocalZone (ZonedDateTime)
     * @param end A ZonedDateTime object instantiated using the Timestamp End referenced in the database set to the LocalZone (ZonedDateTime)
     * @param customer A Customer object instantiated using the Customer_ID referenced in the database. (Customer)
     * @param user A User object instantiated using the User_ID referenced in the database. (User)
     * @param contact A Contact object instantiated using the Contact_ID in the database. (Contact)
     */
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

    /**
     * This method overrides the default toSting() method for the appointment.
     * @return Returns the appointment title as a String.
     */
    @Override
    public String toString() {
        return this.title.get();
    }

    /**
     * This method creates a new Appointment in the database and then reloads all Appointment data from the database.
     * This method intakes ZonedDateTime values in the local timezone and converts them to UCT before writing them to
     * the database.
     * @param title The title of the Appointment. (String)
     * @param description The description of the Appointment. (String)
     * @param location The location of the Appointment. (String)
     * @param type The type of the Appointment. (Type)
     * @param start The start date and time of the Appointment set to the local timezone. (ZonedDateTime)
     * @param end The start date and time of the Appointment set to the local timezone. (ZonedDateTime)
     * @param customer The Customer object associated with the Appointment. (Customer)
     * @param user The User object associated with the Appointment. (User)
     * @param contact The Contact object associated with the Appointment. (Contact)
     * @return Returns true if the Appointment was added to the database.
     */
    public static boolean add(String title, String description, String location,
                           String type, ZonedDateTime start, ZonedDateTime end,
                           Customer customer, User user,Contact contact) {
        try {
            //Get records
            ResultSet rs = JDBC.newResultSet("SELECT * FROM appointments");

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
            rs.updateInt("Customer_ID", customer.getId());
            rs.updateInt("User_ID", user.getId());
            rs.updateInt("Contact_ID", contact.getID());

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

    /**
     * This method intakes user input and saves it to a preselected Appointment record. Note: This method intakes
     * ZonedDateTime set to the local timezone and converts it to UCT before writing it to the database.
     * @param title The title of the Appointment. (String)
     * @param description The description of the Appointment. (String)
     * @param location The location of the Appointment. (String)
     * @param type The type of the Appointment. (Type)
     * @param start The start date and time of the Appointment set to the local timezone. (ZonedDateTime)
     * @param end The start date and time of the Appointment set to the local timezone. (ZonedDateTime)
     * @param customer The Customer object associated with the Appointment. (Customer)
     * @param user The User object associated with the Appointment. (User)
     * @param contact The Contact object associated with the Appointment. (Contact)
     * @return Returns true if the item was updated in the database.
     */
    public final boolean update(String title, String description, String location, String type,
                          ZonedDateTime start, ZonedDateTime end, Customer customer, User user, Contact contact) {
        try {
            //Pull current record.
            ResultSet rs = JDBC.newResultSet("SELECT * FROM appointments WHERE Appointment_ID = " + this.getId());

            //Update the record fields
            rs.next();
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

    /**
     * This method deletes the Appointment from the database and reloads all Appointment data from the database.
     * @return Returns true if the deletion was successful.
     */
    public final boolean delete() {
        try {
            //Get the selected record.
            ResultSet rs = JDBC.newResultSet("SELECT * FROM appointments WHERE Appointment_ID = " + this.getId());

            //Delete the selected record.
            rs.next();
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

    /**
     * This method selects all Appointments for the current user and checks them to see if they will occur within 15
     * minutes of the user's login. If there are appointments within 15 minutes then a dialog is displayed containing
     * the appointment information. If there are no appointments within 15 minutes then a dialog is displayed stating
     * that there are no upcoming appointments.
     */
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

    /**
     * This method queries the database for all Appointment data and parses it to populate the allAppointments list with
     * Appointment objects.
     */
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
                allAppointments = FXCollections.observableList(new ArrayList<>());

                //Loop through results and create new appointments.
                do {
                    Appointment appointment = new Appointment(
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
                    allAppointments.add(appointment);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Data Could Not Be Loaded","Appointment could not be loaded from the database.");
            dialog.showAndWait();
        }
    }

    //Getters

    /**
     * Returns the Contact object associated to the Appointment.
     * @return Contact object associated to the Appointment.
     */
    public final Contact getContact() { return this.contact.get(); }

    /**
     * Returns the Customer object associated to the Appointment.
     * @return Customer object associated to the Appointment.
     */
    public final Customer getCustomer() { return this.customer.get(); }

    /**
     * Returns the description associated to the Appointment.
     * @return Description of the Appointment. (String)
     */
    public final String getDescription() { return this.description.get(); }

    /**
     * Returns the end date and time of the Appointment.
     * @return The end time of the Appointment set to the local timezone. (ZonedDateTime)
     */
    public final ZonedDateTime getEnd() { return this.end.get(); }

    /**
     * Returns ID of the Appointment.
     * @return The ID of the Appointment. (Integer)
     */
    public final int getId() { return this.id.get(); }

    /**
     * Returns the location of the Appointment.
     * @return The location of the Appointment. (String)
     */
    public final String getLocation() { return this.location.get(); }

    /**
     * Returns the start date and time of the Appointment.
     * @return The start time of the Appointment set to the local timezone. (ZonedDateTime)
     */
    public final ZonedDateTime getStart() { return this.start.get(); }

    /**
     * Returns the title of the Appointment.
     * @return The title of the Appointment. (String)
     */
    public final String getTitle() { return this.title.get(); }

    /**
     * Returns the type of the Appointment.
     * @return The type of the Appointment. (String)
     */
    public final String getType() { return this.type.get(); }

    /**
     * Returns the User object associated to the Appointment.
     * @return User object associated to the Appointment.
     */
    public final User getUser() { return this.user.get(); }

    //Property Getters

    /**
     * Returns the property value of the Contact.
     * @return Property value of the Contact.
     */
    public final ObjectProperty<Contact> getContactProperty() { return this.contact; }

    /**
     * Returns the property value of the Customer.
     * @return Property value of the Customer.
     */
    public final ObjectProperty<Customer> getCustomerProperty() { return this.customer; }

    /**
     * Returns the property value of the description.
     * @return Property value of description.
     */
    public final Property<String> getDescriptionProperty() { return this.description; }

    /**
     * Returns the property value of the end.
     * @return Property value of end.
     */
    public final ObjectProperty<ZonedDateTime> getEndProperty() { return this.end; }

    /**
     * Returns the property value of the ID.
     * @return Property of the ID.
     */
    public final Property<Number> getIdProperty() {  return this.id; }

    /**
     * Returns the property value of the location.
     * @return Property of the location.
     */
    public final Property<String> getLocationProperty() { return this.location; }

    /**
     * Returns the property value of the start.
     * @return Property of the start.
     */
    public final ObjectProperty<ZonedDateTime> getStartProperty() { return this.start; }

    /**
     * Return the property value of the title.
     * @return Property of the title.
     */
    public final Property<String> getTitleProperty() { return this.title; }

    /**
     * Return the property value of the type.
     * @return Property of the type.
     */
    public final Property<String> getTypeProperty() { return this.type; }

    /**
     * Return the property value of the user.
     * @return Property of the user.
     */
    public final ObjectProperty<User> getUserProperty() { return this.user; }

    //Property Setters

    /**
     * Takes in a Contact object and sets it as the Appointment contact.
     * @param contact Contact object of the contact on the Appointment.
     */
    public final void setContact(Contact contact) { this.contact.set(contact); }

    /**
     * Takes in a Customer object and sets it at the customer for the Appointment.
     * @param customer Customer of the Appointment.
     */
    public final void setCustomer(Customer customer) { this.customer.set(customer); }

    /**
     * Takes in a string and sets it as the description for the Appointment.
     * @param description Description of the appointment.
     */
    public final void setDescription(String description) { this.description.set(description); }

    /**
     * Takes in a ZonedDateTime and sets it as the End for the Appointment.
     * @param end End date and time.
     */
    public final void setEnd(ZonedDateTime end) { this.end.set(end); }

    /**
     * Takes in an integer and sets it as the Appointment ID.
     * @param id Record ID.
     */
    public final void setId(int id) { this.id.set(id); }

    /**
     * Takes in a string and sets it as the Appointment location.
     * @param location Location of the appointment.
     */
    public final void setLocation(String location) { this.location.set(location); }

    /**
     * Takes in a ZonedDateTime and sets it as the Appointment Start.
     * @param start The start date and time of the appointment.
     */
    public final void setStart(ZonedDateTime start) { this.start.set(start); }

    /**
     * Takes in a String and set it as the appointment title.
     * @param title Appointment title.
     */
    public final void setTitle(String title) { this.title.set(title); }

    /**
     * Takes in a String and sets it as the Appointment Type.
     * @param type Appointment Type.
     */
    public final void setType(String type) { this.type.set(type); }

    /**
     * Takes in a User object and sets it as the User who created the appointment.
     * @param user Appointment User.
     */
    public final void setUser(User user) { this.user.set(user); }
}
