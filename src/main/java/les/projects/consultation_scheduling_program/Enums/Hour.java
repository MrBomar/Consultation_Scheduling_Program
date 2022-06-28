package les.projects.consultation_scheduling_program.Enums;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Hours the user can select.
 *
 * @author Leslie C. Bomar 3rd
 * @version 1.0
 */
public enum Hour {
    /**
     * The number onw.
     */
    One(1),

    /**
     * The number two.
     */
    Two(2),

    /**
     * The number three.
     */
    Three(3),

    /**
     * The number four.
     */
    Four(4),

    /**
     * The number five.
     */
    Five(5),

    /**
     * The number six.
     */
    Six(6),

    /**
     * The number seven.
     */
    Seven(7),

    /**
     * The number eight.
     */
    Eight(8),

    /**
     * The number nine.
     */
    Nine(9),

    /**
     * The number ten.
     */
    Ten(10),

    /**
     * The number eleven.
     */
    Eleven(11),

    /**
     * The number twelve.
     */
    Twelve(12);

    public final int number;

    /**
     * This method initializes the Hour object.
     * @param number The hour.
     */
    Hour(int number) { this.number = number; }

    /**
     * This method converts the enum list into an observable list and returns it.
     * @return This enum list as an ObservableList.
     */
    public static ObservableList<Hour> getHours() { return FXCollections.observableArrayList(values()); }

    /**
     * This method overrides the default toString method for the Hour enum.
     * @return The hour value as a string.
     */
    @Override
    public String toString() { return (this.number < 10)? "0" + this.number : "" + this.number; }
}

