package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Enums.Message;
import les.projects.consultation_scheduling_program.Views.DialogMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Division {
    public SimpleIntegerProperty id = null;
    public SimpleStringProperty divisionName;
    public SimpleObjectProperty<Country> country;
    public static ObservableList<Division> allDivisions;

    public Division(int divisionId, String divisionName, Country country) {
        this.id = new SimpleIntegerProperty(divisionId);
        this.divisionName = new SimpleStringProperty(divisionName);
        this.country = new SimpleObjectProperty<>(country);
    }

    @Override
    public String toString() {
        return this.divisionName.get();
    }

    public static Division add(String divisionName, int countryId) {
        //FIXME - Should take supplied inputs, create a new division, and return the new object from the database.
        return new Division(0, "London", Country.getCountryByID(0));
    }

    public ObservableList<Customer> getCustomers() {
        //FIXME - Should filter all customers for instances related to this object.
        Customer[] filteredCustomers = Customer.allCustomers.stream().filter(c -> c.division.get().equals(this)).toArray(Customer[]::new);
        return FXCollections.observableArrayList(filteredCustomers);
    }

    public int getID() {
        return this.id.get();
    }

    public String getName() {
        return this.divisionName.get();
    }

    public static ObservableList<Division> getAllDivisions() {
        return allDivisions;
    }

    public static void loadData() {
        //FIXME - Need to load from Database
        Division[] divisions = new Division[] {
                new Division(0, "Division 1", Country.getCountryByID(0)),
                new Division(1, "Division 2", Country.getCountryByID(1)),
                new Division(2, "Division 3", Country.getCountryByID(2)),
                new Division(3, "Division 4", Country.getCountryByID(3)),
                new Division(4, "Division 5", Country.getCountryByID(4)),
                new Division(5, "Division 6", Country.getCountryByID(5)),
                new Division(6, "Division 7", Country.getCountryByID(6)),
                new Division(7, "Division 8", Country.getCountryByID(7)),
                new Division(8, "Division 9", Country.getCountryByID(8)),
                new Division(9, "Division 10", Country.getCountryByID(9))
        };
        allDivisions = FXCollections.observableList(new ArrayList<Division>(List.of(divisions)));
    }

    public static Division getDivisionById(int id) {
        try {
            return allDivisions.stream().filter(d -> d.getID() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingDivisionRecord);
            dialog.showAndWait();
            return allDivisions.stream().findFirst().get();
        }
    }

    public int getCountryId() {
        return this.id.get();
    }
}
