package les.projects.consultation_scheduling_program.DataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import les.projects.consultation_scheduling_program.Main;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Appointment {
    private int id;
    private int customerID;
    private int userID;
    private int contactID;
    private String title;
    private String description;
    private String location;
    private String type;
    private ZonedDateTime start, end;
    private static ObservableList<Appointment> allAppointments;

    public Appointment(int id, String title, String description, String location,
                       String type, ZonedDateTime start, ZonedDateTime end, int customerId,
                       int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerId;
        this.userID = userId;
        this.contactID = contactId;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static void add(String title, String description, String location,
                                  String type, ZonedDateTime start, ZonedDateTime end, int customerId, int contactId) {
        //FIXME - Update this method to save the Appointment to the database then refresh allAppointments
        //FIXME - ZonedDateTime needs to be converted to UTC before saving changes.

        int nextID = (allAppointments.size() > 0)?
                Collections.max(allAppointments, Comparator.comparing(i -> i.getId())).getId() + 1 :
                1;
        allAppointments.add(
                new Appointment(nextID,title,description,location,type,start,end,customerId,
                        Main.currentUser.getUserID(),contactId)
        );

    }

    public boolean update(String title, String description, String location, String type,
                          ZonedDateTime start, ZonedDateTime end, int customerId,
                          int userId, int contactId) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerId;
        this.userID = userId;
        this.contactID = contactId;
        return true;
    }

    public boolean delete() {
        //FIXME - Must delete from database and return confirmation.
        return true;
    }

    public static Appointment getAppointment(int index) {
        return allAppointments.get(index);
    }

    public static ObservableList<Appointment> getAllAppointments() { return allAppointments;}

    public static void loadData() {
        //FIXME - Need to load from Database
        Appointment[] appointments = new Appointment[] {
            new Appointment(0,"Appointment1","Description1", "Location1","Type1", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(1,"Appointment2","Description2", "Location2","Type2", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(2,"Appointment3","Description3", "Location3","Type3", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(3,"Appointment4","Description4", "Location4","Type4", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(4,"Appointment5","Description5", "Location5","Type5", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(5,"Appointment6","Description6", "Location6","Type6", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(6,"Appointment7","Description7", "Location7","Type7", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(7,"Appointment8","Description8", "Location8","Type8", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(8,"Appointment9","Description9", "Location9","Type9", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1),
            new Appointment(9,"Appointment10","Description10", "Location10","Type10", ZonedDateTime.now(), ZonedDateTime.now(), 1,1,1)
        };
        allAppointments = FXCollections.observableList(new ArrayList<Appointment>(List.of(appointments)));
    }

    //Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public final String getDescription() { return description; }
    public final String getLocation() { return location; }
    public final String getType() { return type; }
    //FIXME - These need to be converted to local time zone.
    public final ZonedDateTime getStart() { return this.start; }
    public final ZonedDateTime getEnd() { return this.end; }
    public final int getCustomerID() { return customerID; }
    public final int getUserID() { return userID; }
    public final int getContactID() { return contactID; }

}
