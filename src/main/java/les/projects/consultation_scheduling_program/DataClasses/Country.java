package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Country {
    private SimpleIntegerProperty countryId;
    private SimpleStringProperty countryName;
    public static ObservableList<Country> allCountries;

    public Country(int countryId, String countryName) {
        this.countryId.set(countryId);
        this.countryName.set(countryName);
    }

    @Override
    public String toString() {
        return this.countryName.get();
    }

    public static Country add(String countryName) {
        //FIXME -- Needs to add this entry to the database and return the new data object.
        return new Country(1,"Lestopia");
    }

    public boolean update(int countryId, String countryName){
        //FIXME - This method needs to update the database and verify the update before making the change locally.
        this.countryId.set(countryId);
        this.countryName.set(countryName);
        return true;
    }

    public boolean delete() {
        //FIXME - This method needs to verify deletion with the database before returning boolean value.
        return true;
    }

    public ObservableList<Division> getDivisions() {
        //FIXME - Need to return the values present in the database.
        Division[] filteredDivisions = Division.allDivisions.stream().filter(i -> i.getCountryId() == this.getID()).toArray(Division[]::new);
        return FXCollections.observableArrayList(filteredDivisions);
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Country[] countries = new Country[] {
            new Country(0, "Country1"),
            new Country(1, "Country2"),
            new Country(2, "Country3"),
            new Country(3, "Country4"),
            new Country(4, "Country5"),
            new Country(5, "Country6"),
            new Country(6, "Country7"),
            new Country(7, "Country8"),
            new Country(8, "Country9"),
            new Country(9, "Country10")
        };
        allCountries = FXCollections.observableList(new ArrayList<Country>(List.of(countries)));
    }

    public int getID() {
        return this.countryId.get();
    }

    public String getName() {
        return this.countryName.get();
    }

    public static Country getByID(int id) {
        try {
            return allCountries.stream().filter(d -> d.getID() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingCountryRecord);
            dialog.showAndWait();
            return allCountries.stream().findFirst().get();
        }
    }
}