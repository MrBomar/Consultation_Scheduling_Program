package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Customer {
    private int id;
    private int divisionId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    public static ObservableList<Customer> allCustomers;

    public Customer(int id, String customerName, String address, String postalCode, String phone, Division division) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = division.getID();
    }

    @Override
    public String toString() {
        return this.customerName;
    }

    public static Customer add(String customerName, String address, String postalCode, String phone, Division division) {
        //FIXME - Need to add to database and return the data object
        //FIXME - Make sure to capture the user when sending to the database.
        return new Customer(1, customerName, address, postalCode, phone, division);
    }

    public boolean update(String customerName, String address, String postalCode, String phone, Division division){
        //FIXME - Need to send update to database and return the data object.
        //FIXME - Make sure to capture the user when sending to the database.
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = division.getID();
        return true;
    }

    public boolean delete() {
        //FIXME - Send delete request to server and return confirmation
        //This method needs to delete all customer appointments before deleting the customer record.
        return true;
    }

    public ObservableList<Appointment> getAppointments() {
        //FIXME - Need to return all appointments related to this instance of Customer.
        //Need to filter and return all appointments for this customer object.
        return Appointment.getAllAppointments();
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Customer[] customers = new Customer[] {
            new Customer(0,"Customer1","123 Main Street", "12345","123456789", Division.getDivisionById(1) ),
            new Customer(1,"Customer2","123 Main Street", "12345","123456789", Division.getDivisionById(2) ),
            new Customer(2,"Customer3","123 Main Street", "12345","123456789", Division.getDivisionById(3) ),
            new Customer(3,"Customer4","123 Main Street", "12345","123456789", Division.getDivisionById(4) ),
            new Customer(4,"Customer5","123 Main Street", "12345","123456789", Division.getDivisionById(5) ),
            new Customer(5,"Customer6","123 Main Street", "12345","123456789", Division.getDivisionById(6) ),
            new Customer(6,"Customer7","123 Main Street", "12345","123456789", Division.getDivisionById(7) ),
            new Customer(7,"Customer8","123 Main Street", "12345","123456789", Division.getDivisionById(8) ),
            new Customer(8,"Customer9","123 Main Street", "12345","123456789", Division.getDivisionById(9) ),
            new Customer(9,"Customer10","123 Main Street", "12345","123456789", Division.getDivisionById(0) )
        };
        allCustomers = FXCollections.observableList(new ArrayList<Customer>(List.of(customers)));
    }

    public int getId() { return this.id; }
    public String getIDString() { return "" + this.id; }
    public String getCustomerName() {
        return this.customerName;
    }
    public String getAddress() { return this.address; }
    public String getPostalCode() { return this.postalCode; }
    public String getPhone() { return this.phone; }
    public int getDivisionID() { return this.divisionId; }
    public String getDivisionName() { return Division.getDivisionById(this.divisionId).getName(); }

    public static Customer getObjById(int id) {
        try {
            return allCustomers.stream().filter(i -> i.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingCustomerRecord);
            dialog.showAndWait();
            return allCustomers.stream().findFirst().get();
        }
    }
}
