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
            DialogMessage dialog = new DialogMessage(
                    "Record Could Not Be Added", "The country could not be added to the database.");
            dialog.showAndWait();
        }
    }

    public void delete() {
        try {
            ResultSet rs = JDBC.newResultSet("SELECT * FROM countries WHERE Country_ID = " + this.getId());

            rs.next();
            rs.deleteRow();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Record Could Not Be Deleted", "The country could not be deleted from the database.");
            dialog.showAndWait();
        }
    }

    public void update(String countryName){
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
            DialogMessage dialog = new DialogMessage(
                    "Record Could Not Be Updated", "The country could not be updated in the database.");
            dialog.showAndWait();
        }
    }

    public ObservableList<Division> getDivisions() {
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
                dialog.showAndWait();
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
            e.printStackTrace();
            DialogMessage dialog = new DialogMessage(
                    "Data Could Not Be Loaded", "The countries could not be loaded from the database.");
            dialog.showAndWait();
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