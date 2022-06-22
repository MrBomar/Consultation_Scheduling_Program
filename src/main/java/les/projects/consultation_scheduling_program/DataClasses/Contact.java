package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

    public static Contact add(String contactName, String email) {
        //FIXME - Need to connect to database, add contact and return object from database
        return new Contact(1,contactName, email );
    }

    public boolean delete() {
        //FIXME - Send delete request and return status
        return true;
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Contact[] contacts = new Contact[] {
            new Contact(1,"Contact1", "contact2@company.com"),
            new Contact(0,"Contact2", "contact1@company.com"),
            new Contact(2,"Contact3", "contact3@company.com"),
            new Contact(3,"Contact4", "contact4@company.com"),
            new Contact(4,"Contact5", "contact5@company.com"),
            new Contact(5,"Contact6", "contact6@company.com"),
            new Contact(6,"Contact7", "contact7@company.com"),
            new Contact(7,"Contact8", "contact8@company.com"),
            new Contact(8,"Contact9", "contact9@company.com"),
            new Contact(9,"Contact10", "contact10@company.com")
        };
        allContacts = FXCollections.observableList(new ArrayList<>(List.of(contacts)));

    }

    public void update(String contactName, String email) {
        this.setName(contactName);
        this.setEmail(email);
    }

    //Getters
    public final String getEmail() { return this.email.get(); }
    public int getID() { return this.id.get(); }
    public String getName() {
        return this.name.get();
    }
    public static Contact getById(int id) {
        try {
            return allContacts.stream().filter(i -> i.getID() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingContactRecord);
            dialog.showAndWait();
            return allContacts.stream().findFirst().get();
        }
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
