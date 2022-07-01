package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

import static les.projects.consultation_scheduling_program.Main.lrb;

/**
 * This class stores data related to the first_level_divisions in the database and provides methods for managing
 * data related to the division.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Division {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleObjectProperty<Country> country;
    public static ObservableList<Division> allDivisions;

    public Division(int divisionId, String divisionName, Country country) {
        this.id = new SimpleIntegerProperty(divisionId);
        this.name = new SimpleStringProperty(divisionName);
        this.country = new SimpleObjectProperty<>(country);
    }

    /**
     * This method overrides the default toString method for the Division class.
     * @return The name of the division.
     */
    @Override
    public String toString() {
        return this.getName() ;
    }

    /**
     * This method creates a new Division object and writes it to the database. Then refreshes the list of divisions.
     * @param divisionName The name of the division.
     * @param country The Country object where the division resides.
     */
    public static void add(String divisionName, Country country) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM first_level_divisions");

            rs.moveToInsertRow();
            rs.updateString("Division", divisionName);
            rs.updateTimestamp("Create_Date", DTC.currentTimestamp());
            rs.updateString("Created_By", Main.currentUser.getUserName());
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateInt("Country_ID", country.getId());
            rs.insertRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(Message.RecordNotAdded,new String[]{lrb.getString("division")});
            dialog.showAndWait();
        }
    }

    /**
     * This method updates the information related to the Division, writes it to the database and refreshes
     * the list of divisions.
     * @param divisionName The name of the division.
     * @param country The Country object where the division resides.
     */
    public final void update(String divisionName, Country country) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM first_level_divisions WHERE Division_ID = " + this.getId());

            rs.next();
            rs.updateString("Division", divisionName);
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateInt("Country_ID", country.getId());
            rs.updateRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotUpdated,
                    new String[] {lrb.getString("division")}
            );
            dialog.showAndWait();
        }
    }

    /**
     * This method deletes the division from the database and refreshes the list of divisions.
     */
    public final void delete() {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM first_level_divisions WHERE Division_ID = " + this.getId());

            rs.next();
            rs.deleteRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.RecordNotDeleted,
                    new String[] {lrb.getString("division")}
            );
            dialog.showAndWait();
        }
    }

    /**
     * This method filters the Customer list to retrieve a list of Customer related to this division.
     * @return An list of Customer objects residing in this division.
     */
    public final ObservableList<Customer> getCustomers() {
        Customer[] filteredCustomers =
                Customer.allCustomers.stream().filter(c -> c.getDivision().equals(this)).toArray(Customer[]::new);
        return FXCollections.observableArrayList(filteredCustomers);
    }

    /**
     * This method loads the first_level_divisions data from the database and converts each record into an instance
     * of Division, then stores those instances in a list of Divisions.
     */
    public static void loadData() {
        try {
            //Load the data from the database
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM first_level_divisions";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage(
                        Message.DataNotFound,
                        new String[] {lrb.getString("divisions")}
                );
                dialog.showAndWait();
            } else {
                allDivisions = FXCollections.observableList(new ArrayList<>());

                //Loop through results and add division to list.
                do {
                    Division division = new Division(
                            rs.getInt("Division_ID"),
                            rs.getString("Division"),
                            Country.getById(rs.getInt("Country_ID"))
                    );
                    allDivisions.add(division);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    Message.DataNotLoaded,
                    new String[] {lrb.getString("division")}
            );
            dialog.showAndWait();
        }
    }

    //Getters

    /**
     * This method gets the Country object associated with this division.
     * @return The Country object related to this division.
     */
    public final Country getCountry() { return this.country.get(); }

    /**
     * This method gets the name of the division.
     * @return The name of the division.
     */
    public final String getName() { return this.name.get(); }

    /**
     * This method gets the record ID of the division.
     * @return The record ID of the division.
     */
    public final Integer getId() { return this.id.get(); }

    /**
     * This method finds the Division object related to the record ID of the division.
     * @param id The record ID of the division.
     * @return The Division object associated to the record ID.
     */
    public static Division getById(int id) { return allDivisions.stream().filter(d -> d.getId() == id).findFirst().get(); }

    //Property Getters

    /**
     * This method returns the country property of the Division object.
     * @return The country property of the Division object.
     */
    public final Property<Country> getCountryProperty() { return this.country; }

    /**
     * This method returns the name property of the Division object.
     * @return The name property of the Division object.
     */
    public final Property<String> getNameProperty() { return this.name; }

    /**
     * This method returns the ID property of the Division object.
     * @return The ID property of the Division object.
     */
    public final Property<Number> getIdProperty() { return this.id; }

    //Property Setters

    /**
     * This method sets the Country object of the division.
     * @param country The Country object where the division resides.
     */
    public final void setCountry(Country country) { this.country.set(country);}

    /**
     * This method set the name of the division.
     * @param name The name of the division.
     */
    public final void setName(String name) { this.name.set(name);}

    /**
     * This method sets the record ID of the division.
     * @param id The record ID of the division.
     */
    public final void setId(int id) { this.id.set(id); }
}
