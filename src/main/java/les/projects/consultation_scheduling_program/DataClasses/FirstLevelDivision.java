package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class FirstLevelDivision {
    private int divisionId;
    private String divisionName;
    private int countryId;
    private static ObservableList<FirstLevelDivision> allDivisions;

    public FirstLevelDivision(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public static FirstLevelDivision add(String divisionName, int countryId) {
        //FIXME - Should take supplied inputs, create a new division, and return the new object from the database.
        return new FirstLevelDivision(0, "London", 2);
    }

    public boolean update(String divisionName, int countryId) {
        //FIXME - Should send update to database and return status.
        this.divisionName = divisionName;
        this.countryId = countryId;
        return true;
    }

    public boolean delete() {
        //FIXME - Send delete request to database and return status.
        return true;
    }

    public ObservableList<Customer> getCustomers() {
        //FIXME - Should filter all customers for instances related to this object.
        return Customer.getAllCustomers();
    }

    public int getID() {
        return this.divisionId;
    }

    public String getName() {
        return this.divisionName;
    }

    public static ObservableList<FirstLevelDivision> getAllDivisions() {
        return allDivisions;
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        FirstLevelDivision[] divisions = new FirstLevelDivision[] {
                new FirstLevelDivision(0, "Division 1", 0),
                new FirstLevelDivision(1, "Division 2", 1),
                new FirstLevelDivision(2, "Division 3", 2),
                new FirstLevelDivision(3, "Division 4", 3),
                new FirstLevelDivision(4, "Division 5", 4),
                new FirstLevelDivision(5, "Division 6", 5),
                new FirstLevelDivision(6, "Division 7", 6),
                new FirstLevelDivision(7, "Division 8", 7),
                new FirstLevelDivision(8, "Division 9", 8),
                new FirstLevelDivision(9, "Division 10", 9)
        };
        allDivisions = FXCollections.observableList(new ArrayList<FirstLevelDivision>(List.of(divisions)));
    }
}
