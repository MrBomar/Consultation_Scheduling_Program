package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
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
        allCountries = FXCollections.observableList(new ArrayList<>(List.of(countries)));
    }

    //Getters
    public final int getId() {
        return this.id.get();
    }
    public final String getName() {
        return this.name.get();
    }
    public static Country getByID(int id) {
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