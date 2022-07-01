package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
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
 * Country class stores and manages the country data from the database.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class Country {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    public static ObservableList<Country> allCountries;

    public Country(int countryId, String countryName) {
        this.id = new SimpleIntegerProperty(countryId);
        this.name = new SimpleStringProperty(countryName);
    }

    /**
     * Replaces the default toString() function. Returns the country name.
     * @return Country name.
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Adds a new country to the database and refreshes the country observable list.
     * @param countryName The name of the country.
     */
    public static void add(String countryName) {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM countries");

            rs.moveToInsertRow();
            rs.updateString("Country", countryName);
            rs.updateTimestamp("Create_Date", DTC.currentTimestamp());
            rs.updateString("Created_By", Main.currentUser.getUserName());
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.insertRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            String[] words = new String[] {lrb.getString("country")};
            DialogMessage dialog = new DialogMessage(Message.RecordNotAdded, words);
            dialog.showAndWait();
        }
    }

    /**
     * Deletes the country record from the database.
     */
    public final void delete() {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM countries WHERE Country_ID = " + this.getId());

            rs.next();
            rs.deleteRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            String[] words = new String[] {lrb.getString("country")};
            DialogMessage dialog = new DialogMessage(Message.RecordNotDeleted, words);
            dialog.showAndWait();
        }
    }

    /**
     * Updates the country information in the database.
     * @param countryName The name of the country.
     */
    public final void update(String countryName){
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM countries WHERE Country_ID = " + this.getId());

            rs.next();
            rs.updateString("Country", countryName);
            rs.updateTimestamp("Last_Update", DTC.currentTimestamp());
            rs.updateString("Last_Updated_By", Main.currentUser.getUserName());
            rs.updateRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            String[] words = new String[] {lrb.getString("country")};
            DialogMessage dialog = new DialogMessage(Message.RecordNotUpdated, words);
            dialog.showAndWait();
        }
    }

    /**
     * Returns all division objects related to the country.
     * @return Division data object.
     */
    public ObservableList<Division> getDivisions() {
        Division[] filteredDivisions =
                Division.allDivisions.stream().filter(i -> i.getCountry().equals(this)).toArray(Division[]::new);
        return FXCollections.observableArrayList(filteredDivisions);
    }

    /**
     * Pulls all country data from the database and loads it into the application as Country objects.
     */
    public static void loadData() {
        //Query the database
        try {
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM countries";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                String[] words = new String[] {lrb.getString("countries")};
                DialogMessage dialog = new DialogMessage(Message.DataNotFound, words);
                dialog.showAndWait();
            } else {
                allCountries = FXCollections.observableList(new ArrayList<>());

                //Loop through the results
                do {
                    Country country = new Country(
                            rs.getInt("Country_ID"),
                            rs.getString("Country")
                    );
                    allCountries.add(country);
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            String[] words = new String[] {lrb.getString("countries")};
            DialogMessage dialog = new DialogMessage(Message.DataNotLoaded, words);
            dialog.showAndWait();
        }
    }

    //Getters

    /**
     * Gets the country record ID.
     * @return Country ID
     */
    public final int getId() { return this.id.get(); }

    /**
     * Gets the country name.
     * @return Country name.
     */
    public final String getName() { return this.name.get(); }

    /**
     * Returns a Country object using the country ID.
     * @param id Country record ID.
     * @return Country object.
     */
    public static Country getById(int id) { return allCountries.stream().filter(d -> d.getId() == id).findFirst().get(); }

    //Property Getters

    /**
     * Gets the ID property.
     * @return Property value.
     */
    public final Property<Number> getIdProperty() { return this.id; }

    /**
     * Gets the name property.
     * @return Name property of the country.
     */
    public final Property<String> getNameProperty() { return this.name; }

    //Setters

    /**
     * Sets the country record ID
     * @param id Country_ID
     */
    public final void setId(int id) { this.id.set(id); }

    /**
     * Sets the country name.
     * @param countryName Country name.
     */
    public final void setName(String countryName) { this.name.set(countryName); }
}