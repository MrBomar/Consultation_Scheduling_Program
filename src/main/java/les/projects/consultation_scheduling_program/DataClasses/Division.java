package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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

    @Override
    public String toString() {
        return this.name.get();
    }

    public static void add(String divisionName, Country country) {
        //FIXME - Should take supplied inputs, create a new division, and add it to the database.
        allDivisions.add(new Division(0,divisionName,country));
    }

    public final void update(String divisionName, Country country) {
        this.setName(divisionName);
        this.setCountry(country);
    }

    public final void delete() {
        //FIXME - Should delete this object from the database.
        allDivisions.remove(this);
    }

    public final ObservableList<Customer> getCustomers() {
        //FIXME - Should filter all customers for instances related to this object.
        Customer[] filteredCustomers =
                Customer.allCustomers.stream().filter(c -> c.getDivision().equals(this)).toArray(Customer[]::new);
        return FXCollections.observableArrayList(filteredCustomers);
    }

    public static void loadData() {
        try {
            //Load the data from the database
            Statement stmt = JDBC.connection.createStatement();
            String qry = "SELECT * FROM first_level_divisions";
            ResultSet rs = stmt.executeQuery(qry);

            if(!rs.next()) {
                DialogMessage dialog = new DialogMessage("Data Not Found", "No divisions were found in the database.");
            } else {
                allDivisions = FXCollections.observableList(new ArrayList<Division>());

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
            System.out.println("Could not load divisions.");
        }
    }

    //Getters
    public final Country getCountry() { return this.country.get(); }
    public final String getName() { return this.name.get(); }
    public final Integer getId() { return this.id.get(); }
    public static Division getById(int id) {
        try {
            return allDivisions.stream().filter(d -> d.getId() == id).findFirst().get();
        } catch (NoSuchElementException e) {
            DialogMessage dialog = new DialogMessage(Message.MissingDivisionRecord);
            dialog.showAndWait();
            return allDivisions.stream().findFirst().get();
        }
    }

    //Property Getters
    public final Property<Country> getCountryProperty() { return this.country; }
    public final Property<String> getNameProperty() { return this.name; }
    public final Property<Number> getIdProperty() { return this.id; }

    //Property Setters
    public final void setCountry(Country country) { this.country.set(country);}
    public final void setName(String name) { this.name.set(name);}
    public final void setId(int id) { this.id.set(id); }
}
