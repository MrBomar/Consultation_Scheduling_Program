package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Customer {
    public final SimpleIntegerProperty id;
    public SimpleStringProperty customerName, address, postalCode, phone;
    public SimpleObjectProperty<Division> division;
    public static ObservableList<Customer> allCustomers;

    public Customer(int id, String customerName, String address, String postalCode, String phone, Division division) {
        this.id = new SimpleIntegerProperty(id);
        this.customerName = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.division = new SimpleObjectProperty<Division>();
    }

    @Override
    public String toString() {
        return this.customerName.get();
    }

    public static Customer add(String customerName, String address, String postalCode, String phone, Division division) {
        //FIXME - Need to add to database and return the data object
        //FIXME - Make sure to capture the user when sending to the database.
        return new Customer(1, customerName, address, postalCode, phone, division);
    }

    public boolean update(String customerName, String address, String postalCode, String phone, Division division){
        //FIXME - Need to send update to database and return the data object.
        //FIXME - Make sure to capture the user when sending to the database.
        this.customerName.set(customerName);
        this.address.set(address);
        this.postalCode.set(postalCode);
        this.phone.set(phone);
        this.division.set(division);
        return true;
    }

    public final void delete() {
        //FIXME - Send delete request to server and return confirmation
        try {
            allCustomers.remove(this);
        } catch (NullPointerException e) {
            DialogMessage dialog = new DialogMessage("Can't Delete", "Something went wrong and this customer cannot be deleted.");
            dialog.showAndWait();
        }
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

    public int getId() { return this.id.get(); }
    public String getIDString() { return "" + this.id.get(); }
    public String getCustomerName() {
        return this.customerName.get();
    }
    public String getAddress() { return this.address.get(); }
    public String getPostalCode() { return this.postalCode.get(); }
    public String getPhone() { return this.phone.get(); }
    public int getDivisionID() { return this.division.get().getID(); }
    public String getDivisionName() { return Division.getDivisionById(this.getDivisionID()).getName(); }

    public static Customer getById(int id) {
        try {
            return allCustomers.stream().filter(i -> i.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingCustomerRecord);
            dialog.showAndWait();
            return allCustomers.stream().findFirst().get();
        }
    }
}
