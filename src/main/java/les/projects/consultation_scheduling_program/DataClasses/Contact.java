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

public class Contact {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, email;
    public static ObservableList<Contact> allContacts;

    public Contact(int contactId, String contactName, String email) {
        this.id = new SimpleIntegerProperty(contactId);
        this.name = new SimpleStringProperty(contactName);
        this.email = new SimpleStringProperty(email);
    }

    @Override
    public String toString() {
        return this.name.get();
    }

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

    public void delete() {
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

    public void update(String contactName, String email) {
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
    public final String getEmail() { return this.email.get(); }
    public int getID() { return this.id.get(); }
    public String getName() {
        return this.name.get();
    }
    public static Contact getById(int id) {
        return allContacts.stream().filter(i -> i.getID() == id).findFirst().get();
    }

    //Property Getters
    public final Property<String> getEmailProperty() { return this.email; }
    public final Property<Number> getIdProperty() {  return this.id; }
    public final Property<String> getNameProperty() { return this.name; }

    //Setters
    public final void setEmail(String email) { this.email.set(email); }
    public final void setId(int id) { this.id.set(id); }
    public final void setName(String name) { this.name.set(name); }
}
