package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Views.DialogMessage;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class stores information related to the contacts in the database.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Contact {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, email;
    public static ObservableList<Contact> allContacts;

    /**
     * Instantiates a new Contact object.
     * @param contactId The contact ID.
     * @param contactName The contact name.
     * @param email The contact email address.
     */
    public Contact(int contactId, String contactName, String email) {
        this.id = new SimpleIntegerProperty(contactId);
        this.name = new SimpleStringProperty(contactName);
        this.email = new SimpleStringProperty(email);
    }

    /**
     * Overrides the default toString() function.
     * @return Returns a String containing the contact name.
     */
    @Override
    public String toString() {
        return this.name.get();
    }

    /**
     * Creates a new contact in the databse.
     * @param contactName The name of the contact.
     * @param email The email address of the contact.
     * @return Returns true if the contact was added to the database.
     */
    public static boolean add(String contactName, String email) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM contacts");

            //Move to the insertRow and add record
            rs.moveToInsertRow();
            rs.updateString("Contact_Name", contactName);
            rs.updateString("Contact_Email", email);
            rs.insertRow();

            //Refresh the ObservableList
            loadData();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Could Not Add Record", "The contact could not be added to the database.");
            dialog.showAndWait();
            return false;
        }
    }

    /**
     * Deletes the contact associated with this object from the database.
     */
    public final void delete() {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM contacts WHERE Contact_ID = " + this.getID());

            //Try to delete
            rs.next();
            rs.deleteRow();

            //Refresh the ObservableList
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Could Not Delete Record", "The contact could not be deleted from the database.");
            dialog.showAndWait();
        }
    }

    /**
     * Loads the contact data from the database and creates new contact objects from the data.
     */
    public static void loadData() {
        //Load the data from the database.
        try {
            //Query the database for contacts.
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM contacts";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                 DialogMessage dialog = new DialogMessage("Data Not Found", "No contacts found in database.");
                 dialog.showAndWait();
            } else {
                allContacts = FXCollections.observableList(new ArrayList<>());

                //Loop through results and add them to list.
                do {
                    Contact contact = new Contact(
                            rs.getInt("Contact_ID"),
                            rs.getString("Contact_Name"),
                            rs.getString("Email")
                    );
                    allContacts.add(contact);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Data Could Not Be Loaded", " The contacts could not be loaded from the database.");
            dialog.showAndWait();
        }
    }

    /**
     * Updates the current contact in the database.
     * @param contactName Name of the contact.
     * @param email Email address of the contact.
     */
    public final void update(String contactName, String email) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM contacts WHERE Contact_ID = " + this.getID());

            //Update the record
            rs.next();
            rs.updateString("Contact_Name", contactName);
            rs.updateString("Email", email);
            rs.updateRow();

            //Refresh the ObservableList
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage("" +
                    "Record Could Not Be Updated", "Contact could not be updated in the database.");
            dialog.showAndWait();
        }
    }

    //Getters

    /**
     * Get the contact email address.
     * @return Email address
     */
    public final String getEmail() { return this.email.get(); }

    /**
     * Get the contact ID.
     * @return Contact ID.
     */
    public final int getID() { return this.id.get(); }

    /**
     * Get the name of the contact.
     * @return Contact name.
     */
    public final String getName() {
        return this.name.get();
    }

    /**
     * Gets the contact object with the provided Contact ID.
     * @param id Contact ID number.
     * @return Contact data object.
     */
    public static Contact getById(int id) { return allContacts.stream().filter(i -> i.getID() == id).findFirst().get(); }

    //Property Getters

    /**
     * Gets the email property of the contact.
     * @return The email property of the contact.
     */
    public final Property<String> getEmailProperty() { return this.email; }

    /**
     * Gets the ID property of the contact.
     * @return The ID property of the contact.
     */
    public final Property<Number> getIdProperty() {  return this.id; }

    /**
     * Gets the name property of the contact.
     * @return The name property of the contact.
     */
    public final Property<String> getNameProperty() { return this.name; }

    //Setters

    /**
     * Sets the email address of the contact.
     * @param email Email address.
     */
    public final void setEmail(String email) { this.email.set(email); }

    /**
     * Sets the ID of the contact.
     * @param id ID number.
     */
    public final void setId(int id) { this.id.set(id); }

    /**
     * Sets the name of the contact.
     * @param name Contact name.
     */
    public final void setName(String name) { this.name.set(name); }
}
