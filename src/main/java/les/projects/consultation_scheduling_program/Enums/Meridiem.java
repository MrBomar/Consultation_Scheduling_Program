package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This enum represents the A.M./P.M.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public enum Meridiem {
    /**
     * The AM.
     */
    AM("AM"),

    /**
     * The PM.
     */
    PM("PM");

    public final String s;

    /**
     * This constructor instantiates the Meridiem object.
     * @param s The string representing AM or PM.
     */
    Meridiem(String s) {
        this.s = s;
    }

    /**
     * This method converts the enum list into an ObservableList and returns it.
     * @return An ObservableList of Meridiem values.
     */
    public static ObservableList<Meridiem> getMeridiems() { return FXCollections.observableArrayList(values()); }

    /**
     * This method overrides the default toString() method of the enum Meridiem.
     * @return The string value of Meridiem.
     */
    @Override
    public String toString() { return this.s; }
}
