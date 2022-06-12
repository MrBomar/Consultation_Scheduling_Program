package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Division {
    private int divisionId;
    private String divisionName;
    private int countryId;
    private static ObservableList<Division> allDivisions;

    public Division(int divisionId, String divisionName, int countryId) {
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    @Override
    public String toString() {
        return this.divisionName;
    }

    public static Division add(String divisionName, int countryId) {
        //FIXME - Should take supplied inputs, create a new division, and return the new object from the database.
        return new Division(0, "London", 2);
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

    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Division[] divisions = new Division[] {
                new Division(0, "Division 1", 0),
                new Division(1, "Division 2", 1),
                new Division(2, "Division 3", 2),
                new Division(3, "Division 4", 3),
                new Division(4, "Division 5", 4),
                new Division(5, "Division 6", 5),
                new Division(6, "Division 7", 6),
                new Division(7, "Division 8", 7),
                new Division(8, "Division 9", 8),
                new Division(9, "Division 10", 9)
        };
        allDivisions = FXCollections.observableList(new ArrayList<Division>(List.of(divisions)));
    }

    public static Division getObjById(int id) {
        try {
            return allDivisions.stream().filter(d -> d.getID() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingDivisionRecord);
            dialog.showAndWait();
            return allDivisions.stream().findFirst().get();
        }
    }
}
