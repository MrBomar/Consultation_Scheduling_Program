package les.projects.consultation_scheduling_program.DataClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class manages the data objects related to the first report. These object are extracts from the
 * Appointments table.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public class ReportOneItem {
    private final SimpleStringProperty month;
    private final SimpleStringProperty type;
    private final SimpleIntegerProperty count;

    /**
     * This constructor instantiates a ReportOneItem.
     * @param type The type of appointment.
     * @param month The month of the appointment.
     * @param count The number of appointments for the type and month.
     */
    public ReportOneItem(String type, String month, int count) {
        this.month = new SimpleStringProperty(month);
        this.type = new SimpleStringProperty(type);
        this.count = new SimpleIntegerProperty(count);
    }

    /**
     * This method gets the month of the appointment.
     * @return The month of the appointment.
     */
    public final String getMonth() { return month.get(); }

    /**
     * This method gets the type of the appointment.
     * @return The type of the appointment.
     */
    public final String getType() { return type.get(); }

    /**
     * This method gets the number of appointments for the type and month.
     * @return The number of appointments for the type and month.
     */
    public final Integer getCount() { return count.get(); }
}
