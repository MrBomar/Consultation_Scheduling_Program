package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.DTC;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Main;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class manages the data objects related to the Customers table in the database.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Customer {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, address, postalCode, phone;
    private final SimpleObjectProperty<Country> country;
    private final SimpleObjectProperty<Division> division;
    public static ObservableList<Customer> allCustomers;

    /**
     * This constructor instantiates a Customer object which stores the details of the customer and provides methods
     * for editing the customer data.
     * @param id The record number of the customer.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param country The country where the customer resides.
     * @param division The division(city/town) where the customer resides.
     */
    public Customer(int id, String customerName, String address, String postalCode,
                    String phone, Country country, Division division) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.country = new SimpleObjectProperty<>(country);
        this.division = new SimpleObjectProperty<>(division);
    }

    /**
     * This method overrides the default toString() method for the Customer class.
     * @return The name of the customer.
     */
    @Override
    public String toString() {
        return this.name.get();
    }

    /**
     * This method creates anew customer record in the database and refreshes the Customer list.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param division The division(city/town) of the customer.
     */
    public static void add(String customerName, String address, String postalCode, String phone,
                           Division division) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM customers");

            rs.moveToInsertRow();
            rs.updateString("Customer_Name", customerName);
            rs.updateString("Address", address);
            rs.updateString("Postal_Code", postalCode);
            rs.updateString("Phone", phone);
            rs.updateTimestamp("Create_Date", DTC.currentTimestamp());
            rs.updateString("Created_By", Main.currentUser.getUserName());
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateInt("Division_Id", division.getId());
            rs.insertRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotAdded,
                    new String[] {lrb.getString("customer")}
            );
            dialog.showAndWait();
        }
    }

    /**
     * This method deletes the current customer from the database and refreshes the Customer list.
     */
    public final void delete() {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM Customers WHERE Customer_ID = " + this.getId());

            rs.next();
            rs.deleteRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotDeleted,
                    new String[]{lrb.getString("customer")}
            );
            dialog.showAndWait();
        }
    }

    /**
     * This method updates the information related to the customer in the database and refreshes the Customer list.
     * @param customerName The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phone The phone number of the customer.
     * @param division The division(city/town) where the customer is located.
     */
    public final void update(
            String customerName, String address, String postalCode, String phone, Division division) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM customers WHERE Customer_ID = " + this.getId());

            rs.next();
            rs.updateString("Customer_Name", customerName);
            rs.updateString("Address", address);
            rs.updateString("Postal_Code", postalCode);
            rs.updateString("Phone", phone);
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateInt("Division_ID", division.getId());
            rs.updateRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotUpdated,
                    new String[]{lrb.getString("customer")}
            );
            dialog.showAndWait();
        }
    }

    /**
     * This method returns a filtered Appointment list for all appointment associated with the current customer.
     * A lambda is used here to filter the appointments list instead of using a Predicate, which is more verbose and
     * harder to understand.
     * @return A list of all appointments belonging to the customer.
     */
    public final ObservableList<Appointment> getAppointments() {
        Appointment[] appointments = Appointment.allAppointments.stream()
                .filter(i -> i.getCustomer().equals(this)).toArray(Appointment[]::new);
        return FXCollections.observableArrayList(appointments);
    }

    /**
     * This method loads all the customer records from the database.
     */
    public static void loadData() {
        try {
            //Load data from database
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM customers";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage(
                        Message.DataNotFound,
                        new String[]{lrb.getString("customers")}
                );
                dialog.showAndWait();
            } else {
                allCustomers = FXCollections.observableList(new ArrayList<>());

                //Loop through result and add customers
                do {
                    Customer customer = new Customer(
                            rs.getInt("Customer_ID"),
                            rs.getString("Customer_Name"),
                            rs.getString("Address"),
                            rs.getString("Postal_Code"),
                            rs.getString("Phone"),
                            Division.getById(rs.getInt("Division_ID")).getCountry(),
                            Division.getById(rs.getInt("Division_ID"))
                    );
                    allCustomers.add(customer);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.DataNotLoaded,
                    new String[]{lrb.getString("customer")}
            );
            dialog.showAndWait();
        }
    }

    //Getters

    /**
     * This method gets the address of the customer.
     * @return The addresses of the customer.
     */
    public final String getAddress() { return this.address.get(); }

    /**
     * This method gets the Country object belonging to the customer.
     * @return The Country object representing where the customer resides.
     */
    public final Country getCountry() { return this.country.get(); }

    /**
     * This method gets the Division object belonging to the customer.
     * @return The Division object representing where the customer resides.
     */
    public final Division getDivision() { return this.division.get(); }

    /**
     * This method gets the record ID of the customer record.
     * @return The record ID of the customer.
     */
    public final Integer getId() { return this.id.get(); }

    /**
     * This method get the name of the customer.
     * @return The name of the customer.
     */
    public final String getName() { return this.name.get(); }

    /**
     * This method gets the phone number of the customer.
     * @return The phone number of the customer.
     */
    public final String getPhone() { return this.phone.get(); }

    /**
     * This method returns the postal code of the customer.
     * @return The postal code of the customer.
     */
    public final String getPostalCode() { return this.postalCode.get(); }

    /**
     * This method locates and return the Customer object using the customer record ID.
     * @param id The customer record ID.
     * @return The Customer object associated with the record ID.
     */
    public static Customer getById(int id) { return allCustomers.stream().filter(i -> i.getId() == id).findFirst().get(); }

    //Property Getters

    /**
     * This method returns the address property of the Customer object.
     * @return The address property of the Customer object.
     */
    public final Property<String> getAddressProperty() { return this.address; }

    /**
     * This method returns the country property of the Customer object.
     * @return The country property of the Customer object.
     */
    public final ObjectProperty<Country> getCountryProperty() { return this.country; }

    /**
     * This method returns the division property of the Customer object.
     * @return The division property of the Customer object.
     */
    public final ObjectProperty<Division> getDivisionProperty() { return this.division; }

    /**
     * This method returns the ID property of the Customer object.
     * @return The ID property of the Customer object.
     */
    public final Property<Number> getIdProperty() { return this.id; }

    /**
     * This method returns the name property of the Customer object.
     * @return The name property of the Customer object.
     */
    public final Property<String> getNameProperty() { return this.name; }

    /**
     * This method returns the phone number property of the Customer object.
     * @return The phone number property of the Customer object.
     */
    public final Property<String> getPhoneProperty() { return this.phone; }

    /**
     * This method returns the postal code property of the Customer object.
     * @return
     */
    public final Property<String> getPostalCodeProperty() { return this.postalCode; }

    //Property Setter

    /**
     * This method sets the address of the customer.
     * @param address The address of the customer.
     */
    public final void setAddress(String address) {
        this.address.set(address);
        JDBC.updateStringValue(this.getId(), "customers","Address", address);
    }

    /**
     * This method sets the Country object of the customer.
     * @param country The Country object where the customer resides.
     */
    public final void setCountry(Country country) { this.country.set(country); }

    /**
     * This method sets the Division object of the customer.
     * @param division The Division object where the customer resides.
     */
    public final void setDivision(Division division) {
        this.division.set(division);
        JDBC.updateIntValue(this.getId(), "customers", "Division_ID", division.getId());
    }

    /**
     * This method sets the record ID of the customer.
     * @param id The record ID of the customer.
     */
    public final void setId(int id) {
        this.id.set(id);
        JDBC.updateIntValue(this.getId(), "customers", "Customer_ID", id);
    }

    /**
     * This method sets the name of the customer.
     * @param name The name of the customer.
     */
    public final void setName(String name) {
        this.name.set(name);
        JDBC.updateStringValue(this.getId(), "customers", "Customer_Name", name);
    }

    /**
     * This method sets the phone number of the customer.
     * @param phone The phone number of the customer.
     */
    public final void setPhone(String phone) {
        this.phone.set(phone);
        JDBC.updateStringValue(this.getId(), "customers", "Phone", phone);
    }

    /**
     * This method sets the postal code of the customer.
     * @param postalCode The postal code of the customer.
     */
    public final void setPostalCode(String postalCode) {
        JDBC.updateStringValue(this.getId(), "customers", "Postal_Code", postalCode);
    }
}

