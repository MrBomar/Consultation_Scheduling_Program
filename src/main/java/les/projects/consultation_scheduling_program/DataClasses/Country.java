package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Country {
    private int countryId;
    private String countryName;
    private static ObservableList<Country> allCountries;

    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public static Country add(String countryName) {
        //FIXME -- Needs to add this entry to the database and return the new data object.
        return new Country(1,"Lestopia");
    }

    public boolean update(int countryId, String countryName){
        //FIXME - This method needs to update the database and verify the update before making the change locally.
        this.countryId = countryId;
        this.countryName = countryName;
        return true;
    }

    public boolean delete() {
        //FIXME - This method needs to verify deletion with the database before returning boolean value.
        return true;
    }

    public FirstLevelDivision[] getDivisions() {
        //FIXME - Need to return the values present in the database.
        FirstLevelDivision[] divisionData = new FirstLevelDivision[2];
        divisionData[0] = new FirstLevelDivision(1,"Lestertown", 1);
        divisionData[1] = new FirstLevelDivision(2, "Lestervinia", 2);
        return divisionData;
    }

    public static ObservableList<Country> getAllCountries() {
        return allCountries;
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
        return this.countryId;
    }

    public String getName() {
        return this.countryName;
    }
}