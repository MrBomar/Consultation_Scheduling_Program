package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Helpers.JDBC;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Country {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    public static ObservableList<Country> allCountries;

    public Country(int countryId, String countryName) {
        this.id = new SimpleIntegerProperty(countryId);
        this.name = new SimpleStringProperty(countryName);
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static void add(String countryName) {
        //FIXME -- Needs to add this entry to the database and return the new data object.
        allCountries.add(new Country(1, countryName));
    }

    public boolean delete() {
        //FIXME - This method needs to verify deletion with the database before returning boolean value.
        return true;
    }

    public boolean update(int countryId, String countryName){
        //FIXME - This method needs to update the database and verify the update before making the change locally.
        this.id.set(countryId);
        this.name.set(countryName);
        return true;
    }

    public ObservableList<Division> getDivisions() {
        //FIXME - Need to return the values present in the database.
        Division[] filteredDivisions =
                Division.allDivisions.stream().filter(i -> i.getCountry().equals(this)).toArray(Division[]::new);
        return FXCollections.observableArrayList(filteredDivisions);
    }

    public static void loadData() {
        //Query the database
        try {
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM countries";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage("Data Not Found", "No countries were found in the database.");
            } else {
                allCountries = FXCollections.observableList(new ArrayList<Country>());

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
            System.out.println("Could not load countries.");
        }
    }

    //Getters
    public final int getId() {
        return this.id.get();
    }
    public final String getName() {
        return this.name.get();
    }
    public static Country getById(int id) {
        try {
            return allCountries.stream().filter(d -> d.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            System.out.println("Country count: " + allCountries.stream().count());
            System.out.println("Looking for: " + id);
            DialogMessage dialog = new DialogMessage(Message.MissingCountryRecord);
            dialog.showAndWait();
            return allCountries.stream().findFirst().get();
        }
    }

    //Property Getters
    public final Property<Number> getIdProperty() { return this.id; }
    public final Property<String> getNameProperty() { return this.name; }

    //Setters
    public final void setId(int id) { this.id.set(id); }
    public final void setName(String countryName) { this.name.set(countryName); }
}