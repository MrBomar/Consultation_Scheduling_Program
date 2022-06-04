package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private int contactId;
    private String contactName, email;
    private static ObservableList<Contact> allContacts;

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public static Contact add(String contactName, String email) {
        //FIXME - Need to connect to database, add contact and return object from database
        return new Contact(1,"Customer1", "customer@company.com");
    }

    public boolean delete() {
        //FIXME - Send delete request and return status
        return true;
    }

    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Contact[] contacts = new Contact[] {
            new Contact(1,"Contact2", "contact2@company.com"),
            new Contact(0,"Contact1", "contact1@company.com"),
            new Contact(2,"Contact3", "contact3@company.com"),
            new Contact(3,"Contact4", "contact4@company.com"),
            new Contact(4,"Contact5", "contact5@company.com"),
            new Contact(5,"Contact6", "contact6@company.com"),
            new Contact(6,"Contact7", "contact7@company.com"),
            new Contact(7,"Contact8", "contact8@company.com"),
            new Contact(8,"Contact9", "contact9@company.com"),
            new Contact(9,"Contact10", "contact10@company.com")
        };
        allContacts = FXCollections.observableList(new ArrayList<Contact>(List.of(contacts)));


    }

    public String getName() {
        return this.contactName;
    }
    public int getID() { return this.contactId; }
}
