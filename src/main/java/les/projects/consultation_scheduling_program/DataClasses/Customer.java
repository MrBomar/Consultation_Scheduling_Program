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
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Customer {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name, address, postalCode, phone;
    private final SimpleObjectProperty<Country> country;
    private final SimpleObjectProperty<Division> division;
    public static ObservableList<Customer> allCustomers;

    public Customer(int id, String customerName, String address, String postalCode, String phone, Country country, Division division) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(customerName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phone = new SimpleStringProperty(phone);
        this.country = new SimpleObjectProperty<>(country);
        this.division = new SimpleObjectProperty<>(division);
    }

    @Override
    public String toString() {
        return this.name.get();
    }

    public static void add(String customerName, String address, String postalCode, String phone, Country country, Division division) {
        try {
            Statement stmt = JDBC.connection.createStatement();
            String sql = "SELECT * FROM customers";
            ResultSet rs = stmt.executeQuery(sql);

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
            rs.updateRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Record Could Not Be Added", "The customer could not be added to the database.");
            dialog.showAndWait();
        }
    }

    public final void delete() {
        try {
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM Customer WHERE Customer_ID = " + this.getId();
            ResultSet rs = stmt.executeQuery(qry);

            rs.deleteRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Record Could Not Be Deleted","The customer could not be deleted from the database.");
            dialog.showAndWait();
        }
    }

    public final void update(
            String customerName, String address, String postalCode, String phone, Country country, Division division) {
        try {
            Statement stmt = JDBC.connection.createStatement();
            String sql = "SELECT * FROM customers WHERE Customer_ID = " + this.getId();
            ResultSet rs = stmt.executeQuery(sql);

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
                    "Record Could Not Be Updated","The customer could not be updated in the database.");
            dialog.showAndWait();
        }
    }

    public final ObservableList<Appointment> getAppointments() {
        Appointment[] appointments = Appointment.getAllAppointments().stream().filter(i -> i.getCustomer().equals(this)).toArray(Appointment[]::new);
        return FXCollections.observableArrayList(appointments);
    }

    public static void loadData() {
        try {
            //Load data from database
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM customers";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage("Data Not Found", "No customers were found in the database.");
                dialog.showAndWait();
            } else {
                allCustomers = FXCollections.observableList(new ArrayList<Customer>());

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
                    "Data Could Not Be Loaded", "The customers could not be loaded from the database.");
            dialog.showAndWait();
        }
    }

    //Getters
    public final String getAddress() { return this.address.get(); }
    public final Country getCountry() { return this.country.get(); }
    public final Division getDivision() { return this.division.get(); }
    public final Integer getId() { return this.id.get(); }
    public final String getName() { return this.name.get(); }
    public final String getPhone() { return this.phone.get(); }
    public final String getPostalCode() { return this.postalCode.get(); }
    public static Customer getById(int id) {
        try {
            return allCustomers.stream().filter(i -> i.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingCustomerRecord);
            dialog.showAndWait();
            return allCustomers.stream().findFirst().get();
        }
    }

    //Property Getters
    public final Property<String> getAddressProperty() { return this.address; }
    public final ObjectProperty<Country> getCountryProperty() { return this.country; }
    public final ObjectProperty<Division> getDivisionProperty() { return this.division; }
    public final Property<Number> getIdProperty() { return this.id; }
    public final Property<String> getNameProperty() { return this.name; }
    public final Property<String> getPhoneProperty() { return this.phone; }
    public final Property<String> getPostalCodeProperty() { return this.postalCode; }

    //Property Setter
    public final void setAddress(String address) { this.address.set(address); }
    public final void setCountry(Country country) { this.country.set(country); }
    public final void setDivision(Division division) { this.division.set(division);}
    public final void setId(int id) { this.id.set(id); }
    public final void setName(String name) { this.name.set(name); }
    public final void setPhone(String phone) { this.phone.set(phone); }
    public final void setPostalCode(String postalCode) { this.postalCode.set(postalCode); }
}

